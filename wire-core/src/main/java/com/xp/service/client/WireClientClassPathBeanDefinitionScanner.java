package com.xp.service.client;

import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

/**
 * @title WireClientClassPathBeanDefinitionScanner
 * @date 2024/11/11 16:21
 * @author lxp
 * @description
 */
public class WireClientClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public WireClientClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(WireClient.class));
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        processBeanDefinitions(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    public void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        beanDefinitionHolders.forEach(beanDefinitionHolder -> {
            BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                AnnotationMetadata annotationMetadata = ((AnnotatedBeanDefinition)beanDefinition).getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(), "@WireClient只能使用在接口上");
            }
            GenericBeanDefinition definition = (GenericBeanDefinition)beanDefinition;
            String beanClassName = definition.getBeanClassName();
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            definition.setBeanClass(WireClientFactoryBean.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        });
    }
}
