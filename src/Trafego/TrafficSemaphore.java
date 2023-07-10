package Trafego;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.Semaphore;

public class TrafficSemaphore extends Thread {
	public static int SIZE = 10;
	private TrafficLightPhase phase;
	public Vector2 position;
	private Color color;
	public Semaphore semaphore;

	public TrafficSemaphore(int x, int y) {
		setPhase(TrafficLightPhase.RED);
		position = new Vector2(x, y);
		color = Color.RED;
		semaphore = new Semaphore(1);

		start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				TrafficSimulator.crossing.acquire();
				semaphore.acquire(); // Adquire o semáforo para evitar concorrência
				for (int count = 3; count > 0; count--) {
					Change();
					sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public TrafficLightPhase getPhase() {
		return phase;
	}

	public void setPhase(TrafficLightPhase phase) {
		this.phase = phase;
	}

	public void Draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(position.getX(), position.getY(), 15, 15); // Desenha o círculo externo do semáforo
		g.setColor(color);
		g.fillOval(position.getX(), position.getY(), 15, 15); 

	}

	public void Change() {
		switch (getPhase()) {
		case GREEN:
			try {
				sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			color = Color.YELLOW; // Altera a cor para amarelo
			setPhase(TrafficLightPhase.YELLOW); // Atualiza a fase para amarelo

			break;
		case YELLOW:
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			color = Color.RED; // Altera a cor para vermelho
			setPhase(TrafficLightPhase.RED); // Atualiza a fase para vermelho
			semaphore.release(); // Libera o semáforo
			TrafficSimulator.crossing.release(); // Libera a permissão para atravessar a interseção
			break;
		case RED:
			color = Color.GREEN; // Altera a cor para verde
			setPhase(TrafficLightPhase.GREEN); // Atualiza a fase para verde

			break;
		}

	}

}
