package com.example.cooperativa_votacao.core;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//tem o objetico de definição de configuração de beans 
@Configuration
public class ModelMapperConfig {
	
	// indica que ele inicializa por um modo bean gerenciado pelo spring
	//e que vai ser disponibilizado para injeção em outras classes 
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
