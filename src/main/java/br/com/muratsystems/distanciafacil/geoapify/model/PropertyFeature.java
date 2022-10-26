package br.com.muratsystems.distanciafacil.geoapify.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class PropertyFeature {

	private String mode;
	private String distance;
	private String distance_units;
	
}
