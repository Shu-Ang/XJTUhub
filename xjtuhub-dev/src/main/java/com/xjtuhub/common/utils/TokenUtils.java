package com.xjtuhub.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xjtuhub.common.exception.BusinessException;
import com.xjtuhub.common.exception.SysException;
import com.xjtuhub.entity.CurrentUser;
import com.xjtuhub.service.impl.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import java.util.Date;


/**
 * 令牌工具类
 * 将令牌工具类定义为spring配置类，这样令牌工具类的对象就会在Spring容器启动时自动加载
 */
@Slf4j
@Configuration
public class TokenUtils {
    private static RoleService roleService;
    private static RedisOperator redisOperator;
    // 令牌中存放用户账号ID对应的名字
    private static final String CLAIM_NAME_USERID = "CLAIM_NAME_USERID";
    // 令牌的过期时间，单位s
    private static int expireTime;

    /*
        该set方法为实例方法，该方法上加了@Autowired，
        将在令牌工具类的对象被spring容器加载时，自动运行，并注入需要的参数对象

        Spring容器中管理的是实例对象，依赖注入和自动装配只能针对实例方法和实例属性
     */
    @Autowired
    public void setRoleService(RoleService roleService) {
        TokenUtils.roleService = roleService;
    }

    @Autowired
    public void setRedisOperator(RedisOperator redisOperator) {
        TokenUtils.redisOperator = redisOperator;
    }


    @Autowired
    public void setEnv(Environment env) {
        try {
            // 从配置文件中获取参数
            expireTime = env.getProperty("xjtuhub.expire-time", Integer.class, 60);
            log.info("从配置中获取令牌过期时间为{}秒！", expireTime);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("从配置获取令牌过期时间失败！默认令牌过期时间为60秒。");
            expireTime = 60;
        }
    }

    private static String sign(CurrentUser currentUser, String securityKey){
        String token = JWT.create()
                .withClaim(CLAIM_NAME_USERID, currentUser.getUserId())
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime * 1000)) // 有效时间
                .sign(Algorithm.HMAC256(securityKey));
        return token;
    }

    /**
     * 将当前用户信息以用户密码为密钥生成一个token
     */
    public static String loginSign(CurrentUser currentUser, String password){
        // 生成登录令牌
        String token = sign(currentUser, password);
        // 将令牌以键又以值存入redis中，并设置在redis中该令牌存储的过期时间为令牌本身过期时间的两倍（EXPIRED_TIME*2）
        redisOperator.set(token, token, expireTime * 2);
        return token;
    }

    /**
     * 从客户端传来的令牌中获取当前用户
     * @param clientToken 客户端传来的令牌
     * @return
     */
    public static CurrentUser getCurrentUser(String clientToken) {
        // 1.判断传来的令牌是否为空(null、""、空白字符串等都算空)
        if(!StringUtils.hasText(clientToken)){
            throw new BusinessException("令牌为空！请登录。");
        }
        // 2.对客户端令牌进行解码，或取一个解码后的令牌，只有解码后的令牌才能提取信息
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.decode(clientToken); // 获取一个解码后的令牌
        } catch (JWTDecodeException e) {
            throw new BusinessException("令牌格式错误！请登录。");
        }
        // 3.从解码后的令牌中获取当前用户
        String userId = decodedJWT.getClaim(CLAIM_NAME_USERID).asString(); // 获取用户账号id

        return new CurrentUser(userId);
    }

    /**
     * 通过密钥判断判断令牌是否合法，如果合法还要判断令牌是否过期
     * 如果校验没有通过抛出业务异常
     * @param clientToken 客户端令牌
     */
    public static void verify(String clientToken) {
        // 1. 从令牌中获取当前用户
        CurrentUser currentUser = getCurrentUser(clientToken);

        // 2.根据当前用户账号从数据库中查询出当前用户密码作为密钥对令牌进行合法性校验，若未通过抛出业务异常
        String password = null;
        try {
            password = roleService.findRoleById(currentUser.getUserId()).getPassword();
        } catch (Exception e) {
            log.error("系统错误！",e);
            throw new SysException("系统错误！", e);
        }

        if(!StringUtils.hasText(password)){
            throw new BusinessException("没有找到当前用户的密钥！请登录后再试。");
        }

        // 验证令牌：应当验证redis中存储的令牌，而不是验证客户端传来的令牌(客户端传来的令牌做为键)
        String cacheToken = redisOperator.get(clientToken); // 获得redis中存储的令牌
        if(!StringUtils.hasText(cacheToken)){ // redis中存储的令牌不存在
            throw new BusinessException("令牌过期！请重新登录。");
        }

        // 根据密钥，获取加密算法
        Algorithm algorithm = Algorithm.HMAC256(password);

        // 通过加密算法构建令牌的验证器
        JWTVerifier jwtVerifier =  JWT.require(algorithm).build();

        // 通过验证器对令牌进行验证, 若验证不通过会抛出验证异常
        try {
            jwtVerifier.verify(cacheToken);//验证redis中的令牌
        } catch (TokenExpiredException e) {//令牌本身过期异常
            // 验证redis中的令牌是本身否过期，如果过期重新生成新令牌，覆盖redis中的令牌,而键不变即客户端令牌
            String newToken = sign(currentUser, password);
            redisOperator.set(clientToken, newToken, expireTime *2);
        } catch (Exception e){
            throw new BusinessException("非法令牌！请登录。");
        }
    }

    /**
     * 从redis中移除令牌，退出登录时使用
     */
    public static void removeToken(String clientToken){
        redisOperator.del(clientToken);
    }
}
