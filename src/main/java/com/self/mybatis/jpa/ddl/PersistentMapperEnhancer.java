package com.self.mybatis.jpa.ddl;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * Persistent Mapper Enhancer
 */
public class PersistentMapperEnhancer extends BaseBuilder {

    private static final Logger logger = LoggerFactory.getLogger(PersistentMapperEnhancer.class);


    protected Class<?> mapper;
    protected MapperBuilderAssistant assistant;

    private Class entityClass;
    private Class idClass;
    private final Map<String, XNode> sqlFragments;

    public PersistentMapperEnhancer(Configuration configuration, Class<?> mapper) {
        super(configuration);
        String resource = mapper.getName().replace(".", "/") + ".java (best guess)";
        this.assistant = new MapperBuilderAssistant(configuration, resource);
        this.mapper = mapper;
        this.sqlFragments = configuration.getSqlFragments();
    }

    public void enhance() {
        Type[] types = this.mapper.getGenericInterfaces();
        if (Objects.isNull(types) || types.length <= 0) {
            return;

        }

    }
}
