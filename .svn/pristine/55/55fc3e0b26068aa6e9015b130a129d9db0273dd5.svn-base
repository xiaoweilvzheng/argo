package com.jx.argo.logs;

import com.google.inject.ImplementedBy;
import com.jx.argo.internal.DefaultLoggerFactory;

/**
 * 对日志文件进行设置
 * 默认采用log4j
 *
 */
@ImplementedBy(DefaultLoggerFactory.DefaultLog4jConfigure.class)
public interface LogConfigure {

    void configure();


}
