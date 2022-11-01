package br.com.muratsystems.distanciafacil.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.muratsystems.distanciafacil.api.dto.DistanciaDto;
import br.com.muratsystems.distanciafacil.api.dto.EnderecoDto;
import br.com.muratsystems.distanciafacil.domain.exception.ValidationException;
import br.com.muratsystems.distanciafacil.domain.model.DistanciaRota;
import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.domain.service.DistanciaRotaService;
import br.com.muratsystems.distanciafacil.domain.service.GeoLocalizacaoService;
import br.com.muratsystems.distanciafacil.utils.StringUtil;

@RestController
@RequestMapping(value = "/distancias")
public class DistanciaController {

	@Autowired
	private GeoLocalizacaoService geoLocalizacaoService;

	@Autowired
	private DistanciaRotaService distanciaRotaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<DistanciaDto> calcularDistanciasPorUrl(@RequestBody List<String> enderecosString) {
		validarEnderecos(enderecosString);
		List<DistanciaRota> distanciasRota = calcularDistancias(enderecosString);
		return ResponseEntity.ok(geoLocalizacaoService.definirDistanciaRetorno(distanciasRota));
	}

	@PostMapping(value = "/enderecos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<DistanciaDto> calcularDistanciasPorEnderecos(@RequestBody List<EnderecoDto> enderecosDto) {
		List<String> enderecosString = new ArrayList<>();
		enderecosDto.forEach((e) -> enderecosString.add(enderecoParaString(e)));
		validarEnderecos(enderecosString);
		List<DistanciaRota> distanciasRota = calcularDistancias(enderecosString);
		return ResponseEntity.ok(geoLocalizacaoService.definirDistanciaRetorno(distanciasRota));
	}

	private List<DistanciaRota> calcularDistancias(List<String> enderecosString) {
		List<Endereco> enderecos = geoLocalizacaoService.definirEnderecos(enderecosString);
		return distanciaRotaService.calcularDistanciaEnderecos(enderecos);
	}

	private String enderecoParaString(EnderecoDto endereco) {
		if (endereco != null) {
			String enderecoString = "";
			if (!StringUtil.isNullOrEmpty(endereco.getLogradouro()))
				enderecoString += endereco.getLogradouro() + ", ";
			if (!StringUtil.isNullOrEmpty(endereco.getNumero()))
				enderecoString += endereco.getNumero() + ", ";
			if (!StringUtil.isNullOrEmpty(endereco.getBairro()))
				enderecoString += endereco.getBairro() + ", ";
			if (!StringUtil.isNullOrEmpty(endereco.getCidade()))
				enderecoString += endereco.getCidade() + ", ";
			if (!StringUtil.isNullOrEmpty(endereco.getUf()))
				enderecoString += endereco.getUf() + ", ";
			if (!StringUtil.isNullOrEmpty(endereco.getPais()))
				enderecoString += endereco.getPais() + ", ";
			if (!StringUtil.isNullOrEmpty(endereco.getCep()))
				enderecoString += endereco.getCep() + ", ";

			enderecoString = enderecoString.trim();

			if (enderecoString.endsWith(",")) {
				return enderecoString.substring(0, enderecoString.length() - 1);
			}

			return enderecoString;
		}
		throw new ValidationException("Endereço inválido");
	}

	private void validarEnderecos(List<String> enderecosString) {
		enderecosString.forEach((e) -> {
			if (StringUtil.isNullOrEmpty(e)) {
				throw new ValidationException("Um ou mais endereços estão inválidos!");
			}
		});
	}

}
