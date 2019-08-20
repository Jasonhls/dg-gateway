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


import cn.hutool.core.collection.CollectionUtil;
import com.dg.mall.core.util.RegexUtil;
import com.dg.mall.gateway.core.constants.AuthConstants;
import com.dg.mall.gateway.core.constants.GatewayFiltersOrder;
import com.dg.mall.gateway.core.exception.AuthExceptionEnum;
import com.dg.mall.jwt.properties.JwtProperties;
import com.dg.mall.model.exception.ServiceException;
import com.dg.mall.system.api.context.LoginUser;
import com.dg.mall.system.api.context.SysMenuDTO;
import com.dg.mall.system.api.exception.enums.SystemExceptionEnum;
import com.dg.mall.system.api.feign.AuthFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * 请求路径权限过滤器
 */
public class PathMatchFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtProperties jwtProperties;

    @Value("${filter.path}")
    private String filterPath;

    @Autowired
    private AuthFeignService authFeignService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        String path = serverHttpRequest.getPath().pathWithinApplication().value();

        if(!RegexUtil.matcher(filterPath, path)){
            final String sysToken = serverHttpRequest.getHeaders().getFirst(AuthConstants.MANAGE_AUTH_HEADER);
            LoginUser loginUser = authFeignService.getLoginUserByToken(sysToken);
            Optional.ofNullable(loginUser).orElseThrow(() -> new ServiceException(SystemExceptionEnum.USER_NOT_FOUND));
            List<SysMenuDTO> sysMenuDTOS = loginUser.getMenus();
            Set<String> permissionUrls= new HashSet<>();
            if(CollectionUtil.isNotEmpty(sysMenuDTOS)){
                sysMenuDTOS.forEach(s -> permissionUrls.add(s.getPath()));
            }
            boolean hasPermission = permissionUrls.contains(path);
            if (hasPermission) {
                return null;
            } else {
                throw new ServiceException(AuthExceptionEnum.NO_PERMISSION);
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return GatewayFiltersOrder.PATH_MATCH_FILTER_ORDER;
    }
}
