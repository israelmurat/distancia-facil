package br.com.muratsystems.distanciafacil.api.controller;

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
import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.domain.service.DistanciaRotaService;
import br.com.muratsystems.distanciafacil.domain.service.GeoLocalizacaoService;

@RestController
@RequestMapping(value = "/distancias")
public class DistanciaController {

	@Autowired
	private GeoLocalizacaoService geoLocalizacaoService;
	
	@Autowired
	private DistanciaRotaService distanciaRotaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) 
	public ResponseEntity<DistanciaDto> calcularDistancias(@RequestBody List<String> enderecosString) {
		List<Endereco> enderecos = geoLocalizacaoService.definirEnderecos(enderecosString);
		
		return ResponseEntity.ok(new DistanciaDto());
	}
	
}
