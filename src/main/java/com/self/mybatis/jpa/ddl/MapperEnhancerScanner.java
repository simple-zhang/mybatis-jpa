package com.self.mybatis.jpa.ddl;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.builder.IncompleteElementException;
import org.apache.ibatis.builder.annotation.MethodResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MapperEnhancerScanner implements ApplicationListener<ApplicationEvent> {

    Logger logger = LoggerFactory.getLogger(MapperEnhancerScanner.class);

    private boolean hasInit = false;
    private String basePackage;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        boolean contextEvent = event instanceof ContextRefreshedEvent;
        if (!contextEvent) {
            return;

        }

        if (hasInit) {
            return;

        }
        hasInit = true;
        Configuration configuration = this.sqlSessionFactory.getConfiguration();
        TypeFilter filter = AnnotationTypeFilterBuilder.build(Mapper.class);
        SpringClassScanner scanner = new SpringClassScanner.Builder()
                .scanPackage(this.basePackage)
                .typeFilter(filter)
                .build();
        Set<Class<?>> mapperSet = null;
        try {
            mapperSet = scanner.scan();
        } catch (IOException | ClassNotFoundException e) {
            logger.info("class exception:", e);
            e.printStackTrace();
        }

        if (!CollectionUtils.isEmpty(mapperSet)) {
            for (Class<?> mapper : mapperSet) {
                PersistentMapperEnhancer mapperEnhancer = new PersistentMapperEnhancer(configuration, mapper);
                mapperEnhancer.enhance();
            }
            this.parsePendingMethods(configuration);
        }
    }

    private void parsePendingMethods(Configuration configuration) {
        Collection<MethodResolver> incompleteMethods = configuration.getIncompleteMethods();
        synchronized (incompleteMethods) {
            Iterator<MethodResolver> iterator = incompleteMethods.iterator();
            while (iterator.hasNext()) {
                try {
                    iterator.next().resolve();
                    iterator.remove();
                } catch (IncompleteElementException ignore) {
                    // ignore Exception
                }
            }
        }
    }
}
