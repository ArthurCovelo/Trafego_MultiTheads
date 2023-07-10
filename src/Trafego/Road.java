package Trafego;

import java.awt.*;

class Road {
	private int x; // Posição X da estrada
	private int y; // Posição Y da estrada
	private int width; // Largura da estrada
	private int height; // Altura da estrada
	Direction direction; // Direção da estrada

	private Vector2 position; // Posição relativa da estrada

	private TrafficSemaphore trafficSemaphore; // Semáforo de tráfego associado à estrada

	public static final int INTERSECTION_SIZE = 5; // Tamanho da interseção entre estradas

	public boolean reverse; // Indica se o veículo está se movendo no sentido contrário

	private Vector2 intersectionPosition; // Posição da base do quadrado branco no cruzamento

	public Road(int x, int y, int width, int height, Direction direction, TrafficSimulator trafficSimulator,
			Vector2 position, boolean reverse, Vector2 intersectionPosition) {
		// Restante do código...

		this.intersectionPosition = intersectionPosition;
		this.x = x; // Inicializa a posição X da estrada
		this.y = y; // Inicializa a posição Y da estrada
		this.reverse = reverse;
		this.width = width; // Inicializa a largura da estrada
		this.height = height; // Inicializa a altura da estrada
		this.position = position; // Inicializa a posição relativa da estrada
		this.direction = direction; // Inicializa a direção da estrada
		this.trafficSemaphore = new TrafficSemaphore(this.x + this.width / 2 + this.position.getX(),
				this.y + this.height / 2 + this.position.getY()); // Cria um semáforo de tráfego associado à estrada

	}

	public void draw(Graphics g) {
		g.setColor(Color.GRAY); // Define a cor das linhas da contra mão
		if (direction == Direction.HORIZONTAL) {
			// Desenha as linhas da contra mão
			for (int i = INTERSECTION_SIZE; i < width; i += INTERSECTION_SIZE * 2) {
				g.fillRect(x + i, y + INTERSECTION_SIZE / 2, INTERSECTION_SIZE, height - INTERSECTION_SIZE);
			}
		} else {
			// Desenha as linhas da contra mão
			for (int i = INTERSECTION_SIZE; i < height; i += INTERSECTION_SIZE * 2) {
				g.fillRect(x + INTERSECTION_SIZE / 2, y + i, width - INTERSECTION_SIZE, INTERSECTION_SIZE);
			}
		}
		trafficSemaphore.Draw(g); // Desenha o semáforo de tráfego
	}

	public Vector2 getIntersectionPosition() {
		return intersectionPosition; // Retorna a posição do cruzamento
	}

	public int getX() {
		return x; // Retorna a posição X da estrada
	}

	public int getY() {
		return y; // Retorna a posição Y da estrada
	}

	public int getWidth() {
		return width; // Retorna a largura da estrada
	}

	public int getHeight() {
		return height; // Retorna a altura da estrada
	}

	public Direction getDirection() {
		return direction; // Retorna a direção da estrada
	}

	public TrafficSemaphore getTrafficSemaphore() {
		return trafficSemaphore; // Retorna o semáforo de tráfego associado à estrada
	}

	public void setTrafficSemaphore(TrafficSemaphore trafficSemaphore) {
		this.trafficSemaphore = trafficSemaphore; // Define o semáforo de tráfego associado à estrada
	}

}
