package br.com.muratsystems.distanciafacil.domain.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class Geoapify {

	private List<ResultadoGeoapify> results;
	
}
