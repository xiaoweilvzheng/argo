package com.jx.argo.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.jx.argo.inject.ArgoAbstractModule;

public class GuiceUtils {

	private static boolean initFlag = false;
	private static Injector parentInjector = null;
	private static List<Injector> injectorList = new ArrayList<Injector>();
	public static void init(Injector injector) {
		if (injector == null)
			return;
		parentInjector = injector;
		initFlag = true;
	}
	
	/**
     * 动态增加注入绑定
     * @param inter
     * @param impl
     */
    public static void bandingInjector(Class inter ,Class impl) {
    	if (!initFlag)
    		return;
    	Injector c = parentInjector.createChildInjector(new ArgoAbstractModule(inter,impl));
    	injectorList.add(c);
    }
    /**
     * 获取动态绑定注入实例
     * @param inter
     * @return
     */
    public static <T> T dynamicGetInstance(Class<T> inter) {
    	if (!initFlag)
    		return null;
    	for (Injector child : injectorList) {
			try {
				return child.getInstance(inter);
			} catch (Exception e) {
				continue;
			}
		}
		return null;
    }
	
}
