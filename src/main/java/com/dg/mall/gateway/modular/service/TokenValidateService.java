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
package com.dg.mall.gateway.modular.service;


import com.dg.mall.core.util.ToolUtil;
import com.dg.mall.gateway.core.constants.AuthConstants;
import com.dg.mall.gateway.core.exception.AuthExceptionEnum;
import com.dg.mall.jwt.properties.JwtProperties;
import com.dg.mall.model.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;


/**
 * Token校验的服务
 */

public abstract class TokenValidateService {

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 获取token 并 校验token 是否合法
     * @param request
     * @return
     */
    public boolean doValidate(ServerHttpRequest request) {

        //先获取token
        String tokenFromRequest = this.getTokenFromRequest(request);

        //校验token是否正确
        return this.validateToken(tokenFromRequest, request);
    }

    /**
     * 获取请求中的token
     */
    private String getTokenFromRequest(ServerHttpRequest request) {
        //获取管理后台token
        String authToken = request.getHeaders().getFirst(AuthConstants.MANAGE_AUTH_HEADER);
        if(ToolUtil.isEmpty(authToken)){
            authToken = request.getHeaders().getFirst(AuthConstants.AUTH_HEADER);

            if (ToolUtil.isEmpty(authToken)) {
                //如果header中没有token，则检查请求参数中是否带token
                authToken = request.getHeaders().getFirst("token");
                if (ToolUtil.isEmpty(authToken)) {
                    throw new ServiceException(AuthExceptionEnum.TOKEN_EMPTY);
                }
            } else {
                authToken = authToken.substring("Bearer ".length());
            }
        }

        return authToken;
    }

    /**
     * 校验token
     */
    protected abstract boolean validateToken(String token, ServerHttpRequest request);

}
