package br.com.muratsystems.distanciafacil.geoapify.model;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class ResultGeocoding {

	private String housenumber;
	private String street;
	private String suburb;
	private String city;
	private String state_code;
	private String postcode;
	private String country;
	private BigDecimal lat;
	private BigDecimal lon;
	private Rank rank;
	
}
