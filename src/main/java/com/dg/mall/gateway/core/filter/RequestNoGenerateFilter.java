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


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.dg.mall.gateway.core.constants.GatewayFiltersOrder;
import com.dg.mall.model.constants.RosesConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * requestNo生成过滤器
 *
 * @author fengshuonan
 * @date 2017-11-08-下午2:49
 */
public class RequestNoGenerateFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        //生成唯一请求号uuid
        String requestNo = IdWorker.get32UUID();
        serverHttpResponse.getHeaders().add(RosesConstants.REQUEST_NO_HEADER_NAME, requestNo);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return GatewayFiltersOrder.REQUEST_NO_GENERATE_FILTER_ORDER;
    }
}
