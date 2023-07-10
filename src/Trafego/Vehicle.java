package Trafego;

import java.awt.Color;
import java.awt.Graphics;

public class Vehicle extends Thread {
	private static final int SIZE = 20; // Tamanho do veículo
	private static final int MAX_SPEED = 4; // Velocidade máxima do veículo

	private Road road; // Estrada em que o veículo está
	private TrafficSimulator trafficSimulator; // Referência ao simulador de tráfego
	private int x; // Posição X do veículo
	private int y; // Posição Y do veículo
	private int speed; // Velocidade do veículo

	public boolean car;

	public Vehicle(Road road, TrafficSimulator trafficSimulator, int id) {
		this.road = road; // Define a estrada em que o veículo está this.reverse = reverse;
		this.car = false; // Inicialmente, o veículo não está virado
		this.trafficSimulator = trafficSimulator; // Define a referência ao simulador de tráfego
		speed = (int) (Math.random() * MAX_SPEED) + 1; // Define uma velocidade aleatória para o veículo

		if (road.getDirection() == Direction.HORIZONTAL) {
			if (!road.reverse) {
				x = road.getX() - SIZE - (SIZE + 5) * id;
				y = road.getY() + road.getHeight() / 2 - SIZE / 2;
			} else {
				x = road.getX() + road.getWidth() + SIZE + (SIZE + 5) * id;
				y = road.getY() + road.getHeight() / 2 - SIZE / 2;
			}
		} else {
			if (!road.reverse) {
				x = road.getX() + road.getWidth() / 2 - SIZE / 2;
				y = road.getY() - SIZE - (SIZE + 5) * id;
			} else {
				x = road.getX() + road.getWidth() / 2 - SIZE / 2;
				y = road.getY() + road.getHeight() + SIZE + (SIZE + 5) * id;
			}
		}
	}

	public void move() {
		if (!car) {
			// Veículo não está virado, movimenta-se normalmente
			if (road.getDirection() == Direction.HORIZONTAL) {
				if (!road.reverse) {
					x += speed; // Move o veículo na direção positiva da estrada horizontal
				} else {
					x -= speed; // Move o veículo na direção negativa da estrada horizontal (sentido contrário)
				}
			} else {
				if (!road.reverse) {
					y += speed; // Move o veículo na direção positiva da estrada vertical
				} else {
					y -= speed; // Move o veículo na direção negativa da estrada vertical (sentido contrário)
				}
			}
		} else {
			// Veículo está virado, movimenta-se de acordo com a direção da estrada
			if (road.getDirection() == Direction.HORIZONTAL) {
				if (!road.reverse) {
					y -= speed; // Move o veículo para baixo na estrada horizontal
				} else {
					y -= speed; // Move o veículo para cima na estrada horizontal
				}
			} else {
				if (!road.reverse) {
					x -= speed; // Move o veículo para a direita na estrada vertical
				} else {
					x -= speed; // Move o veículo para a esquerda na estrada vertical
				}
			}
		}

		// Verifica colisão com o quadrado branco
		if (isCollidingWithWhiteSquare()) {
			car = true;
//			System.out.println("Veículo colidiu com o quadrado branco!");
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, SIZE, SIZE); // Desenha o veículo

		// Desenhar semáforo (faltando implementação)
		road.getTrafficSemaphore().Draw(g); // Desenha o semáforo associado à estrada
	}

	@Override
	public void run() {
		while (true) {
			try {

				TrafficLightPhase phase = road.getTrafficSemaphore().getPhase();
				if (phase == TrafficLightPhase.GREEN) {
					move(); // Move o veículo apenas se o semáforo estiver verde
				} else if (phase == TrafficLightPhase.YELLOW || phase == TrafficLightPhase.RED) {
					if (!isInFrontOfSemaphore()) {
						move(); // Move o veículo até chegar em frente ao semáforo

					}
				}

				trafficSimulator.repaint(); // Redesenha o simulador de tráfego

				sleep(10); // Aguarda por um curto período de tempo antes de realizar o próximo movimento

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isCollidingWithWhiteSquare() {
		int squareSize = 37; // Tamanho dos quadrados brancos
		int centerX = 20 + trafficSimulator.getWidth() / 2 - squareSize / 2;
		int centerY = 25 + trafficSimulator.getHeight() / 2 - squareSize / 2;

		if (x + SIZE >= centerX && x <= centerX + squareSize && y + SIZE >= centerY && y <= centerY + squareSize) {
			return true; // Há uma colisão
		}
		return false; // Não há colisão
	}

	private boolean isInFrontOfSemaphore() {
		TrafficSemaphore semaphore = road.getTrafficSemaphore();

		if (road.getDirection() == Direction.HORIZONTAL) {
			return x + SIZE >= semaphore.position.getX() - SIZE && x <= semaphore.position.getX();
		} else {
			return y + SIZE >= semaphore.position.getY() - SIZE && y <= semaphore.position.getY();
		}
	}

}
