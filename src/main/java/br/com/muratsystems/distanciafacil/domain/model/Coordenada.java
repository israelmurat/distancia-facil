package br.com.muratsystems.distanciafacil.domain.model;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class Coordenada {

	private Integer grau;
	private BigDecimal minuto;
	private String direcao;
	
}
