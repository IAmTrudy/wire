package com.xp.service.client;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * @title WireClientFactoryBean
 * @date 2024/11/11 16:28
 * @author lxp
 * @description
 */
public class WireClientFactoryBean<T> implements FactoryBean<T> {

    private Class<T> type;

    public WireClientFactoryBean(Class<T> type) {
        this.type = type;
    }

    @Override
    public T getObject() throws Exception {
        return (T)Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new DefaultWireClient<>(type));
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }
}
