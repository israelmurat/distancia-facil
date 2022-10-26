package br.com.muratsystems.distanciafacil.domain.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.muratsystems.distanciafacil.domain.exception.BusinessException;
import br.com.muratsystems.distanciafacil.domain.model.DistanciaRota;
import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.domain.model.GeoLocalizacao;
import br.com.muratsystems.distanciafacil.geoapify.model.Geocoding;
import br.com.muratsystems.distanciafacil.geoapify.model.Routing;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class GeoLocalizacaoService {
	
	private static final String CHAVE_API = "7f7d03b570294eb9ad3d6409058fdb6b";
	
	private Gson gson = new Gson();

	public void definirGeoLocalizacaoPorEndereco(Endereco endereco) {
		String url = "https://api.geoapify.com/v1/geocode/search?text=" + endereco.getTipoLogradouro() + "%20"
				+ endereco.getLogradouro().replace(" ", "%20") + "%2C%20" + endereco.getNumero() + "%20"
				+ endereco.getBairro().replace(" ", "%20") + "%2C%20" + endereco.getCidade().replace(" ", "%20")
				+ "%2C%20" + endereco.getUf() + "%2C%20" + endereco.getCep()
				+ "&format=json&apiKey=" + CHAVE_API;

		Optional<Geocoding> optGeocoding = Optional.of(gson.fromJson(getJsonGeoapify(url).body(), Geocoding.class));
		if (optGeocoding.isPresent()) {
			var geoLocalizacao = new GeoLocalizacao();
			geoLocalizacao.setLatitude(new BigDecimal(optGeocoding.get().getResults().get(0).getLat()));
			geoLocalizacao.setLongitude(new BigDecimal(optGeocoding.get().getResults().get(0).getLon()));
			endereco.setGeoLocalizacao(geoLocalizacao);
			// Ver se vai ter exception para o BigDecimal
		}
	}

	public DistanciaRota definirDistanciaEntreEnderecos(Endereco enderecoA, Endereco enderecoB) {
		String url = "https://api.geoapify.com/v1/routing?waypoints="
				+ enderecoA.getGeoLocalizacao().getLatitude() + "%2C" 
				+ enderecoA.getGeoLocalizacao().getLongitude() + "%7C" 
				+ enderecoB.getGeoLocalizacao().getLatitude() + "%2C" 
				+ enderecoB.getGeoLocalizacao().getLongitude()
				+ "&mode=drive&apiKey=" + CHAVE_API;	
		Optional<Routing> optRouting = Optional.of(gson.fromJson(teste(url).body().toString(), Routing.class));
		if (optRouting.isPresent()) {
			var distanciaRota = new DistanciaRota();
			var propertyFeature = optRouting.get().getFeatures().get(0).getProperties().get(0);
			distanciaRota.setEnderecoA(enderecoA);
			distanciaRota.setEnderecoB(enderecoB);
			distanciaRota.setDistanciaEntreEnderecos(
					new BigDecimal(propertyFeature.getDistance())
					.divide(new BigDecimal("1000"), 3 , RoundingMode.HALF_EVEN));
			distanciaRota.setUnidadeDistancia("KM");
			// Ver se vai ter exception para o BigDecimal
			return distanciaRota;
		}
		throw new BusinessException("Rota não encontrada!");
	}

	private HttpResponse<String> getJsonGeoapify(String url) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
					.header("Content-Type", "application/json").build();

			return client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Response teste(String url) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					 .build();
			Request request = new Request.Builder()
			 .url(url)
			 .method("GET", null)
			 .build();
			return client.newCall(request).execute();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
