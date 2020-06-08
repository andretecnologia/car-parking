//o que pe o estadia minusculo mesmo
package estacionamento.estadia;

import java.time.LocalTime;

import estacionamento.entidade.Carro;

public class Estadia {
	
	private Carro carro;
	private LocalTime entrada;
	private LocalTime saida;
	private boolean isRemovido;
	
	public LocalTime getEntrada() {
		return entrada;
	}
	public void setEntrada(LocalTime entrada) {
		this.entrada = entrada;
	}
	public LocalTime getSaida() {
		return saida;
	}
	public void setSaida(LocalTime saida) {
		this.saida = saida;
	}
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	public boolean isRemovido() {
		return isRemovido;
	}
	public void setRemovido(boolean isRemovido) {
		this.isRemovido = isRemovido;
	}
	@Override
	public String toString() {
		return "Estadia [carro=" + carro + ", entrada=" + entrada + ", saida=" + saida + ", removido=" + isRemovido + "]";
	}
}

