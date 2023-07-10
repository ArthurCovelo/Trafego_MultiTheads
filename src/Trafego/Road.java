package Trafego;

import java.awt.*;

class Road {
	private int x; // Posi��o X da estrada
	private int y; // Posi��o Y da estrada
	private int width; // Largura da estrada
	private int height; // Altura da estrada
	Direction direction; // Dire��o da estrada

	private Vector2 position; // Posi��o relativa da estrada

	private TrafficSemaphore trafficSemaphore; // Sem�foro de tr�fego associado � estrada

	public static final int INTERSECTION_SIZE = 5; // Tamanho da interse��o entre estradas

	public boolean reverse; // Indica se o ve�culo est� se movendo no sentido contr�rio

	private Vector2 intersectionPosition; // Posi��o da base do quadrado branco no cruzamento

	public Road(int x, int y, int width, int height, Direction direction, TrafficSimulator trafficSimulator,
			Vector2 position, boolean reverse, Vector2 intersectionPosition) {
		// Restante do c�digo...

		this.intersectionPosition = intersectionPosition;
		this.x = x; // Inicializa a posi��o X da estrada
		this.y = y; // Inicializa a posi��o Y da estrada
		this.reverse = reverse;
		this.width = width; // Inicializa a largura da estrada
		this.height = height; // Inicializa a altura da estrada
		this.position = position; // Inicializa a posi��o relativa da estrada
		this.direction = direction; // Inicializa a dire��o da estrada
		this.trafficSemaphore = new TrafficSemaphore(this.x + this.width / 2 + this.position.getX(),
				this.y + this.height / 2 + this.position.getY()); // Cria um sem�foro de tr�fego associado � estrada

	}

	public void draw(Graphics g) {
		g.setColor(Color.GRAY); // Define a cor das linhas da contra m�o
		if (direction == Direction.HORIZONTAL) {
			// Desenha as linhas da contra m�o
			for (int i = INTERSECTION_SIZE; i < width; i += INTERSECTION_SIZE * 2) {
				g.fillRect(x + i, y + INTERSECTION_SIZE / 2, INTERSECTION_SIZE, height - INTERSECTION_SIZE);
			}
		} else {
			// Desenha as linhas da contra m�o
			for (int i = INTERSECTION_SIZE; i < height; i += INTERSECTION_SIZE * 2) {
				g.fillRect(x + INTERSECTION_SIZE / 2, y + i, width - INTERSECTION_SIZE, INTERSECTION_SIZE);
			}
		}
		trafficSemaphore.Draw(g); // Desenha o sem�foro de tr�fego
	}

	public Vector2 getIntersectionPosition() {
		return intersectionPosition; // Retorna a posi��o do cruzamento
	}

	public int getX() {
		return x; // Retorna a posi��o X da estrada
	}

	public int getY() {
		return y; // Retorna a posi��o Y da estrada
	}

	public int getWidth() {
		return width; // Retorna a largura da estrada
	}

	public int getHeight() {
		return height; // Retorna a altura da estrada
	}

	public Direction getDirection() {
		return direction; // Retorna a dire��o da estrada
	}

	public TrafficSemaphore getTrafficSemaphore() {
		return trafficSemaphore; // Retorna o sem�foro de tr�fego associado � estrada
	}

	public void setTrafficSemaphore(TrafficSemaphore trafficSemaphore) {
		this.trafficSemaphore = trafficSemaphore; // Define o sem�foro de tr�fego associado � estrada
	}

}
