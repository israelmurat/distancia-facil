package br.com.muratsystems.distanciafacil.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @Getter @Setter 
@ToString @EqualsAndHashCode
public class Endereco {

	private String tipoLogradouro;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String cidade;
	private String uf;
	private String pais;
	private String cep;
	private Coordenada latitude;
	private Coordenada longitude;
	
}
