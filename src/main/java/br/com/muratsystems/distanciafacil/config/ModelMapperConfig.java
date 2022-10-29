package br.com.muratsystems.distanciafacil.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper getModelMapper() {
		var modelMapper = new ModelMapper();
		return modelMapper;
	}
	
}
