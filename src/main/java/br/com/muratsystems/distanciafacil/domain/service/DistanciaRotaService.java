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
		for (var enderecoA : enderecos) {
			compararComEnderecoB(enderecos, enderecoA, elementosComparados, distancias);
		}
		return distancias;
	}

	private void compararComEnderecoB(List<Endereco> enderecos, Endereco enderecoA, List<int[]> elementosComparados,
			List<DistanciaRota> distancias) {
		for (var enderecoB : enderecos) {
			executarComparacao(enderecos, enderecoA, enderecoB, elementosComparados, distancias);
		}
	}

	private void executarComparacao(List<Endereco> enderecos, Endereco enderecoA, Endereco enderecoB,
			List<int[]> elementosComparados, List<DistanciaRota> distancias) {
		if (!enderecoA.equals(enderecoB)) {
			int numeroElemA = enderecos.indexOf(enderecoA) + 1;
			int numeroElemB = enderecos.indexOf(enderecoB) + 1;
			if (!isElementoComparado(numeroElemA, numeroElemB, elementosComparados)) {
				definirDistancias(enderecoA, enderecoB, distancias);
				int[] comparacao = { numeroElemA, numeroElemB };
				elementosComparados.add(comparacao);
			}
		}
	}

	private boolean isElementoComparado(int numeroElemA, int numeroElemB, List<int[]> elementosComparados) {
		for (int[] elementos : elementosComparados) {
			if ((elementos[0] == numeroElemA && elementos[1] == numeroElemB)
					|| (elementos[0] == numeroElemB && elementos[1] == numeroElemA)) {
				return true;
			}
		}
		return false;
	}
	
	private void definirDistancias(Endereco enderecoA, Endereco enderecoB, List<DistanciaRota> distancias) {
		var distanciaRota = new DistanciaRota();
		distanciaRota.setEnderecoA(enderecoA);
		distanciaRota.setEnderecoB(enderecoB);
		calcularDistanciaEmKmEntrePontos(distanciaRota);
		distancias.add(distanciaRota);
	}

	/**
	 * Solução adaptada.
	 * Fonte: https://thiagovespa.com.br/blog/2010/09/10/distancia-utilizando-coordenadas-geograficas-em-java/
	 */
	public void calcularDistanciaEmKmEntrePontos(DistanciaRota distanciaRota) {

		// Conversão de graus pra radianos das latitudes
		double latitudeARadiano = Math
				.toRadians(distanciaRota.getEnderecoA().getCoordenada().getLatitude().doubleValue());
		double latitudeBRadiano = Math
				.toRadians(distanciaRota.getEnderecoB().getCoordenada().getLatitude().doubleValue());

		// Diferença das longitudes
		double deltaLongitudeRadiano = Math
				.toRadians(distanciaRota.getEnderecoB().getCoordenada().getLongitude().doubleValue()
						- distanciaRota.getEnderecoA().getCoordenada().getLongitude().doubleValue());

		// Cálcula da distância entre os pontos
		double distancia = Math
				.acos(Math.cos(latitudeARadiano) * Math.cos(latitudeBRadiano) * Math.cos(deltaLongitudeRadiano)
						+ Math.sin(latitudeARadiano) * Math.sin(latitudeBRadiano))
				* RAIO_TERRA_KM;

		distanciaRota.setDistanciaEntreEnderecos(new BigDecimal(distancia).setScale(3, RoundingMode.HALF_EVEN));
		distanciaRota.setUnidadeDistancia(UnidadeDistancia.KM);

	}

}
