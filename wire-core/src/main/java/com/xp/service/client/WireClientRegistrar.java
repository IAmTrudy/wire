package com.xp.service.client;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

import com.xp.service.server.EnableWireServer;

/**
 * @title WireClientRegistrar
 * @date 2024/11/11 16:15
 * @author lxp
 * @description
 */
public class WireClientRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        String name =
            ((StandardAnnotationMetadata)importingClassMetadata).getIntrospectedClass().getPackage().getName();
        Map<String, Object> attributes =
            importingClassMetadata.getAnnotationAttributes(EnableWireServer.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(attributes);
        String[] basePackages = null;
        if (annotationAttributes == null) {
            basePackages = new String[] {name};
        }
        if (annotationAttributes != null && Objects.nonNull(annotationAttributes.getStringArray("value"))) {
            basePackages = annotationAttributes.getStringArray("value");
        }
        if (Objects.isNull(basePackages) || basePackages.length == 0) {
            basePackages = (String[])attributes.get("basePackages");
        }
        WireClientClassPathBeanDefinitionScanner scanner =
            new WireClientClassPathBeanDefinitionScanner(registry, false);
        scanner.doScan(basePackages);
    }
}
