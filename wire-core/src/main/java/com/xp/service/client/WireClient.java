package com.xp.service.client;

import java.lang.annotation.*;

/**
 * @author lxp
 * @title WireClient
 * @date 2024/11/6 11:07
 * @description 客户端定义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WireClient {

    /**
     * 名称
     */
    String value();

    /**
     * 访问地址
     */
    String path();

    /**
     * 访问端口
     */
    String port();

    /**
     * 编码器
     */
    Class<?> encoded() default void.class;
}
