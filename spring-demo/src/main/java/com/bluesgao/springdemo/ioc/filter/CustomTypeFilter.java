package com.bluesgao.springdemo.ioc.filter;

import com.bluesgao.springdemo.ioc.filter.entity.color.Green;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * @ClassName：CustomTypeFilter
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 15:18
 **/
public class CustomTypeFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //自定义过滤规则，判断类名是否与green相同
        return classMetadata.getClassName().equals(Green.class.getName());
    }
}
