package com.example.pkisecurity;

import com.example.pkisecurity.repository.keystores.KeyStoreReader;
import com.example.pkisecurity.repository.keystores.KeyStoreWriter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PkiSecurityApplication {

    public static KeyStoreReader keyStoreReader;
    public static KeyStoreWriter keyStoreWriter;
    public static ApplicationContext context;

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    public static void main(String[] args) {
        context = SpringApplication.run(PkiSecurityApplication.class, args);

        keyStoreReader = (KeyStoreReader) context.getBean("keyStoreReader");
        keyStoreWriter = (KeyStoreWriter) context.getBean("keyStoreWriter");
    }

}
