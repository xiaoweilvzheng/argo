package com.jx.argo.internal;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.Model;
import com.jx.argo.client.ClientContext;
import com.jx.argo.inject.ArgoSystem;
import com.jx.argo.jxlifecycle.Lifecycle;
import com.jx.argo.route.ActionMonitor;

@ArgoSystem
public class DefaultBeatContext implements BeatContext {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Model model;
    private final ClientContext clientContext;
    private final ServletContext servletContext;
    private Lifecycle lifecycle;
    private ActionMonitor monitor;

    @Inject
    public DefaultBeatContext(HttpServletRequest request, HttpServletResponse response, Model model, ClientContext clientContext, ServletContext servletContext) {
        this.request = request;
        this.response = response;
        this.model = model;
        this.clientContext = clientContext;
        this.servletContext = servletContext;
        this.lifecycle = new Lifecycle(request);
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public HttpServletRequest getRequest() {
        return request;
    }

    @Override
    public HttpServletResponse getResponse() {
        return response;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public ClientContext getClient() {
        return clientContext;
    }

	@Override
	public Lifecycle getLifecycle() {
		return lifecycle;
	}

	@Override
	public void setActionMonitor(ActionMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void outputMonitor(ActionResult result) {
		if (monitor == null)
			return;
		monitor.output(this,result);
	}


}
