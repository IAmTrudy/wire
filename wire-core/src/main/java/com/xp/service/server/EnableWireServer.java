package com.xp.service.server;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

import com.xp.service.client.WireClientRegistrar;

/**
 * @author lxp
 * @title EnableWireServer
 * @date 2024/11/6 13:59
 * @description 自动配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WireClientRegistrar.class)
public @interface EnableWireServer {

    String[] value() default {};

    String[] basePackages() default {};
}
