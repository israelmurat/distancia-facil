package br.com.muratsystems.distanciafacil.domain.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import br.com.muratsystems.distanciafacil.domain.service.GeoLocalizacaoService;

public class GeoLocalizacaoServiceTest {

//	@Autowired
	private GeoLocalizacaoService service = new GeoLocalizacaoService();
	
	@Test
	public void definirGeoLocalizacaoPorEnderecoTest() {
		Endereco endereco = new Endereco();
		endereco.setTipoLogradouro("AV.");
		endereco.setLogradouro("Rio Branco");
		endereco.setNumero(1);
		endereco.setBairro("Centro");
		endereco.setCidade("Rio de Janeiro");
		endereco.setUf("RJ");
		endereco.setCep("20090003");
		service.definirGeoLocalizacaoPorEndereco(endereco);
		assertNotNull(endereco.getGeoLocalizacao(), "Localização não encontrada!");
		if (endereco.getGeoLocalizacao() != null) {
			assertNotNull(endereco.getGeoLocalizacao().getLatitude(), "Latitude não encontrada!");
			assertNotNull(endereco.getGeoLocalizacao().getLongitude(), "Longitude não encontrada!");
		}
	}
	
}
