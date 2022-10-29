package br.com.muratsystems.distanciafacil.geoapify.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GeoapifyServiceTest {

	@Autowired
	private GeoapifyService service;
	
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
