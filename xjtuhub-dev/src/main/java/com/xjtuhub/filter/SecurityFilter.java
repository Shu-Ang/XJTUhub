package com.xjtuhub.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.common.utils.XjtuhubConstants;
import com.xjtuhub.controller.LoginController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebFilter("/*")
@Slf4j
public class SecurityFilter implements Filter {

    @Autowired
    private LoginController loginController;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String path = request.getServletPath();

        // 白名单
        if(path.equals("/")
                || path.equals("/captcha/captchaImage")
                || path.equals("/register")
                || path.equals("/login")
                || path.equals("/activate/*")
                || path.equals("/logout")) {
            // 继续传递
            System.out.println("filter");
            chain.doFilter(request, response);
            return;
        }


        // 检查Token
        String clientToken = request.getHeader(XjtuhubConstants.HEADER_TOKEN_NAME);
        JSONResult result = loginController.verifyToken(clientToken);
        if(result.getSuccess() == true){
            chain.doFilter(request, response);
            return;
        }

        System.out.println("token error");
        // 其它情况，将校验结果返回
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result); // 将result对象转成json串
        out.print(json);
        out.flush();
        out.close();
    }
}

