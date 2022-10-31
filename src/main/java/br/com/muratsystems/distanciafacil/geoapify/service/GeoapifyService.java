package br.com.muratsystems.distanciafacil.geoapify.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.muratsystems.distanciafacil.geoapify.model.Geocoding;
import br.com.muratsystems.distanciafacil.geoapify.model.ResultGeocoding;

@Service
public class GeoapifyService {
	
	private static final String CHAVE_API = "7f7d03b570294eb9ad3d6409058fdb6b";
	
	@Autowired
	private Gson gson;

	public Optional<ResultGeocoding> getGeocodingGeoapify(String endereco) {
		endereco = Normalizer.normalize(endereco, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		String url = "https://api.geoapify.com/v1/geocode/search?text=";
		url += endereco.replace(".", "%2E").replace(" ", "%20").replace(",", "%2C").replace("/", "%2F");
		url += "&format=json&apiKey=" + CHAVE_API;
		Optional<Geocoding> optGeocoding = Optional.of(gson.fromJson(getJsonGeoapify(url).body(), Geocoding.class));
		if (optGeocoding.isPresent()) {
			return getResultadoConfiavel(optGeocoding.get().getResults());
		}
		return Optional.empty();
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
	
	public Optional<ResultGeocoding> getResultadoConfiavel(List<ResultGeocoding> resultados) {
		if (!resultados.isEmpty()) {
			var optResultado = Optional.of(resultados.get(0));
			BigDecimal nivelAceitavel = new BigDecimal("0.95");
			BigDecimal nivelDeclinavel = new BigDecimal("0.2");
			var rank = optResultado.get().getRank();
			if (rank.getConfidence() != null) {
				if (rank.getConfidence().compareTo(nivelAceitavel) >= 0) {
					return optResultado;
				} else if (rank.getConfidence().compareTo(nivelDeclinavel) >= 0) {
					if (rank.getConfidence_street_level() != null && rank.getConfidence_street_level().compareTo(nivelAceitavel) >= 0) {
						return optResultado;
					}
					if (rank.getConfidence_city_level() != null && rank.getConfidence_city_level().compareTo(nivelAceitavel) >= 0) {
						return optResultado;
					}
				}
			}
		}
		return Optional.empty();
	}
	
}
