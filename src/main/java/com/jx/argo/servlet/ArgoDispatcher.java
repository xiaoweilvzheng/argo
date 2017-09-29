package com.jx.argo.servlet;

import com.google.inject.ImplementedBy;
import com.jx.argo.BeatContext;
import com.jx.argo.internal.DefaultArgoDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 用于处理request请求调度的核心类
 *
 */
@ImplementedBy(DefaultArgoDispatcher.class)
public interface ArgoDispatcher {

    void init();

    void service(HttpServletRequest request, HttpServletResponse response);

    void destroy();

    public HttpServletRequest currentRequest();

    public HttpServletResponse currentResponse();

    BeatContext currentBeatContext();

}
