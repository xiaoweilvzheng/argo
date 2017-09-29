package com.jx.argo.servlet;

import com.jx.argo.Argo;
import com.jx.argo.convention.GroupConvention;
import com.jx.argo.convention.GroupConventionFactory;

import javax.servlet.ServletContext;


public class ArgoDispatcherFactory {

    public static ArgoDispatcher create(ServletContext servletContext){

        // xxx:这是一处硬编码
        GroupConvention groupConvention = GroupConventionFactory.getGroupConvention();

        return Argo.instance.init(servletContext, groupConvention);

    }
}
