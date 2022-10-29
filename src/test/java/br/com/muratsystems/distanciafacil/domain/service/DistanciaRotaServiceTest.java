package br.com.muratsystems.distanciafacil.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.muratsystems.distanciafacil.domain.model.DistanciaRota;
import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.domain.model.GeoLocalizacao;

@SpringBootTest
public class DistanciaRotaServiceTest {

	@Autowired
	private DistanciaRotaService service;

	@Test
	public void calcularDistanciaEnderecosTest() {
		var endereco1 = new Endereco();
		endereco1.setLogradouro("Avenida Rio Branco");
		endereco1.setGeoLocalizacao(
				new GeoLocalizacao(new BigDecimal("-22.89750765"), new BigDecimal("-43.18020490826943")));
		var endereco2 = new Endereco();
		endereco2.setLogradouro("Praça Mal. Âncora");
		endereco2.setGeoLocalizacao(new GeoLocalizacao(new BigDecimal("-22.9045592"), new BigDecimal("-43.1697526")));
		var endereco3 = new Endereco();
		endereco3.setLogradouro("Rua Lauro Muller");
		endereco3.setGeoLocalizacao(new GeoLocalizacao(new BigDecimal("-22.9573161"), new BigDecimal("-43.176043")));
		List<Endereco> enderecos = List.of(endereco1, endereco2, endereco3);
		List<DistanciaRota> distancias = service.calcularDistanciaEnderecos(enderecos);
		assertEquals(false, distancias.isEmpty());
//		assertEquals(new BigDecimal("5"), distancias.get(0).getDistanciaEntreEnderecos());
//		assertEquals(new BigDecimal("5.5"), distancias.get(1).getDistanciaEntreEnderecos());
//		assertEquals(new BigDecimal("8.1"), distancias.get(2).getDistanciaEntreEnderecos());
	}

	@Test
	public void calcularDistanciaEmKmEntrePontosTest() {
		var enderecoA = new Endereco();
//		endereco1.setLogradouro("Avenida Rio Branco");
//		endereco1.setGeoLocalizacao(
//				new GeoLocalizacao(new BigDecimal("-22.89750765"), new BigDecimal("-43.18020490826943")));
		enderecoA.setLogradouro("Rua Jose Luiz Flaquer");
		enderecoA.setCidade("Sorocaba");
		enderecoA.setUf("SP");
		enderecoA.setGeoLocalizacao(
				new GeoLocalizacao(new BigDecimal("-23.417813285604964"), new BigDecimal("-47.4152675812739")));
		var enderecoB = new Endereco();
		enderecoB.setLogradouro("Praça Mal. Âncora");
		enderecoA.setCidade("Rio de Janeiro");
		enderecoA.setUf("RJ");
		enderecoB.setGeoLocalizacao(new GeoLocalizacao(new BigDecimal("-22.9045592"), new BigDecimal("-43.1697526")));
		var rota = new DistanciaRota();
		rota.setEnderecoA(enderecoA);
		rota.setEnderecoB(enderecoB);
		service.calcularDistanciaEmKmEntrePontos(rota);
		assertNotNull(rota.getDistanciaEntreEnderecos());
		assertNotNull(rota.getUnidadeDistancia());

	}

}
