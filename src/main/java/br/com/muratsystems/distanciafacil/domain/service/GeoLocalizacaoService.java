package br.com.muratsystems.distanciafacil.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.muratsystems.distanciafacil.api.dto.DistanciaDto;
import br.com.muratsystems.distanciafacil.api.dto.DistanciaRotaDto;
import br.com.muratsystems.distanciafacil.domain.exception.BusinessException;
import br.com.muratsystems.distanciafacil.domain.model.Coordenada;
import br.com.muratsystems.distanciafacil.domain.model.DistanciaRota;
import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.geoapify.model.ResultGeocoding;
import br.com.muratsystems.distanciafacil.geoapify.service.GeoapifyService;

@Service
public class GeoLocalizacaoService {

	@Autowired
	private GeoapifyService geoapifyService;

	@Autowired
	private ModelMapper modelMapper;

	public List<Endereco> definirEnderecos(List<String> enderecosString) {
		if (enderecosString == null || enderecosString.size() < 3) {
			throw new BusinessException("Devem ser informados no mínimo 3 endereços!");
		}
		List<Endereco> enderecos = new ArrayList<>();
		for (String enderecoString : enderecosString) {
			definirEnderecoComCoordenada(enderecoString).ifPresent((endereco) -> {
				enderecos.add(endereco);
			});
		}
		return enderecos;
	}

	public Optional<Endereco> definirEnderecoComCoordenada(String enderecoString) {
		var optResultado = geoapifyService.getGeocodingGeoapify(enderecoString);
		if (optResultado.isPresent()) {
			return Optional.of(definirEndereco(optResultado.get()));
		}
		throw new BusinessException("Endereço " + enderecoString + " não encontrado no serviço de Geocoding");
	}

	private Endereco definirEndereco(ResultGeocoding resultado) {
		var endereco = new Endereco();
		endereco.setLogradouro(resultado.getStreet());
		endereco.setNumero(resultado.getHousenumber());
		endereco.setBairro(resultado.getSuburb());
		endereco.setCidade(resultado.getCity());
		endereco.setUf(resultado.getState_code());
		endereco.setPais(resultado.getCountry());
		endereco.setCep(resultado.getPostcode());
		var coordenada = new Coordenada();
		coordenada.setLatitude(resultado.getLat());
		coordenada.setLongitude(resultado.getLon());
		endereco.setCoordenada(coordenada);
		return endereco;
	}

	public DistanciaDto definirDistanciaRetorno(List<DistanciaRota> distanciasRota) {
		if (!distanciasRota.isEmpty()) {
			var distanciaDto = new DistanciaDto();
			classificarRotas(distanciasRota, distanciaDto);
			return distanciaDto;
		}
		throw new BusinessException("Endereços não encontrados no serviço de Geocoding");
	}

	private void classificarRotas(List<DistanciaRota> distanciasRota, DistanciaDto distanciaDto) {
		List<DistanciaRotaDto> distancias = new ArrayList<>();
		var maisProxima = new DistanciaRota();
		var maisDistante = new DistanciaRota();
		for (var rota : distanciasRota) {
			if (distanciasRota.indexOf(rota) == 0) {
				maisProxima = rota;
				maisDistante = rota;
			} else {
				if (rota.getDistanciaEntreEnderecos().compareTo(maisProxima.getDistanciaEntreEnderecos()) < 0) {
					maisProxima = rota;
				}
				if (rota.getDistanciaEntreEnderecos().compareTo(maisDistante.getDistanciaEntreEnderecos()) > 0) {
					maisDistante = rota;
				}
			}
			distancias.add(toDistanciaRotaDto(rota));
		}
		distanciaDto.setDistancias(distancias);
		distanciaDto.setDistanciaMaisProxima(toDistanciaRotaDto(maisProxima));
		distanciaDto.setDistanciaMaisDistante(toDistanciaRotaDto(maisDistante));
	}

	private DistanciaRotaDto toDistanciaRotaDto(DistanciaRota entity) {
		return modelMapper.map(entity, DistanciaRotaDto.class);
	}

}
