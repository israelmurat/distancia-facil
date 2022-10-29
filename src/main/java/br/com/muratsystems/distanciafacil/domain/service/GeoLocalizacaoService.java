package br.com.muratsystems.distanciafacil.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.muratsystems.distanciafacil.domain.exception.BusinessException;
import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.domain.model.GeoLocalizacao;
import br.com.muratsystems.distanciafacil.geoapify.service.GeoapifyService;

@Service
public class GeoLocalizacaoService {
	
//	private static final String CHAVE_API = "7f7d03b570294eb9ad3d6409058fdb6b";
//	
//	private Gson gson = new Gson();
	
	@Autowired
	private GeoapifyService geoapifyService;

	public List<Endereco> definirEnderecos(List<String> enderecosString) {
		if (enderecosString == null || enderecosString.size() < 3) {
			throw new BusinessException("Deve ser informado no mínimo 3 endereços!");
		}
		List<Endereco> enderecos = new ArrayList<>();
		for (String enderecoString : enderecosString) {
			definirEnderecoComGeoLocalizacao(enderecoString)
				.ifPresent((endereco) -> {
					enderecos.add(endereco);
				});
		}
		return enderecos;
	}
	
	public Optional<Endereco> definirEnderecoComGeoLocalizacao(String enderecoString) {
		var endereco = new Endereco();
//		var optGeocoding = getGeocodingGeoapify(enderecoString);
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
			var geoLocalizacao = new GeoLocalizacao();
			geoLocalizacao.setLatitude(optGeocoding.get().getResults().get(0).getLat());
			geoLocalizacao.setLongitude(optGeocoding.get().getResults().get(0).getLon());
			endereco.setGeoLocalizacao(geoLocalizacao);
		}
		return Optional.of(endereco);
	}
	
//	public Optional<Geocoding> getGeocodingGeoapify(String endereco) {
//		String url = "https://api.geoapify.com/v1/geocode/search?text=";
//		url += endereco.replace(".", "%2E").replace(" ", "%20").replace(",", "%2C").replace("/", "%2F");
//		url += "&format=json&apiKey=" + CHAVE_API;
//		return Optional.of(gson.fromJson(getJsonGeoapify(url).body(), Geocoding.class));
//	}
	
//	public DistanciaRota definirDistanciaEntreEnderecos(Endereco enderecoA, Endereco enderecoB) {
//		String url = "https://api.geoapify.com/v1/routing?waypoints="
//				+ enderecoA.getGeoLocalizacao().getLatitude() + "%2C" 
//				+ enderecoA.getGeoLocalizacao().getLongitude() + "%7C" 
//				+ enderecoB.getGeoLocalizacao().getLatitude() + "%2C" 
//				+ enderecoB.getGeoLocalizacao().getLongitude()
//				+ "&mode=drive&apiKey=" + CHAVE_API;	
////		Optional<Routing> optRouting = Optional.of(gson.fromJson(teste(url).body().toString(), Routing.class));
////		if (optRouting.isPresent()) {
////			var distanciaRota = new DistanciaRota();
////			var propertyFeature = optRouting.get().getFeatures().get(0).getProperties().get(0);
////			distanciaRota.setEnderecoA(enderecoA);
////			distanciaRota.setEnderecoB(enderecoB);
////			distanciaRota.setDistanciaEntreEnderecos(
////					new BigDecimal(propertyFeature.getDistance())
////					.divide(new BigDecimal("1000"), 3 , RoundingMode.HALF_EVEN));
////			distanciaRota.setUnidadeDistancia("KM");
////			// Ver se vai ter exception para o BigDecimal
////			return distanciaRota;
////		}
////		throw new BusinessException("Rota não encontrada!");
//		testeRestTemplate(enderecoA, enderecoB);
//		return null;
//	}

//	private HttpResponse<String> getJsonGeoapify(String url) {
//		try {
//			HttpClient client = HttpClient.newHttpClient();
//			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
//					.header("Content-Type", "application/json").build();
//
//			return client.send(request, BodyHandlers.ofString());
//		} catch (IOException | InterruptedException e) {
//			throw new RuntimeException(e);
//		}
//	}
	
//	private Response teste(String url) {
//		try {
//			OkHttpClient client = new OkHttpClient().newBuilder()
//					 .build();
//			Request request = new Request.Builder()
//			 .url(url)
//			 .method("GET", null)
//			 .build();
//			return client.newCall(request).execute();
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
//	private void testeRestTemplate(Endereco enderecoA, Endereco enderecoB) {
//		RestTemplate template = new RestTemplate();
//		List<String> waypoints = new ArrayList<>(); 
//		waypoints.add(enderecoA.getGeoLocalizacao().getLatitude().toString());
//		waypoints.add(enderecoA.getGeoLocalizacao().getLongitude().toString());
//		waypoints.add(enderecoB.getGeoLocalizacao().getLatitude().toString());
//		waypoints.add(enderecoB.getGeoLocalizacao().getLongitude().toString());
//		UriComponents uri = UriComponentsBuilder.newInstance()
//				.scheme("https")
//				.host("api.geoapify.com")
//				.path("v1/routing")
//				.queryParam("waypoints", enderecoA.getGeoLocalizacao().getLatitude(), 
//						enderecoA.getGeoLocalizacao().getLongitude(),
//						"%7C",
//						enderecoB.getGeoLocalizacao().getLatitude(),
//						enderecoB.getGeoLocalizacao().getLongitude())
//				.queryParam("mode", "drive")
//				.queryParam("apiKey", CHAVE_API)
//				.build();
//		
//		ResponseEntity<Routing> entity = template.getForEntity(uri.toUriString(), Routing.class);
//		var routing = entity.getBody();
//		System.out.println(routing);
//	}

}
