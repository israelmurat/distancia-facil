package br.com.muratsystems.distanciafacil.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.muratsystems.distanciafacil.domain.model.DistanciaRota;
import br.com.muratsystems.distanciafacil.domain.model.Endereco;

@SpringBootTest
public class GeoLocalizacaoServiceTest {

	@Autowired
	private GeoLocalizacaoService service;

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
			assertEquals(new BigDecimal("-22.89750765"), enderecos.get(0).getCoordenada().getLatitude());
			assertEquals(new BigDecimal("-43.18020490826943"), enderecos.get(0).getCoordenada().getLongitude());
			assertEquals("Praça Marechal Âncora", enderecos.get(1).getLogradouro());
			assertEquals("Centro", enderecos.get(1).getBairro());
			assertEquals("Rio de Janeiro", enderecos.get(1).getCidade());
			assertEquals(new BigDecimal("-22.9045592"), enderecos.get(1).getCoordenada().getLatitude());
			assertEquals(new BigDecimal("-43.1697526"), enderecos.get(1).getCoordenada().getLongitude());
			assertEquals("Rua Lauro Müller", enderecos.get(2).getLogradouro());
			assertEquals("Botafogo", enderecos.get(2).getBairro());
			assertEquals("Rio de Janeiro", enderecos.get(2).getCidade());
			assertEquals(new BigDecimal("-22.9573161"), enderecos.get(2).getCoordenada().getLatitude());
			assertEquals(new BigDecimal("-43.176043"), enderecos.get(2).getCoordenada().getLongitude());
		}
	}

	@Test
	public void definirEnderecoComGeoLocalizacaoTest() {
		String endereco = "Av. Rio Branco, 1 Centro, Rio de Janeiro RJ, 20090003";
		var optEndereco = service.definirEnderecoComCoordenada(endereco);
		assertEquals(true, optEndereco.isPresent());
		assertEquals("Avenida Rio Branco", optEndereco.get().getLogradouro());
		assertEquals("Rio de Janeiro", optEndereco.get().getCidade());
		assertEquals(new BigDecimal("-22.89750765"), optEndereco.get().getCoordenada().getLatitude());
		assertEquals(new BigDecimal("-43.18020490826943"), optEndereco.get().getCoordenada().getLongitude());
	}

	@Test
	public void definirDistanciaRetornoTest() {

		var rota1 = new DistanciaRota();
		rota1.setEnderecoA(new Endereco("Avenida Rio Branco"));
		rota1.setEnderecoB(new Endereco("Praça Mal. Âncora"));
		rota1.setDistanciaEntreEnderecos(new BigDecimal("5"));

		var rota2 = new DistanciaRota();
		rota2.setEnderecoA(new Endereco("Avenida Rio Branco"));
		rota2.setEnderecoB(new Endereco("Rua Lauro Muller"));
		rota2.setDistanciaEntreEnderecos(new BigDecimal("7"));

		var rota3 = new DistanciaRota();
		rota3.setEnderecoA(new Endereco("Praça Mal. Âncora"));
		rota3.setEnderecoB(new Endereco("Rua Lauro Muller"));
		rota3.setDistanciaEntreEnderecos(new BigDecimal("4.99"));

		var distanciaDto = service.definirDistanciaRetorno(List.of(rota1, rota2, rota3));
		assertNotNull(distanciaDto);
		assertEquals(3, distanciaDto.getDistancias().size());
		assertEquals(new BigDecimal("7"), distanciaDto.getDistanciaMaisDistante().getDistanciaEntreEnderecos());
		assertEquals(new BigDecimal("4.99"), distanciaDto.getDistanciaMaisProxima().getDistanciaEntreEnderecos());

	}

}
