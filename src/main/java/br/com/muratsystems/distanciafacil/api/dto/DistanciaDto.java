package br.com.muratsystems.distanciafacil.api.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class DistanciaDto {

	private List<DistanciaRotaDto> distancias;
	private DistanciaRotaDto distanciaMaisProxima;
	private DistanciaRotaDto distanciaMaisDistante;
	
}
