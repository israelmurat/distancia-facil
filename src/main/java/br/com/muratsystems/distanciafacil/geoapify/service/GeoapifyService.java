package br.com.muratsystems.distanciafacil.geoapify.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.muratsystems.distanciafacil.geoapify.model.Geocoding;

@Service
public class GeoapifyService {
	
	private static final String CHAVE_API = "7f7d03b570294eb9ad3d6409058fdb6b";
	
	@Autowired
	private Gson gson;

	public Optional<Geocoding> getGeocodingGeoapify(String endereco) {
		String url = "https://api.geoapify.com/v1/geocode/search?text=";
		url += endereco.replace(".", "%2E").replace(" ", "%20").replace(",", "%2C").replace("/", "%2F");
		url += "&format=json&apiKey=" + CHAVE_API;
		return Optional.of(gson.fromJson(getJsonGeoapify(url).body(), Geocoding.class));
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
	
}
