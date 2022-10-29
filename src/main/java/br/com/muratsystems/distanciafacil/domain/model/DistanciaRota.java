package br.com.muratsystems.distanciafacil.domain.model;

import java.math.BigDecimal;

import br.com.muratsystems.distanciafacil.domain.enuns.UnidadeDistancia;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class DistanciaRota {

	private Endereco enderecoA;
	private Endereco enderecoB;
	private BigDecimal distanciaEntreEnderecos;
	private UnidadeDistancia unidadeDistancia;
	
}
