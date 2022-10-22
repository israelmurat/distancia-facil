package br.com.muratsystems.distanciafacil.domain.model;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class GeoLocalizacao {

	private BigDecimal latitude;
	private BigDecimal longitude;
	
}
