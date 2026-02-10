package com.uexcel.roomservice.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;

import jakarta.persistence.EntityManager;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.modelling.saga.repository.jpa.JpaSagaStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.serialization.xml.XStreamSerializer;

import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AxonConfig {
    @Bean
    public TransactionManager axonTransactionManager(PlatformTransactionManager ptm) {
        return new SpringTransactionManager(ptm);

    }

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.addPermission(NoTypePermission.NONE);
        xStream.allowTypesByWildcard(new String[]{
                "com.uexcel.common.command.*",
                "com.uexcel.common.event.*",
                "com.uexcel.common.command.error.*",
                "com.uexcel.roomservice.command.*",
                "com.uexcel.roomservice.command.room.*",
                "com.uexcel.roomservice.command.roomtype.*",
                "com.uexcel.roomservice.error.*",
                "com.uexcel.roomservice.command.interceptor.*",
                "com.uexcel.roomservice.query.reservationfordate.*",
                "com.uexcel.roomservice.query.room.*",
                "com.uexcel.roomservice.query.*",
                "com.uexcel.roomservice.entity.*",
                "com.uexcel.roomservice.query.controller.*",
                "com.uexcel.roomservice.command.controller.*",
                "com.uexcel.roomservice.command.inventory.*",
                "java.util.*", "java.lang.*"

        });
        return xStream;
    }

    @Bean
    public Serializer messageSerializer(XStream xStream) {
        return XStreamSerializer.builder().xStream(xStream).build();
    }
}