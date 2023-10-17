package com.compassuol.sp.challenge.msproducts.service.assembler;

import com.compassuol.sp.challenge.msproducts.jacoco.ExcludeFromJacocoGeneratedReport;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.compassuol.sp.challenge.msproducts")
public class ModelMapperConfig {
    @Bean
    @ExcludeFromJacocoGeneratedReport
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
