package com.jx.argo;

import java.io.Serializable;

/**
 * 所有Action的返回结果
 *
 */
public interface ActionResult extends Serializable {

    public final static ActionResult NULL = null;

    /**
     * 用于生成显示页面
     *
     * @param beatContext 需要渲染的上下文
     */
    void render(BeatContext beatContext);
}
