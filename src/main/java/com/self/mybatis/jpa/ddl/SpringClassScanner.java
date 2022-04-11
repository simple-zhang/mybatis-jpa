package com.self.mybatis.jpa.ddl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class扫描器，封装了Spring资源解析器{@ResourcePatternResolver}</br>
 */
public class SpringClassScanner {

    public static final String RESOURCE_PATTERN = "**/*.class";

    private Set<TypeFilter> typeFilters;
    private Set<String> scanPackages;

    private boolean filterFlag = true;

    public SpringClassScanner() {
        scanPackages = new HashSet<>();
        typeFilters = new HashSet<>();
    }

    public Set<Class<?>> scan() throws IOException, ClassNotFoundException {
        Set<Class<?>> set = new HashSet<>();
        if (!this.scanPackages.isEmpty()) {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory(resolver);
            for (String scanPackage : this.scanPackages) {
                String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(scanPackage))
                        + "/"
                        + RESOURCE_PATTERN;
                Resource[] resources = resolver.getResources(packageSearchPath);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader reader = factory.getMetadataReader(resource);
                        String className = reader.getClassMetadata().getClassName();
                        if (matched(reader, factory)) {
                            set.add(Class.forName(className));
                        }
                    }
                }
            }
        }

        return set;
    }

    private boolean matched(MetadataReader reader, CachingMetadataReaderFactory factory) throws IOException {
        if (filterFlag) {
            return filterAll(reader, factory);
        } else {
            return filterWhether(reader, factory);
        }
    }

    private boolean filterWhether(MetadataReader reader, CachingMetadataReaderFactory factory) throws IOException {
        if (!this.typeFilters.isEmpty()) {
            for (TypeFilter typeFilter : this.typeFilters) {
                if (typeFilter.match(reader, factory)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean filterAll(MetadataReader reader, CachingMetadataReaderFactory factory) throws IOException {
        if (!this.typeFilters.isEmpty()) {
            for (TypeFilter typeFilter : this.typeFilters) {
                if (!typeFilter.match(reader, factory)) {
                    return false;

                }
            }

            return true;
        }

        return false;
    }

    public static class Builder {

        private SpringClassScanner scanner = new SpringClassScanner();

        public Builder scanPackage(String scanPackage) {
            this.scanner.getScanPackage().add(scanPackage);
            return this;
        }

        public Builder typeFilter(TypeFilter typeFilter) {
            this.scanner.getTypeFilters().add(typeFilter);
            return this;
        }

        public SpringClassScanner build() {
            return this.scanner;
        }

    }

    public Set<TypeFilter> getTypeFilters() {
        return typeFilters;
    }

    public Set<String> getScanPackage() {
        return scanPackages;
    }
}
