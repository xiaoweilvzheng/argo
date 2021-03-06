package com.jx.argo.internal;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.io.Closeables;
import com.google.inject.Key;
import com.google.inject.OutOfScopeException;
import com.jx.argo.ActionResult;
import com.jx.argo.Argo;
import com.jx.argo.BeatContext;
import com.jx.argo.inject.ArgoSystem;
import com.jx.argo.internal.actionresult.StaticActionResult;
import com.jx.argo.internal.actionresult.StatusCodeActionResult;
import com.jx.argo.logs.Logger;
import com.jx.argo.route.ActionMonitor;
import com.jx.argo.route.Router;
import com.jx.argo.servlet.ArgoDispatcher;
import com.jx.argo.servlet.ArgoRequest;
import com.jx.argo.utils.GuiceUtils;
import com.jx.argo.utils.OnlyOnceCondition;

/**
 *
 * 用于处理Rest请求调度的核心类
 *
 */
@Singleton
public class DefaultArgoDispatcher implements ArgoDispatcher {

    private final Argo argo;

    private final Router router;

    private final StatusCodeActionResult statusCodeActionResult;

    private final Key<BeatContext> defaultBeatContextKey = Key.get(BeatContext.class, ArgoSystem.class);

    private final MultipartConfigElement config;

    private final Logger logger;


    @Inject
    public DefaultArgoDispatcher(Argo argo, Router router, StatusCodeActionResult statusCodeActionResult, MultipartConfigElement config) {

        this.argo = argo;

        this.router = router;
        this.statusCodeActionResult = statusCodeActionResult;
        this.config = config;

        this.logger = argo.getLogger(this.getClass());

        logger.info("constructed.", this.getClass());
    }

    @Override
    public void init() {
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {

        ArgoRequest argoRequest = new ArgoRequest(request, config);

        try {
            BeatContext beatContext = bindBeatContext(argoRequest, response);

            route(beatContext);
        } finally {
            Closeables.closeQuietly(argoRequest);
        }
    }

    private BeatContext bindBeatContext(HttpServletRequest request, HttpServletResponse response) {
        Context context = new Context(request, response);
        localContext.set(context);

        BeatContext beat = argo.injector().getInstance(defaultBeatContextKey);
        // 增加默认参数到model
        beat.getModel().add("__beat", beat);
        // 增加监控事件
        ActionMonitor monitor = GuiceUtils.dynamicGetInstance(ActionMonitor.class);
        beat.setActionMonitor(monitor);
        
        context.setBeat(beat);
        return beat;
    }

    private void route(BeatContext beat) {
        try {
            ActionResult result = router.route(beat);

            if (ActionResult.NULL == result)
                result = statusCodeActionResult.getSc404();

            result.render(beat);
            
        	//输出监控
        	beat.outputMonitor(result);
        } catch (Exception e) {

            statusCodeActionResult.render405(beat);

            e.printStackTrace();

            logger.error(String.format("fail to route. url:%s", beat.getClient().getRelativeUrl()), e);

            //TODO: catch any exceptions.

        } finally {
            localContext.remove();
        }
    }

    public void destroy() {
    }

    public HttpServletRequest currentRequest() {
        return getContext().getRequest();
    }

    public HttpServletResponse currentResponse() {
        return getContext().getResponse();
    }

    public BeatContext currentBeatContext() {
        return getContext().getBeat();
    }


    private Context getContext() {
        Context context = localContext.get();
        if (context == null) {
            throw new OutOfScopeException("Cannot access scoped object. Either we"
                    + " are not currently inside an HTTP Servlet currentRequest, or you may"
                    + " have forgotten to apply " + DefaultArgoDispatcher.class.getName()
                    + " as a servlet filter for this currentRequest.");
        }
        return context;
    }

    final ThreadLocal<Context> localContext = new ThreadLocal<Context>();

    private static class Context {

        final HttpServletRequest request;
        final HttpServletResponse response;

        BeatContext beat;

        OnlyOnceCondition onlyOnce = OnlyOnceCondition.create("The current beat has been created.");

        Context(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }

        HttpServletRequest getRequest() {
            return request;
        }

        HttpServletResponse getResponse() {
            return response;
        }

        BeatContext getBeat() {
            return beat;
        }

        void setBeat(BeatContext beat) {
            onlyOnce.check();
            this.beat = beat;
        }
    }

}
