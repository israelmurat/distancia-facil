package br.com.muratsystems.distanciafacil.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.muratsystems.distanciafacil.domain.service.GeoLocalizacaoService;

public class GeoLocalizacaoServiceTest {

//	@Autowired
	private GeoLocalizacaoService service = new GeoLocalizacaoService();

	@Test
	public void definirGeoLocalizacaoPorEnderecoTest() {
		var endereco = new Endereco();
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
			if (endereco.getGeoLocalizacao().getLatitude() != null
					&& endereco.getGeoLocalizacao().getLongitude() != null) {
				assertEquals(new BigDecimal("-22.89750765"), endereco.getGeoLocalizacao().getLatitude());
				assertEquals(new BigDecimal("-43.18020490826943"), endereco.getGeoLocalizacao().getLongitude());
			}
		}
	}

	@Test
	public void definirDistanciaEntreEnderecosTest() {
		
		var enderecoA = new Endereco();
		var geoLocalizacaoA = new GeoLocalizacao();
		geoLocalizacaoA.setLatitude(new BigDecimal("-22.89750765"));
		geoLocalizacaoA.setLongitude(new BigDecimal("-43.18020490826943"));
		enderecoA.setGeoLocalizacao(geoLocalizacaoA);
		
		var enderecoB = new Endereco();
		var geoLocalizacaoB = new GeoLocalizacao();
		geoLocalizacaoB.setLatitude(new BigDecimal("-22.9045592"));
		geoLocalizacaoB.setLongitude(new BigDecimal("-43.1697526"));
		enderecoB.setGeoLocalizacao(geoLocalizacaoB);

		var distanciaRota = service.definirDistanciaEntreEnderecos(enderecoA, enderecoB);
		assertEquals(new BigDecimal("86.825"), distanciaRota.getDistanciaEntreEnderecos());

	}

}
