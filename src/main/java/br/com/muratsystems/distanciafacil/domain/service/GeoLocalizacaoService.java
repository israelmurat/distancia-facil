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
import br.com.muratsystems.distanciafacil.geoapify.service.GeoapifyService;

@Service
public class GeoLocalizacaoService {

	@Autowired
	private GeoapifyService geoapifyService;

	@Autowired
	private ModelMapper modelMapper;

	public List<Endereco> definirEnderecos(List<String> enderecosString) {
		if (enderecosString == null || enderecosString.size() < 3) {
			throw new BusinessException("Deve ser informado no mínimo 3 endereços!");
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
		var endereco = new Endereco();
		var optResultado = geoapifyService.getGeocodingGeoapify(enderecoString);
		if (optResultado.isPresent()) {
			endereco.setLogradouro(optResultado.get().getStreet());
			endereco.setNumero(optResultado.get().getHousenumber());
			endereco.setBairro(optResultado.get().getSuburb());
			endereco.setCidade(optResultado.get().getCity());
			endereco.setUf(optResultado.get().getState_code());
			endereco.setPais(optResultado.get().getCountry());
			endereco.setCep(optResultado.get().getPostcode());
			var coordenada = new Coordenada();
			coordenada.setLatitude(optResultado.get().getLat());
			coordenada.setLongitude(optResultado.get().getLon());
			endereco.setCoordenada(coordenada);
			return Optional.of(endereco);
		}
		throw new BusinessException("Endereço " + enderecoString + " não encontrado no serviço de Geocoding");
	}

	public DistanciaDto definirDistanciaRetorno(List<DistanciaRota> distanciasRota) {
		if (!distanciasRota.isEmpty()) {
			var distanciaDto = new DistanciaDto();
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
			return distanciaDto;
		}
		throw new BusinessException("Endereços não encontrados no serviço de Geocoding");
	}

	private DistanciaRotaDto toDistanciaRotaDto(DistanciaRota entity) {
		return modelMapper.map(entity, DistanciaRotaDto.class);
	}

}
