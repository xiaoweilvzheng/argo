package com.jx.argo.internal;

import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
import com.jx.argo.Model;
import com.jx.argo.client.ClientContext;
import com.jx.argo.jxlifecycle.Lifecycle;
import com.jx.argo.route.ActionMonitor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeatContextWrapper implements BeatContext {

    private BeatContext beatContext;

    private ActionMonitor monitor;
    public BeatContextWrapper(BeatContext beatContext) {
        this.beatContext = beatContext;
    }

    @Override
    public Model getModel() {
        return beatContext.getModel();
    }

    @Override
    public HttpServletRequest getRequest() {
        return beatContext.getRequest();
    }

    @Override
    public HttpServletResponse getResponse() {
        return beatContext.getResponse();
    }

    @Override
    public ServletContext getServletContext() {
        return beatContext.getServletContext();
    }

    @Override
    public ClientContext getClient() {
        return beatContext.getClient();
    }

	@Override
	public Lifecycle getLifecycle() {
		return beatContext.getLifecycle();
	}
	
	@Override
    public void setActionMonitor(ActionMonitor monitor){
    	this.monitor = monitor;
    }
	
	@Override
    public void outputMonitor(ActionResult result){
    	if (monitor == null)
			return;
		monitor.output(this,result);
    }
//    @Override
//    public Lifecycle getLifecycle() {
//        return beatContext.getLifecycle();
//    }
}
