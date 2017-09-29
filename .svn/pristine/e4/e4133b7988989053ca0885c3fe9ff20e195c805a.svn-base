package com.jx.argo.internal;

import java.util.Map;

import com.google.common.collect.Maps;
import com.jx.argo.ActionResult;
import com.jx.argo.interceptor.PostInterceptor;
import com.jx.argo.interceptor.PreInterceptor;
import com.jx.argo.jxlifecycle.LifecycleState;
import com.jx.argo.route.Action;
import com.jx.argo.route.RouteBag;
import com.jx.argo.route.RouteResult;
import com.jx.argo.thirdparty.AntPathMatcher;
import com.jx.argo.thirdparty.PathMatcher;

public class MethodAction implements Action {

    public static MethodAction create(ActionInfo actionInfo) {
        return new MethodAction(actionInfo);
    }

    private final ActionInfo actionInfo;

    private final double order;

    private PathMatcher pathMatcher = new AntPathMatcher();
    


    private MethodAction(ActionInfo actionInfo) {
        this.actionInfo = actionInfo;

        order = actionInfo.getOrder()
                + (10000.0d - actionInfo.getPathPattern().length())/100000.0d
                + (actionInfo.isPattern() ? 0.5d : 0d);
    }

    @Override
    public double order() {
        return order;
    }
    
    @Override
    public RouteResult matchAndInvoke(RouteBag bag) {

        if (!actionInfo.matchHttpMethod(bag))
            return RouteResult.unMatch();

        Map<String, String> uriTemplateVariables = Maps.newHashMap();

        boolean match = actionInfo.match(bag, uriTemplateVariables);
        if (!match)
            return RouteResult.unMatch();

        //标记路由结束时间
        bag.getBeat().getLifecycle().setTimestamp(LifecycleState.ROUTE);
        
        // PreIntercept
        for(PreInterceptor preInterceptor : actionInfo.getPreInterceptors()) {
            ActionResult actionResult = preInterceptor.preExecute(bag.getBeat());
            //标记前置拦截结束时间
            bag.getBeat().getLifecycle().setTimestamp(LifecycleState.PRE,preInterceptor.getClass().getName());
            if (ActionResult.NULL != actionResult)
                return RouteResult.invoked(actionResult);
        }

        ActionResult actionResult = actionInfo.invoke(uriTemplateVariables);
        //标记执行结束时间
        bag.getBeat().getLifecycle().setTimestamp(LifecycleState.EXECUTE);
        
        // PostIntercept
        for(PostInterceptor postInterceptor : actionInfo.getPostInterceptors()) {
            actionResult = postInterceptor.postExecute(bag.getBeat(), actionResult);
            //标记后置拦截结束时间
            bag.getBeat().getLifecycle().setTimestamp(LifecycleState.POST,postInterceptor.getClass().getName());
        }

        return RouteResult.invoked(actionResult);
    }

}
