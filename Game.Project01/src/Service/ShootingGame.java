package Service;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import AppMain.AppMain;

public class ShootingGame extends JFrame {

	private Image bufferImage; // 플레이어 또는 적의 움직임을 그릴 때 깜빡임을 없애기 위해 더블 버퍼링 사용
	private Graphics screenGraphics; // 이를 대비해서 미리 변수 선언

	// 이미지 패키지에 있는 메인스크린 이미지 변수에 저장하기
	private Image mainScreen = new ImageIcon("src/images/main_screen.png").getImage();
	private Image loadingScreen = new ImageIcon("src/images/loading_screen.png").getImage();
	private Image gameScreen = new ImageIcon("src/images/game_screen.jpg").getImage();

	public boolean isMainScreen, isLoadingScreen, isGameScreen;

	public static Game game = new Game();
	
	private Audio backGroundMusic;

	public ShootingGame() {
		setTitle("Shooting Game"); // 제목
		setUndecorated(true); // 태두리가 없는 창
		setSize(AppMain.SCREEN_WIDTH, AppMain.SCREEN_HEIGHT); // 창 크기 설정
		setResizable(false); // 창크기 조절 여부 설정
		setLocationRelativeTo(null); // 창 위치 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼 설정
		setVisible(true);
		setLayout(null); // 레이아웃 없음

		init(); // 실행시 메인화면 메서드 불러오기
	}

	private void init() { // 메인화면 메서드
		isMainScreen = true;
		isLoadingScreen = false;
		isGameScreen = false;
		
		backGroundMusic = new Audio("src/audio/menuBGM.wav", true);
		backGroundMusic.start();

		addKeyListener(new KeyListener()); // 메인화면에서 사용될 키 메서드
	}

	private void gameStart() { // 게임 시작 메서드
		isMainScreen = false;
		isLoadingScreen = true;

		Timer loadingTimer = new Timer();
		TimerTask loadingTask = new TimerTask() {
	
			
			@Override
			public void run() {
				backGroundMusic.stop();
				isLoadingScreen = false;
				isGameScreen = true;
				game.start(); // 만들어진 Game 클래스의 스래드를 실행하기 위한 코드
			}
		};
		loadingTimer.schedule(loadingTask, 3000);
	}

	public void paint(Graphics g) { // 버퍼 이미지를 만들어서 뿌려줌 -> 화면 깜빡임 최소화
		bufferImage = createImage(AppMain.SCREEN_WIDTH, AppMain.SCREEN_HEIGHT);
		screenGraphics = bufferImage.getGraphics();
		screenDraw(screenGraphics);
		g.drawImage(bufferImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		if (isMainScreen) {
			g.drawImage(mainScreen, 0, 0, null);
		}
		if (isLoadingScreen) {
			g.drawImage(loadingScreen, 0, 0, null);
		}
		if (isGameScreen) {
			g.drawImage(gameScreen, 0, 0, null);
			game.gameDraw(g); // 게임화면 일때, gameDraw 메서드를 실행
		}
		this.repaint();
	}

	class KeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {// 버튼을 눌렀을때 행하는 행동 입력 메서드(여기서는 탈출)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.setUp(true);
				break;
			case KeyEvent.VK_DOWN:
				game.setDown(true);
				break;
			case KeyEvent.VK_LEFT:
				game.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
				game.setRight(true);
				break;
			case KeyEvent.VK_SPACE:
				game.setShooting(true);
				break;
				
			case KeyEvent.VK_ENTER:// 메인화면에서 엔터누르면 게임시작
				if (isMainScreen) {
					gameStart();
				}
				break;
			case KeyEvent.VK_ESCAPE: // esc 버튼을 누르면 시스템이 종료되는 메서드임!
				System.exit(0);
				break;
			case KeyEvent.VK_R: // R을 누르면 게임 리셋
				if(game.isOver()) game.reset(); // 만약 isOver가 트루일때만 리셋 메서드 실행하도록
				
				break;
				
				}
			}
		
		public void keyReleased(KeyEvent e) {// 버튼을 누르지 않았을때 메서드
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.setUp(false);
					break;
			case KeyEvent.VK_DOWN:
				game.setDown(false);
					break;
			case KeyEvent.VK_LEFT:
				game.setLeft(false);
					break;
			case KeyEvent.VK_RIGHT:
				game.setRight(false);
					break;
			case KeyEvent.VK_SPACE:
				game.setShooting(false);
				break;
	
			}
		
		
	}

}
}