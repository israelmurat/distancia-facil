package br.com.muratsystems.distanciafacil.geoapify.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class Parsed {

	private String housenumber;
	private String street;
	private String suburb;
	private String postcode;
	private String city;
	private String state;
	private String country;
	
}
