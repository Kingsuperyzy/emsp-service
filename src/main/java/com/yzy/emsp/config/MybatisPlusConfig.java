package com.yzy.emsp.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.yzy.emsp.domain.AccountStatus;
import com.yzy.emsp.domain.CardStatus;
import com.yzy.emsp.domain.CardType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.type.EnumTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

@Configuration
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.getTypeHandlerRegistry()
                    .register(AccountStatus.class, new EnumTypeHandler<>(AccountStatus.class));
            configuration.getTypeHandlerRegistry()
                    .register(CardStatus.class, new EnumTypeHandler<>(CardStatus.class));
            configuration.getTypeHandlerRegistry()
                    .register(CardType.class, new EnumTypeHandler<>(CardType.class));
        };
    }

    @Component
    public static class DateMetaObjectHandler implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            this.setFieldValByName("createTime", new Date(), metaObject);
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.setFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}