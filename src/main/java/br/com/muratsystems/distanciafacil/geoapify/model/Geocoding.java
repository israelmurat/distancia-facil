package br.com.muratsystems.distanciafacil.geoapify.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class Geocoding {

	private List<ResultGeocoding> results;
	
}
