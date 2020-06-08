package estacionamento.gerenciamento;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import estacionamento.entidade.Carro;
import estacionamento.estadia.Estadia;

public class Gerenciamento {

	public static Scanner scanner = new Scanner(System.in);
	public static Map<String, Estadia> estadias = new HashMap<String, Estadia>();

	public static void setEntrada() {
		Estadia estadia = new Estadia();
		Carro carro = new Carro();

		estadia.setEntrada(LocalTime.now());
		System.out.println("Digite uma Placa");
		carro.setPlaca(scanner.next());
		System.out.println("Digite um Modelo");
		carro.setModelo(scanner.next());
		System.out.println(estadia.getEntrada());
		estadia.setCarro(carro);
		estadias.put(carro.getPlaca(), estadia);
	}

	public static void setSaida() {
		System.out.println("Digite uma Placa");
		String placaDaSaida = scanner.next();
		if (estadias.get(placaDaSaida) != null) {
			Estadia estadiaDaSaida = estadias.get(placaDaSaida);
			estadiaDaSaida.setSaida(LocalTime.now());
			estadias.put(placaDaSaida, estadiaDaSaida);
			System.out.println(estadiaDaSaida.getSaida());
		} else {
			System.out.println("Placa não encontrada");
		}
	}

	public static void getPermanencia() {
		System.out.println("Digite uma Placa");
		String placaParaConsulta = scanner.next();
		Estadia estadiaDeConsulta = estadias.get(placaParaConsulta);
		try {
			System.out.println("Permanencia " + calculaPermanencia(estadiaDeConsulta));
		} catch (Exception e) {
			System.out.println("Não existe registro de entrada/saída para a placa informada");

		}
	}

	public static void getQuantidade() {
		int quantidade = 0;
		for (Map.Entry<String, Estadia> entry : estadias.entrySet()) {
			if (entry.getValue().getSaida() == null && !entry.getValue().isRemovido()) {
				quantidade++;
			}
		}
		System.out.println("Você tem " + quantidade + " carro(s) estacionados");
	}

	public static void removeEstadia() {
		System.out.println("Digite uma Placa");
		String placa = scanner.next();
		if (estadias.get(placa) != null) {
			Estadia estadia = estadias.get(placa);
			estadia.setRemovido(true);
			estadias.put(placa, estadia);
		} else {
			System.out.println("Placa não encontrada");
		}
	}

	public static void listEstadia() {
		for (Map.Entry<String, Estadia> entry : estadias.entrySet()) {
			if (!entry.getValue().isRemovido()) {
				StringBuilder mensagem = new StringBuilder();
				mensagem.append("O Carro Placa: [ " + entry.getKey());
				mensagem.append(" ] Modelo: [ " + entry.getValue().getCarro().getModelo());
				mensagem.append(" ] Estacionou: [ " + entry.getValue().getEntrada() + "] ");
	
				if (entry.getValue().getSaida() != null) {
					mensagem.append("Até: [ " + entry.getValue().getSaida() + " ] ");
					mensagem.append(" A Permanência Foi De: [ " + calculaPermanencia(entry.getValue()) + " ] ");
				}
				System.out.println(mensagem);
			}
		}
	}

	public static void debug() {
		for (Map.Entry<String, Estadia> entry : estadias.entrySet()) {
			System.out.println("Placa: [ " + entry.getKey() + " ] - "+ entry.getValue().toString());
		}
	}

	private static long calculaPermanencia(Estadia estadiaDeConsulta) {
		return estadiaDeConsulta.getEntrada().until(estadiaDeConsulta.getSaida(), SECONDS);
	}

}
