package Trafego;

import java.awt.Color;
import java.awt.Graphics;

public class Vehicle extends Thread {
	private static final int SIZE = 20; // Tamanho do ve�culo
	private static final int MAX_SPEED = 4; // Velocidade m�xima do ve�culo

	private Road road; // Estrada em que o ve�culo est�
	private TrafficSimulator trafficSimulator; // Refer�ncia ao simulador de tr�fego
	private int x; // Posi��o X do ve�culo
	private int y; // Posi��o Y do ve�culo
	private int speed; // Velocidade do ve�culo

	public boolean car;

	public Vehicle(Road road, TrafficSimulator trafficSimulator, int id) {
		this.road = road; // Define a estrada em que o ve�culo est� this.reverse = reverse;
		this.car = false; // Inicialmente, o ve�culo n�o est� virado
		this.trafficSimulator = trafficSimulator; // Define a refer�ncia ao simulador de tr�fego
		speed = (int) (Math.random() * MAX_SPEED) + 1; // Define uma velocidade aleat�ria para o ve�culo

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
			// Ve�culo n�o est� virado, movimenta-se normalmente
			if (road.getDirection() == Direction.HORIZONTAL) {
				if (!road.reverse) {
					x += speed; // Move o ve�culo na dire��o positiva da estrada horizontal
				} else {
					x -= speed; // Move o ve�culo na dire��o negativa da estrada horizontal (sentido contr�rio)
				}
			} else {
				if (!road.reverse) {
					y += speed; // Move o ve�culo na dire��o positiva da estrada vertical
				} else {
					y -= speed; // Move o ve�culo na dire��o negativa da estrada vertical (sentido contr�rio)
				}
			}
		} else {
			// Ve�culo est� virado, movimenta-se de acordo com a dire��o da estrada
			if (road.getDirection() == Direction.HORIZONTAL) {
				if (!road.reverse) {
					y -= speed; // Move o ve�culo para baixo na estrada horizontal
				} else {
					y -= speed; // Move o ve�culo para cima na estrada horizontal
				}
			} else {
				if (!road.reverse) {
					x -= speed; // Move o ve�culo para a direita na estrada vertical
				} else {
					x -= speed; // Move o ve�culo para a esquerda na estrada vertical
				}
			}
		}

		// Verifica colis�o com o quadrado branco
		if (isCollidingWithWhiteSquare()) {
			car = true;
//			System.out.println("Ve�culo colidiu com o quadrado branco!");
		}
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, SIZE, SIZE); // Desenha o ve�culo

		// Desenhar sem�foro (faltando implementa��o)
		road.getTrafficSemaphore().Draw(g); // Desenha o sem�foro associado � estrada
	}

	@Override
	public void run() {
		while (true) {
			try {

				TrafficLightPhase phase = road.getTrafficSemaphore().getPhase();
				if (phase == TrafficLightPhase.GREEN) {
					move(); // Move o ve�culo apenas se o sem�foro estiver verde
				} else if (phase == TrafficLightPhase.YELLOW || phase == TrafficLightPhase.RED) {
					if (!isInFrontOfSemaphore()) {
						move(); // Move o ve�culo at� chegar em frente ao sem�foro

					}
				}

				trafficSimulator.repaint(); // Redesenha o simulador de tr�fego

				sleep(10); // Aguarda por um curto per�odo de tempo antes de realizar o pr�ximo movimento

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
			return true; // H� uma colis�o
		}
		return false; // N�o h� colis�o
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
