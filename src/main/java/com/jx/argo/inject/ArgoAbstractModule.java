package com.jx.argo.inject;

import com.google.inject.AbstractModule;
/**
 * 提供简单的抽象类满足动态绑定注入事件
 * @author duqingxiang
 *
 */
public class ArgoAbstractModule extends AbstractModule  {

	private Class interClass ;
	private Class implClass;
	
	public ArgoAbstractModule(Class interClass,Class implClass){
		this.interClass = interClass;
		this.implClass = implClass;
	}
	
	
	@Override
	protected void configure() {
		bind(interClass).to(implClass);
	}

}
