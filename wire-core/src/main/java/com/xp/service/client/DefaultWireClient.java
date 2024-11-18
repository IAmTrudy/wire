package com.xp.service.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;

/**
 * @title DefaultWireClient
 * @date 2024/11/11 16:32
 * @author lxp
 * @description 
 */
public class DefaultWireClient<T> implements InvocationHandler {

    private Class<T> type;

    public DefaultWireClient(Class<T> type) {
        this.type = type;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        Class<?> declaringClass = method.getDeclaringClass();
        WireClient wireClient = declaringClass.getAnnotation(WireClient.class);
        String value = wireClient.path() + ":" +wireClient.port();

        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof PostMapping) {
                PostMapping postMapping = (PostMapping) annotation;
                String[] path = postMapping.value();

                // TODO 换成NETTY
                HttpRequest post = HttpRequest.post(value + path[0]);
                HttpResponse execute = post.body(JSONUtil.toJsonStr(args[0]))
                        .execute();
                return execute.body();
            }
            if (annotation instanceof GetMapping) {
                GetMapping getMapping = (GetMapping) annotation;
                String[] path = getMapping.value();

                // TODO 换成NETTY
                HttpRequest get = HttpRequest.get(value + path[0]);
                HttpResponse execute = get.body(JSONUtil.toJsonStr(args[0]))
                        .execute();
                return execute.body();
            }
        }
        return null;
    }
}
