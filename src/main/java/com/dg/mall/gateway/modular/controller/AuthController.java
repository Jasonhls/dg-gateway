/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dg.mall.gateway.modular.controller;


import com.dg.mall.core.reqres.response.ResponseData;
import com.dg.mall.gateway.core.constants.AuthConstants;
import com.dg.mall.gateway.modular.consumer.AuthServiceConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 鉴权服务
 */
@RestController
public class AuthController {

    @Autowired
    private AuthServiceConsumer authServiceConsumer;

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(AuthConstants.AUTH_ACTION_URL)
    public ResponseData auth(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        String token = authServiceConsumer.login(userName, password);
        return ResponseData.success(token);
    }


    /**
     * 校验 token
     *
     * @param token
     * @return
     */
    @PostMapping(AuthConstants.VALIDATE_TOKEN_URL)
    public ResponseData validateToken(@RequestParam("token") String token) {
        boolean tokenFlag = authServiceConsumer.checkToken(token);
        return ResponseData.success(tokenFlag);
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @PostMapping(AuthConstants.LOGOUT_URL)
    public ResponseData logout(@RequestParam("token") String token) {
        authServiceConsumer.logout(token);
        return ResponseData.success();
    }

}
