package com.self.mybatis.jpa.ddl;

import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.lang.annotation.Annotation;

public class AnnotationTypeFilterBuilder {

    private AnnotationTypeFilterBuilder() {}

    public static TypeFilter build(Class<? extends Annotation> annotationType) {
        return new AnnotationTypeFilter(annotationType, false);
    }
}
