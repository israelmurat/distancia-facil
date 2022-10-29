package br.com.muratsystems.distanciafacil.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter 
@ToString @EqualsAndHashCode
public class Coordenada {

	private BigDecimal latitude;
	private BigDecimal longitude;
	
}
