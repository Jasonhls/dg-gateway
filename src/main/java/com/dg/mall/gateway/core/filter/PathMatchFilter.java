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


import com.dg.mall.gateway.core.constants.GatewayFiltersOrder;
import com.dg.mall.gateway.modular.consumer.AuthServiceConsumer;
import com.dg.mall.jwt.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 请求路径权限过滤器
 */
public class PathMatchFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtProperties jwtProperties;

    @Value("${filter.path}")
    private String filterPath;

    @Autowired
    private AuthServiceConsumer authServiceConsumer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest serverHttpRequest = exchange.getRequest();
//
//        String path = serverHttpRequest.getPath().pathWithinApplication().value();
//
//        if(!RegexUtil.matcher(filterPath, path)){
//            final String sysToken = serverHttpRequest.getHeaders().getFirst(AuthConstants.MANAGE_AUTH_HEADER);
//            LoginUser loginUser = LoginContext.me().getLoginUser();
//            if(loginUser == null){
//                throw new ServiceException(AuthExceptionEnum.NO_PERMISSION);
//            }
//            Set<SysMenuDTO>  menus = loginUser.getMenus();
//            if(menus == null || menus.size() < 1){
//                throw new ServiceException(AuthExceptionEnum.NO_PERMISSION);
//            }
//            Set<String> permissionUrls = menus.stream().map(menu -> menu.getRedirect()).collect(Collectors.toSet());
//
//            boolean hasPermission = permissionUrls.contains(path);
//            if (hasPermission) {
//                return null;
//            } else {
//                throw new ServiceException(AuthExceptionEnum.NO_PERMISSION);
//            }
//        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return GatewayFiltersOrder.PATH_MATCH_FILTER_ORDER;
    }
}
