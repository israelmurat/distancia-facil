package br.com.muratsystems.distanciafacil.domain.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

import br.com.muratsystems.distanciafacil.domain.model.Endereco;

@Service
public class GeoLocalizacaoService {

	public void definirGeoLocalizacaoDoEndereco(Endereco endereco) {
		try {
			URL url = new URL("https://api.geoapify.com/v1/geocode/search?text=38%20Upper%20Montagu%20Street%2C%20Westminster%20W1H%201LJ%2C%20United%20Kingdom&apiKey=7f7d03b570294eb9ad3d6409058fdb6b");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestProperty("Accept", "application/json");

			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
			http.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
