package com.jx.argo.controller;

import com.google.inject.ImplementedBy;
import com.jx.argo.ActionResult;
import com.jx.argo.internal.VelocityViewFactory;

/**
 * 提供View工厂，默认采用velocity模板
 */
@ImplementedBy(VelocityViewFactory.class)
public interface ViewFactory {
    ActionResult create(String viewName);
}
