package com.jx.argo.route;

import com.google.inject.ImplementedBy;
import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.internal.DefaultRouter;

/**
 * 路由器，根据每个请求的url进行匹配找到合适的
 * @see Action
 * 来执行
 */
@ImplementedBy(DefaultRouter.class)
//@Singleton
public interface Router {

    public ActionResult route(BeatContext beat);

}
