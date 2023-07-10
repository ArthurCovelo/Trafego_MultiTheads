package Trafego;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import java.util.concurrent.Semaphore;
import java.awt.image.BufferedImage;

public class TrafficSimulator extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int CITY_WIDTH = 800; // Largura da cidade
	public static final int CITY_HEIGHT = 600; // Altura da cidade
	private static final int ROAD_WIDTH = 65; // Largura da estrada
	private static final int NUM_ROADS = 4; // N�mero de estradas
	private static final int NUM_VEHICLES = 20; // N�mero de ve�culos

	private static final int SPAWN_INTERVAL = 1000; // Intervalo de tempo entre a gera��o de novos ve�culos 
	public static TrafficSimulator instance;

	int squareSize = 20; // Tamanho dos quadrados
	int centerX = CITY_WIDTH / 2 - squareSize / 2;
	int centerY = CITY_HEIGHT / 2 - squareSize / 2;

	private Road[] roads; // Array de estradas
	private Vehicle[] vehicles; // Array de ve�culos
	public static Semaphore crossing; // Sem�foro para controle de cruzamentos

	private BufferedImage buffer; // Buffer de imagem para desenhar o simulador

	public TrafficSimulator() {
		// Configura��es da janela do simulador de tr�fego
		setTitle("Traffic Simulator"); // T�tulo da janela
		setSize(CITY_WIDTH, CITY_HEIGHT); // Tamanho da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // A��o ao fechar a janela
		setResizable(false); // Impede o redimensionamento da janela
		crossing = new Semaphore(1); // Inicializa o sem�foro com uma permiss�o
		roads = new Road[NUM_ROADS]; // Inicializa o array de estradas
		vehicles = new Vehicle[NUM_VEHICLES]; // Inicializa o array de ve�culos
		TrafficSimulator.instance = this;

		roads[0] = new Road(0, CITY_HEIGHT / 2 - ROAD_WIDTH / 2, CITY_WIDTH, ROAD_WIDTH, Direction.HORIZONTAL, this,
				new Vector2(-60, -ROAD_WIDTH + 10), false, new Vector2(centerX + squareSize / 2, centerY)); // Estrada 0
		roads[1] = new Road(CITY_WIDTH / 2 - ROAD_WIDTH / 2, 0, ROAD_WIDTH, CITY_HEIGHT, Direction.VERTICAL, this,
				new Vector2(-ROAD_WIDTH + 10, ROAD_WIDTH + 50), true, new Vector2(centerX, centerY + squareSize / 2)); // Estrada
																														// 1
		roads[2] = new Road(0, CITY_HEIGHT / 2 + ROAD_WIDTH / 2, CITY_WIDTH, ROAD_WIDTH, Direction.HORIZONTAL, this,
				new Vector2(ROAD_WIDTH + 45, ROAD_WIDTH - 13), true,
				new Vector2(centerX + squareSize / 2, centerY + squareSize)); // Estrada 2
		roads[3] = new Road(CITY_WIDTH / 2 + ROAD_WIDTH / 2, 0, ROAD_WIDTH, CITY_HEIGHT, Direction.VERTICAL, this,
				new Vector2(ROAD_WIDTH - 17, ROAD_WIDTH - 120), false,
				new Vector2(centerX + squareSize, centerY + squareSize / 2)); // Estrada 3

		// Cria��o dos ve�culos e suas posi��es iniciais
		for (int i = 0; i < NUM_VEHICLES; i++) {
			Vehicle vehicle = new Vehicle(roads[i % NUM_ROADS], this, i);
			vehicles[i] = vehicle;
			vehicle.start(); // Inicia a thread do ve�culo
		}
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				spawnVehicle();
			}
		}, SPAWN_INTERVAL, SPAWN_INTERVAL);

		buffer = new BufferedImage(CITY_WIDTH, CITY_HEIGHT, BufferedImage.TYPE_INT_ARGB); 
																						

		setVisible(true); // Torna a janela vis�vel
	}

	private void spawnVehicle() {
		int roadIndex = (int) (Math.random() * NUM_ROADS); // Seleciona uma estrada aleat�ria
		Vehicle vehicle = new Vehicle(roads[roadIndex], this, vehicles.length);
		vehicles = Arrays.copyOf(vehicles, vehicles.length + 1); // Aumenta o tamanho do array de ve�culos
		vehicles[vehicles.length - 1] = vehicle; // Adiciona o novo ve�culo ao array
		vehicle.start(); // Inicia a thread do ve�culo
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) buffer.getGraphics(); // Obt�m o contexto gr�fico do buffer
		g2d.setColor(Color.BLACK); // Define a cor de fundo como preto
		g2d.fillRect(0, 0, CITY_WIDTH, CITY_HEIGHT); // Pinta o fundo de preto

		for (Road road : roads) {
			road.draw(g2d); // Desenha as estradas no buffer
		}

		for (Vehicle vehicle : vehicles) {
			vehicle.draw(g2d); // Desenha os ve�culos no buffer
		}
		g.drawImage(buffer, 0, 0, this); // Desenha o buffer na tela
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new TrafficSimulator(); // Cria uma inst�ncia do simulador de tr�fego
		});
	}
}
