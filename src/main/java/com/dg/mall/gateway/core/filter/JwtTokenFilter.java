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
package com.dg.mall.gateway.core.filter;


import com.dg.mall.core.util.RegexUtil;
import com.dg.mall.gateway.core.constants.GatewayFiltersOrder;
import com.dg.mall.gateway.core.exception.AuthExceptionEnum;
import com.dg.mall.gateway.modular.service.TokenValidateService;
import com.dg.mall.model.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 权限校验的过滤器
 *
 * @author fengshuonan
 * @date 2017-11-08-下午2:49
 */
public class JwtTokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private TokenValidateService tokenValidateService;

    @Value("${filter.path}")
    private String filterPath;



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        String path = serverHttpRequest.getPath().pathWithinApplication().value();

        if(!RegexUtil.matcher(filterPath, path)){
            if(!tokenValidateService.doValidate(serverHttpRequest)){
                throw new ServiceException(AuthExceptionEnum.TOKEN_ERROR);
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return GatewayFiltersOrder.JWT_TOKEN_FILTER_ORDER;
    }
}
