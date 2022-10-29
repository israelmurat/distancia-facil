package br.com.muratsystems.distanciafacil.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.muratsystems.distanciafacil.domain.enuns.UnidadeDistancia;
import br.com.muratsystems.distanciafacil.domain.model.DistanciaRota;
import br.com.muratsystems.distanciafacil.domain.model.Endereco;

@Service
public class DistanciaRotaService {

	private static final int RAIO_TERRA_KM = 6371;

	public List<DistanciaRota> calcularDistanciaEnderecos(List<Endereco> enderecos) {

		List<int[]> elementosComparados = new ArrayList<>();
		List<DistanciaRota> distancias = new ArrayList<>();

		for (Endereco enderecoA : enderecos) {
			int numeroElemA = enderecos.indexOf(enderecoA) + 1;
			for (Endereco enderecoB : enderecos) {
				if (!enderecoA.equals(enderecoB)) {
					int numeroElemB = enderecos.indexOf(enderecoB) + 1;

					boolean jaComparado = false;
					for (int[] elementos : elementosComparados) {
						if ((elementos[0] == numeroElemA && elementos[1] == numeroElemB)
								|| (elementos[0] == numeroElemB && elementos[1] == numeroElemA)) {
							jaComparado = true;
							break;
						}
					}
					if (!jaComparado) {
						var distanciaRota = new DistanciaRota();
						distanciaRota.setEnderecoA(enderecoA);
						distanciaRota.setEnderecoB(enderecoB);
						calcularDistanciaEmKmEntrePontos(distanciaRota);
						distancias.add(distanciaRota);
						int[] comparacao = { numeroElemA, numeroElemB };
						elementosComparados.add(comparacao);
					}
				}
			}
		}
		return distancias;
	}

	public void calcularDistanciaEmKmEntrePontos(DistanciaRota distanciaRota) {

		// Conversão de graus pra radianos das latitudes
		double latitudeARadiano = Math
				.toRadians(distanciaRota.getEnderecoA().getGeoLocalizacao().getLatitude().doubleValue());
		double latitudeBRadiano = Math
				.toRadians(distanciaRota.getEnderecoB().getGeoLocalizacao().getLatitude().doubleValue());

		// Diferença das longitudes
		double deltaLongitudeRadiano = Math
				.toRadians(distanciaRota.getEnderecoB().getGeoLocalizacao().getLongitude().doubleValue()
						- distanciaRota.getEnderecoA().getGeoLocalizacao().getLongitude().doubleValue());

		// Cálcula da distância entre os pontos
		double distancia = Math
				.acos(Math.cos(latitudeARadiano) * Math.cos(latitudeBRadiano) * Math.cos(deltaLongitudeRadiano)
						+ Math.sin(latitudeARadiano) * Math.sin(latitudeBRadiano))
				* RAIO_TERRA_KM;

		distanciaRota.setDistanciaEntreEnderecos(new BigDecimal(distancia).setScale(3, RoundingMode.HALF_EVEN));
		distanciaRota.setUnidadeDistancia(UnidadeDistancia.KM);

	}

}
