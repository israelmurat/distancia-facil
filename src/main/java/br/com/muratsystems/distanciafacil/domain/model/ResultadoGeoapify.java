package br.com.muratsystems.distanciafacil.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class ResultadoGeoapify {

	private String lat;
	private String lon;
	
}
