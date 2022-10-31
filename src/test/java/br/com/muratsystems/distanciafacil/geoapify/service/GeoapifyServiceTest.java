package br.com.muratsystems.distanciafacil.geoapify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.muratsystems.distanciafacil.geoapify.model.Rank;
import br.com.muratsystems.distanciafacil.geoapify.model.ResultGeocoding;

@SpringBootTest
public class GeoapifyServiceTest {

	@Autowired
	private GeoapifyService service;
	
	@Test
	public void getGeocodingGeoapifyTest() {
		String endereco = "Av. Rio Branco, 1 Centro, Rio de Janeiro RJ, 20090003";
		var optResultado = service.getGeocodingGeoapify(endereco);
		assertEquals(true, optResultado.isPresent());
		assertEquals("Avenida Rio Branco", optResultado.get().getStreet());
		assertEquals("Rio de Janeiro", optResultado.get().getCity());
		assertEquals(new BigDecimal("-22.89750765"), optResultado.get().getLat());
		assertEquals(new BigDecimal("-43.18020490826943"), optResultado.get().getLon());
	}
	
	@Test
	public void getResultadoConfiavelOkTest() {
		var resultado = new ResultGeocoding();
		resultado.setStreet("Rua A");
		var rank = new Rank();
		rank.setConfidence(BigDecimal.ONE);
		resultado.setRank(rank);
		Optional<ResultGeocoding> result = service.getResultadoConfiavel(List.of(resultado));
		assertEquals(true, result.isPresent());
	}
	
	@Test
	public void getResultadoConfiavelParcialmenteTest() {
		var resultado = new ResultGeocoding();
		resultado.setStreet("Rua A");
		var rank = new Rank();
		rank.setConfidence(new BigDecimal("0.94"));
		rank.setConfidence_street_level(new BigDecimal("0.95"));
		rank.setConfidence_city_level(BigDecimal.ONE);
		resultado.setRank(rank);
		Optional<ResultGeocoding> result = service.getResultadoConfiavel(List.of(resultado));
		assertEquals(true, result.isPresent());
	}
	
	@Test
	public void getResultadoConfiavelRecusadoTest() {
		var resultado = new ResultGeocoding();
		resultado.setStreet("Rua A");
		var rank = new Rank();
		rank.setConfidence(new BigDecimal("0.199"));
		resultado.setRank(rank);
		Optional<ResultGeocoding> result = service.getResultadoConfiavel(List.of(resultado));
		assertEquals(true, result.isEmpty());
	}
	
}
