package br.com.muratsystems.distanciafacil.domain.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocalizacao {

	@JsonProperty("lat")
	private BigDecimal latitude;
	
	@JsonProperty("lon")
	private BigDecimal longitude;
	
}
