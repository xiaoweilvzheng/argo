package com.jx.argo.route;

import com.jx.argo.BeatContext;
import com.jx.argo.utils.PathUtils;

/**
 * 路由信息
 */
public class RouteBag {

    public static RouteBag create(BeatContext beat) {
        return new RouteBag(beat);
    }

    private final BeatContext beat;
    private final boolean isGet;
    private final boolean isPost;
    private final String path;
    private final String simplyPath;

    private RouteBag(BeatContext beat) {
        this.beat = beat;

        path = beat.getClient().getRelativeUrl();
        simplyPath = PathUtils.simplyWithoutSuffix(path);

        String requestMethod = beat.getRequest().getMethod().toUpperCase();
        isPost = "POST".equals(requestMethod);
        isGet = !isPost;
    }

    public BeatContext getBeat() {
        return beat;
    }

    public boolean isGet() {
        return isGet;
    }

    public boolean isPost() {
        return isPost;
    }

    public String getPath() {
        return path;
    }

    public String getSimplyPath() {
        return simplyPath;
    }
}
