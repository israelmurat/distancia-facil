package br.com.muratsystems.distanciafacil.domain.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.domain.model.GeoLocalizacao;
import br.com.muratsystems.distanciafacil.domain.model.Geoapify;

@Service
public class GeoLocalizacaoService {
	
	public void definirGeoLocalizacaoPorEndereco(Endereco endereco) {
		try {
			String urlString = "https://api.geoapify.com/v1/geocode/search?text=" 
					+ endereco.getTipoLogradouro() + "%20" 
					+ endereco.getLogradouro().replace(" ", "%20") +"%2C%20"
					+ endereco.getNumero() + "%20" 
					+ endereco.getBairro().replace(" ", "%20") + "%2C%20" 
					+ endereco.getCidade().replace(" ", "%20") + "%2C%20" 
					+ endereco.getUf() + "%2C%20" 
					+ endereco.getCep() + "&format=json&apiKey=7f7d03b570294eb9ad3d6409058fdb6b";
			
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create(urlString))
			      .header("Content-Type", "application/json")
			      .build();

			HttpResponse<String> response =
			      client.send(request, BodyHandlers.ofString());

			Gson gson = new Gson();
			Optional<Geoapify> optGeoapify = Optional.of(gson.fromJson(response.body(), Geoapify.class));
			if (optGeoapify.isPresent()) {
				GeoLocalizacao geoLocalizacao = new GeoLocalizacao();
				geoLocalizacao.setLatitude(new BigDecimal(optGeoapify.get().getResults().get(0).getLat()));
				geoLocalizacao.setLongitude(new BigDecimal(optGeoapify.get().getResults().get(0).getLon()));
				endereco.setGeoLocalizacao(geoLocalizacao);
			}

		} catch (IOException | NumberFormatException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
