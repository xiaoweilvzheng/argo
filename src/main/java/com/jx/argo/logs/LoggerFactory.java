package com.jx.argo.logs;

import com.google.inject.ImplementedBy;
import com.jx.argo.internal.DefaultLoggerFactory;

@ImplementedBy(DefaultLoggerFactory.class)
public interface LoggerFactory {

    /**
     *
     * @param name the name of the Logger to return
     * @return a Logger instance
     */
    Logger getLogger(String name);

    Logger getLogger(Class<?> clazz);
}
