package com.jx.argo.route;

import com.jx.argo.ActionResult;
import com.jx.argo.BeatContext;
public interface ActionMonitor {

	public void output(BeatContext beat,ActionResult result);
}
