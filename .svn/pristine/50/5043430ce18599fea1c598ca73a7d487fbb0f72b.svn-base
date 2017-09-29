package com.jx.argo.interceptor;

import com.jx.argo.interceptor.PostInterceptor;

import java.lang.annotation.*;

/**
 * 设置拦截器
 * @author renjun
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostInterceptorAnnotation {
    Class<? extends PostInterceptor> value();
}