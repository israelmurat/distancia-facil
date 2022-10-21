package br.com.muratsystems.distanciafacil.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.muratsystems.distanciafacil.domain.model.Endereco;
import br.com.muratsystems.distanciafacil.domain.model.GeoLocalizacao;

@Service
public class GeoLocalizacaoService {

//	@Autowired
	private RestTemplate restTemplate = new RestTemplate();
	
	public void definirGeoLocalizacaoPorEndereco(Endereco endereco) {
//		try {
			String urlString = "https://api.geoapify.com/v1/geocode/search?text=" 
					+ endereco.getTipoLogradouro() + "%20" 
					+ endereco.getLogradouro().replace(" ", "%20") +"%2C%20"
					+ endereco.getNumero() + "%20" 
					+ endereco.getBairro().replace(" ", "%20") + "%2C%20" 
					+ endereco.getCidade().replace(" ", "%20") + "%2C%20" 
					+ endereco.getUf() + "%2C%20" 
					+ endereco.getCep() + "&format=json&apiKey=7f7d03b570294eb9ad3d6409058fdb6b";
//			URL url = new URL("https://api.geoapify.com/v1/geocode/search?text=Av.%20Rio%20Branco%2C%201%20Centro%2C%20Rio%20de%20Janeiro%20RJ%2C%2020090003&format=json&apiKey=7f7d03b570294eb9ad3d6409058fdb6b");
//			URL url = new URL(urlString);
//			HttpURLConnection http = (HttpURLConnection)url.openConnection();
//			http.setRequestProperty("Accept", "application/json");
//
//			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
//			http.disconnect();
			GeoLocalizacao geoLocalizacao = restTemplate.getForObject(urlString, GeoLocalizacao.class);
			endereco.setGeoLocalizacao(geoLocalizacao);
			
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
}
