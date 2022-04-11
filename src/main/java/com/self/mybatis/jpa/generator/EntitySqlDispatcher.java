package com.self.mybatis.jpa.generator;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EntitySqlDispatcher {

    private static final Map<Class, MetaDataParser> metaDataParseMap = new ConcurrentHashMap<>();
    private static final Map<String, MetaDataParser> namespaceDataParserMap = new ConcurrentHashMap<>();
    private static final EntitySqlDispatcher instance = new EntitySqlDispatcher();

    public EntitySqlDispatcher() {
    }

    public static EntitySqlDispatcher getInstance() {
        return instance;
    }


    public Set<Class> getEntities() {
        return Collections.unmodifiableSet(metaDataParseMap.keySet());
    }

    public MetaDataParser getMetaDataParser(Class clazz) {
        return metaDataParseMap.get(clazz);
    }
}
