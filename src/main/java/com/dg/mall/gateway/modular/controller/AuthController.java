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
import com.dg.mall.gateway.core.exception.AuthExceptionEnum;
import com.dg.mall.model.exception.ServiceException;
import com.dg.mall.system.api.feign.AuthFeignService;
import com.dg.mall.system.api.req.LoginReq;
import com.dg.mall.system.api.req.ToeknReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 鉴权服务
 */
@RestController
public class AuthController {

    @Autowired
    private AuthFeignService authFeignService;

    /**
     * 登录
     *
     * @return
     */
    @PostMapping(AuthConstants.AUTH_ACTION_URL)
    public ResponseData auth(@Validated @RequestBody LoginReq loginReq) {
        return authFeignService.login(loginReq);

    }


    /**
     * 校验 token
     * @return
     */
    @PostMapping(AuthConstants.VALIDATE_TOKEN_URL)
    public ResponseData validateToken(@Validated @RequestBody ToeknReq tokenReq) {
        boolean tokenFlag;
        try {
            tokenFlag = authFeignService.checkToken(tokenReq);
        } catch (Exception e) {
            throw new ServiceException(AuthExceptionEnum.TOKEN_ERROR);
        }
        return ResponseData.success(tokenFlag);
    }

    /**
     * 退出登录
     */
    @PostMapping(AuthConstants.LOGOUT_URL)
    public ResponseData logout(@Validated @RequestBody ToeknReq tokenReq) {
        authFeignService.logout(tokenReq);
        return ResponseData.success();
    }

}
