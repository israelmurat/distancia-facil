package br.com.muratsystems.distanciafacil.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor 
@Getter @Setter 
@ToString @EqualsAndHashCode
public class EnderecoDto {

	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;
	private String uf;
	private String pais;
	private String cep;
	
}
