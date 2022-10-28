package br.com.muratsystems.distanciafacil.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.domain.service.GeoLocalizacaoService;

public class GeoLocalizacaoServiceTest {

//	@Autowired
	private GeoLocalizacaoService service = new GeoLocalizacaoService();

//
//	@Test
//	public void definirDistanciaEntreEnderecosTest() {
//		
//		var enderecoA = new Endereco();
//		var geoLocalizacaoA = new GeoLocalizacao();
//		geoLocalizacaoA.setLatitude(new BigDecimal("-22.89750765"));
//		geoLocalizacaoA.setLongitude(new BigDecimal("-43.18020490826943"));
//		enderecoA.setGeoLocalizacao(geoLocalizacaoA);
//		
//		var enderecoB = new Endereco();
//		var geoLocalizacaoB = new GeoLocalizacao();
//		geoLocalizacaoB.setLatitude(new BigDecimal("-22.9045592"));
//		geoLocalizacaoB.setLongitude(new BigDecimal("-43.1697526"));
//		enderecoB.setGeoLocalizacao(geoLocalizacaoB);
//
//		var distanciaRota = service.definirDistanciaEntreEnderecos(enderecoA, enderecoB);
//		assertEquals(new BigDecimal("86.825"), distanciaRota.getDistanciaEntreEnderecos());
//
//	}
	
	@Test
	public void definirEnderecosTest() {
		List<String> enderecosString = new ArrayList<>();
		enderecosString.add("Av. Rio Branco, 1 Centro, Rio de Janeiro RJ, 20090003");
		enderecosString.add("Praça Mal. Âncora, 122 Centro, Rio de Janeiro RJ, 20021200");
		enderecosString.add("Rua Lauro Muller 116, Botafogo, Rio de janeiro,RJ, 22290160");
		List<Endereco> enderecos = service.definirEnderecos(enderecosString);
		assertEquals(false, enderecos.isEmpty());
		assertEquals(3, enderecos.size());
		if (!enderecos.isEmpty() && enderecos.size() == 3) {
			assertEquals("Avenida Rio Branco", enderecos.get(0).getLogradouro());
			assertEquals("Centro", enderecos.get(0).getBairro());
			assertEquals("Rio de Janeiro", enderecos.get(0).getCidade());
			assertEquals(new BigDecimal("-22.89750765"), enderecos.get(0).getGeoLocalizacao().getLatitude());
			assertEquals(new BigDecimal("-43.18020490826943"), enderecos.get(0).getGeoLocalizacao().getLongitude());
			assertEquals("Praça Marechal Âncora", enderecos.get(1).getLogradouro());
			assertEquals("Centro", enderecos.get(1).getBairro());
			assertEquals("Rio de Janeiro", enderecos.get(1).getCidade());
			assertEquals(new BigDecimal("-22.9045592"), enderecos.get(1).getGeoLocalizacao().getLatitude());
			assertEquals(new BigDecimal("-43.1697526"), enderecos.get(1).getGeoLocalizacao().getLongitude());
			assertEquals("Rua Lauro Müller", enderecos.get(2).getLogradouro());
			assertEquals("Botafogo", enderecos.get(2).getBairro());
			assertEquals("Rio de Janeiro", enderecos.get(2).getCidade());
			assertEquals(new BigDecimal("-22.9573161"), enderecos.get(2).getGeoLocalizacao().getLatitude());
			assertEquals(new BigDecimal("-43.176043"), enderecos.get(2).getGeoLocalizacao().getLongitude());
		}
	}
	
	@Test
	public void definirEnderecoComGeoLocalizacaoTest() {
		String endereco = "Av. Rio Branco, 1 Centro, Rio de Janeiro RJ, 20090003";
		var optEndereco = service.definirEnderecoComGeoLocalizacao(endereco);
		assertEquals(true, optEndereco.isPresent());
		assertEquals("Avenida Rio Branco", optEndereco.get().getLogradouro());
		assertEquals("Rio de Janeiro", optEndereco.get().getCidade());
		assertEquals(new BigDecimal("-22.89750765"), optEndereco.get().getGeoLocalizacao().getLatitude());
		assertEquals(new BigDecimal("-43.18020490826943"), optEndereco.get().getGeoLocalizacao().getLongitude());
	}
	
	@Test
	public void getGeocodingGeoapifyTest() {
		String endereco = "Av. Rio Branco, 1 Centro, Rio de Janeiro RJ, 20090003";
		var optGeocoding = service.getGeocodingGeoapify(endereco);
		assertEquals(true, optGeocoding.isPresent());
		assertEquals("Avenida Rio Branco", optGeocoding.get().getResults().get(0).getStreet());
		assertEquals("Rio de Janeiro", optGeocoding.get().getResults().get(0).getCity());
		assertEquals(new BigDecimal("-22.89750765"), optGeocoding.get().getResults().get(0).getLat());
		assertEquals(new BigDecimal("-43.18020490826943"), optGeocoding.get().getResults().get(0).getLon());
	}

}
