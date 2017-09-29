package com.jx.argo.interceptor;

import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;

/**
 * 出错拦截器
 * TODO: 还未实现
 */
public interface ExceptInterceptor {

    Throwable[] getExceptionScopes();

    ActionResult catchMe(Throwable e, BeatContext beatContext);
}
