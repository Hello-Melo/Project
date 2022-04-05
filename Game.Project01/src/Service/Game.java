package Service;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import AppMain.AppMain;

public class Game extends Thread {

	public Game(AppMain main) {
	}

	public Game() {
		// TODO Auto-generated constructor stub
	}

	private int delay = 20; // 게임의 딜레이
	private int cnt; // 딜레이 마다 증가하는 cnt 선언, 이게 이벤트 발생 주기 컨트롤하는 변수
	private long pretime;

	private Image player = new ImageIcon("src/images/player.png").getImage();

	private int playerX, playerY;// 플레이어 좌표
	private int playerWidth = player.getWidth(null);// 플레이어 크기
	private int playerHeight = player.getHeight(null);
	private int playerSpeed = 10; // 키입력이 한번 됬을 때 이동하는 거리
	private int playerHp = 30;

	private boolean up, down, left, right, shooting; // 여기서 shooting 변수가 true이면 발사하는 것!

	// 플레이어의 공격을 담을 arraylist
	ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	// 변수선언
	private PlayerAttack playerAttack;

	@Override
	public void run() {
		cnt = 0;
		playerX = 10;
		playerY = (AppMain.SCREEN_HEIGHT - playerHeight) / 2;

		while (true) { // cnt가 앞에서 설정한 delay 밀리초가 지날때마다 증가
			pretime = System.currentTimeMillis();
			// 정확한 주기를 위해 현재 시간 - (cnt가 증가하기 전 시간) < delay 일 경우
			// 그 차이만큼 스레드에 sleep을 주는 코드
			if (System.currentTimeMillis() - pretime < delay) {
				try {
					Thread.sleep(delay - System.currentTimeMillis() + pretime);
					keyProcess();
					playerAttackProcess();
					cnt++;
				} catch (InterruptedException e) { // try catch 문으로 sleep을 주고 에러 뜰시 예외!
					e.printStackTrace();
				}
			}
		}
	}

	private void keyProcess() { // 키입력 처리 메서드(움직일 때 화면을 벗어나지 않게)
		if (up && playerY - playerSpeed > 0)
			playerY -= playerSpeed;
		if (down && playerY + playerHeight + playerSpeed < AppMain.SCREEN_HEIGHT)
			playerY += playerSpeed;
		if (left && playerX - playerSpeed > 0)
			playerX -= playerSpeed;
		if (right && playerX + playerWidth + playerSpeed < AppMain.SCREEN_WIDTH)
			playerX += playerSpeed;
		if (shooting && cnt % 14 == 0) {// cnt가 0.02초마다 증가하니 0.3초마다 미사일 발사
			playerAttack = new PlayerAttack(playerX + 100, playerY + 25);
			playerAttackList.add(playerAttack);
		}
	}

	private void playerAttackProcess() {// 플레이어 공격처리 메서드
		for (int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);// get메서드를 통해 객체하나하나에 접근해 fire 메서드 실행
			playerAttack.fire();
			}
		}

	public void gameDraw(Graphics g) { // 앞으로 만들 게임안의 모든 요소들을 그려주는 메서드는
										// 전부 이 메서드에 저장될것!
		playerDraw(g);
	}

	public void playerDraw(Graphics g) { // 플레이어 이미지를 x,y에 대입
		g.drawImage(player, playerX, playerY, null);
		for(int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);// get메서드를 통해 객체하나하나에 접근해 fire 메서드 실행
			g.drawImage(playerAttack.image, playerAttack.x, playerAttack.y, null);
			}
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	
	
}
