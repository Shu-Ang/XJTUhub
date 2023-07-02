package com.xjtuhub.common;

import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

@Component
public class StringUtils {

    public String simpleUUID_8(){
        String uuid = IdUtil.simpleUUID();
        String uuid_8 = new String();
        for (int i = 0;i < 8;i++){
            uuid_8 = uuid_8 + uuid.substring(4 * i, 4 * i + 1);
        }
        return uuid_8;
    }
}
