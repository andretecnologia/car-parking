package estacionamento.gerenciamento;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import estacionamento.entidade.Carro;
import estacionamento.estadia.Estadia;

public class Gerenciamento {

	private static final String FILENAME = "bd.json";
	public static Scanner scanner = new Scanner(System.in);
	public static Map<String, Estadia> estadias = new HashMap<String, Estadia>();
	
	public Gerenciamento() throws IOException {
		
		
	}

	public static void setEntrada()  throws IOException {
		le();
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
		grava();
	}

	public static void setSaida()  throws IOException {
		le();
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
		grava();
	}

	public static void getPermanencia()  throws IOException {
		le();
		System.out.println("Digite uma Placa");
		String placaParaConsulta = scanner.next();
		Estadia estadiaDeConsulta = estadias.get(placaParaConsulta);
		try {
			System.out.println("Permanencia " + calculaPermanencia(estadiaDeConsulta));
		} catch (Exception e) {
			System.out.println("Não existe registro de entrada/saída para a placa informada");

		}
		grava();
	}

	public static void getQuantidade()  throws IOException {
		le();
		int quantidade = 0;
		for (Map.Entry<String, Estadia> entry : estadias.entrySet()) {
			if (entry.getValue().getSaida() == null && !entry.getValue().isRemovido()) {
				quantidade++;
			}
		}
		System.out.println("Você tem " + quantidade + " carro(s) estacionados");
		grava();
	}

	public static void removeEstadia()  throws IOException {
		le();
		System.out.println("Digite uma Placa");
		String placa = scanner.next();
		if (estadias.get(placa) != null) {
			Estadia estadia = estadias.get(placa);
			estadia.setRemovido(true);
			estadias.put(placa, estadia);
		} else {
			System.out.println("Placa não encontrada");
		}
		grava();
	}

	public static void listEstadia()  throws IOException {
		le();
		for (Map.Entry<String, Estadia> entry : estadias.entrySet()) {
			le();
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
		grava();
	}

	public static void debug()  throws IOException {
		le();
		for (Map.Entry<String, Estadia> entry : estadias.entrySet()) {
			System.out.println("Placa: [ " + entry.getKey() + " ] - "+ entry.getValue().toString());
		}
		grava();
	}

	private static long calculaPermanencia(Estadia estadiaDeConsulta) throws IOException {
		return estadiaDeConsulta.getEntrada().until(estadiaDeConsulta.getSaida(), SECONDS);
	}
	
	public static void le() throws IOException {
		BufferedReader fr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(FILENAME))));
	    String stringLida = fr.readLine();
	    Gson gson = new Gson();
	    Map<String, Estadia> estadiasBD = gson.fromJson(stringLida , new TypeToken<Map<String, Estadia>>(){}.getType());
	    if (estadiasBD != null)
	    	estadias = estadiasBD;
	    fr.close();
	}
	
	public static void grava() throws FileNotFoundException {
    	PrintWriter pw = new PrintWriter(FILENAME);
        Gson gson = new Gson();
    	String jsonString = gson.toJson(estadias);
    	pw.append(jsonString);
    	pw.close();
	}

}
