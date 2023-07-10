package Trafego;

public class Vector2 {
	private int x; // Componente X do vetor
	private int y; // Componente Y do vetor

	public Vector2(int x, int y) {
		this.x = x; // Inicializa o componente X com o valor fornecido
		this.y = y; // Inicializa o componente Y com o valor fornecido
	}

	public int getX() {
		return x; // Retorna o componente X do vetor
	}

	public void setX(int x) {
		this.x = x; // Define o valor do componente X do vetor
	}

	public int getY() {
		return y; // Retorna o componente Y do vetor
	}

	public void setY(int y) {
		this.y = y; // Define o valor do componente Y do vetor
	}
}
