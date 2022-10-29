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
		var optGeocoding = geoapifyService.getGeocodingGeoapify(enderecoString);
		if (optGeocoding.isPresent()) {

			///// Definir pelo rank

			endereco.setLogradouro(optGeocoding.get().getResults().get(0).getStreet());
			endereco.setNumero(optGeocoding.get().getResults().get(0).getHousenumber());
			endereco.setBairro(optGeocoding.get().getResults().get(0).getSuburb());
			endereco.setCidade(optGeocoding.get().getResults().get(0).getCity());
			endereco.setUf(optGeocoding.get().getResults().get(0).getState_code());
			endereco.setPais(optGeocoding.get().getResults().get(0).getCountry());
			endereco.setCep(optGeocoding.get().getResults().get(0).getPostcode());
			var coordenada = new Coordenada();
			coordenada.setLatitude(optGeocoding.get().getResults().get(0).getLat());
			coordenada.setLongitude(optGeocoding.get().getResults().get(0).getLon());
			endereco.setCoordenada(coordenada);
		}
		return Optional.of(endereco);
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
