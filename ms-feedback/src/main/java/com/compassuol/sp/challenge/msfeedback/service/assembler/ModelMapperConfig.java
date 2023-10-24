package com.compassuol.sp.challenge.msfeedback.service.assembler;

import com.compassuol.sp.challenge.msfeedback.jacoco.ExcludeFromJacocoGeneratedReport;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    @ExcludeFromJacocoGeneratedReport
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
