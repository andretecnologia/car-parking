package estacionamento.entidade;

public class Carro {
	private String placa;
	private String modelo;
	
	public String getPlaca() {
		return placa;
	}
	public String getModelo() {
		return modelo;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	@Override
	public String toString() {
		return "Carro [placa=" + placa + ", modelo=" + modelo + "]";
	}
}
