package com.uexcel.reservationservice.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.serialization.Serializer;
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
                "com.uexcel.reservationservice.command.*",
                "com.uexcel.reservationservice.query.*",
                "com.uexcel.reservationservice.command.controller.*",
                "com.uexcel.reservationservice.saga.ReservationSaga",
                "java.util.*", "java.lang.*"
        });
        return xStream;
    }

    @Bean
    public Serializer messageSerializer(XStream xStream) {
        return XStreamSerializer.builder().xStream(xStream).build();
    }


}
