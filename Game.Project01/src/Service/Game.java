package Service;

import java.awt.Color;
import java.awt.Font;
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
	private int score; // 점수를 체크할 변수
	
	
	

	private Image player = new ImageIcon("src/images/player.png").getImage();

	private int playerX, playerY;// 플레이어 좌표
	private int playerWidth = player.getWidth(null);// 플레이어 크기
	private int playerHeight = player.getHeight(null);
	private int playerSpeed = 10; // 키입력이 한번 됬을 때 이동하는 거리
	private int playerHp = 30;

	private boolean up, down, left, right, shooting; // 여기서 shooting 변수가 true이면 발사하는 것!
	private boolean isOver; //게임오버를 나타낼 번수
	
	
	// 플레이어의 공격을 담을 arraylist
	ArrayList<PlayerAttack> playerAttackList = new ArrayList<PlayerAttack>();
	ArrayList<Enermy> enermyList = new ArrayList<Enermy>();
	ArrayList<Boss> bossList = new ArrayList<Boss>();
	
	ArrayList<EnermyAttack> enermyAttackList = new ArrayList<EnermyAttack>();
	ArrayList<BossAttack> bossAttackList = new ArrayList<BossAttack>();
	// 변수선언
	private PlayerAttack playerAttack;
	private Enermy enermy;
	private EnermyAttack enermyAttack;

	
	private Audio backGroundMusic;
	private Audio hitSound;
	
	private Boss boss;
	
	@Override
	public void run() {
		cnt = 0;
		playerX = 10;
		playerY = (AppMain.SCREEN_HEIGHT - playerHeight) / 2;
		
		backGroundMusic = new Audio("src/audio/gameBGM.wav", true);
		hitSound = new Audio("src/audio/hitSound.wav", true);
	
		reset();

		while (true) { // cnt가 앞에서 설정한 delay 밀리초가 지날때마다 증가
			while(!isOver) {
			
			pretime = System.currentTimeMillis();
			// 정확한 주기를 위해 현재 시간 - (cnt가 증가하기 전 시간) < delay 일 경우
			// 그 차이만큼 스레드에 sleep을 주는 코드
			if (System.currentTimeMillis() - pretime < delay) {
				try {
					Thread.sleep(delay - System.currentTimeMillis() + pretime);
					keyProcess();
					playerAttackProcess();
					enermyAppearProcess();
					enermyMoveProcess();
					enermyAttackProcess();
					cnt++;
				} catch (InterruptedException e) { // try catch 문으로 sleep을 주고 에러 뜰시 예외!
					e.printStackTrace();
				}
				}
				}
			try {
				Thread.sleep(100); // 만약 isOver = true일 경우 쓰레드가 계속 쉬도록 해줌
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
	}

	
	public void reset() {
		isOver = false;
		cnt = 0;
		score = 0;
		playerX = 10;
		playerY = (AppMain.SCREEN_HEIGHT - playerHeight) / 2;
		
		backGroundMusic.start();
		playerAttackList.clear();
		
		enermyList.clear();
		enermyAttackList.clear();
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
			
			for( int j = 0; j < enermyList.size(); j++) {
				enermy = enermyList.get(j);
				if(playerAttack.x > enermy.x && playerAttack.x < enermy.x + enermy.width &&
						playerAttack.y > enermy.y && playerAttack.y < enermy.y+enermy.height) {
					enermy.enemy_hp -= playerAttack.attack;
					playerAttackList.remove(playerAttack);
				} 
				if(enermy.enemy_hp <= 0) {
					hitSound.start();
					enermyList.remove(enermy);
					score += 1000;
				}
			}
			}
		}

	private void enermyAppearProcess() { // 적 출현빈도 메서드
		if(cnt % 80 == 0) {
			enermy = new Enermy(1120, (int) (Math.random()*621));
			enermyList.add(enermy);
			}
		}
	
	//보스 출현 조건 및 적들 제거
	private void bossAppearProcess() {
		if(score >= 10000) {
			enermyList.clear();
			boss = new Boss(1120, 1120);
			bossList.add(boss);
		}
	}
	
	
	private void bossMoveProcess() {
		for(int i = 0; i < bossList.size() ; i++) {
			boss = bossList.get(i);
			boss.move();
		}
		
	}
	
	private void enermyMoveProcess() { //적의 움직임을 관장하는 메서드
		for(int i = 0; i <enermyList.size() ; i++) {
			enermy = enermyList.get(i);
			enermy.move();
		}
		}
	
	private void enermyAttackProcess() {
		if(cnt % 50 == 0) {
			enermyAttack = new EnermyAttack(enermy.x - 80, enermy.y + 35);
			enermyAttackList.add(enermyAttack);
		}
		for(int i = 0; i < enermyAttackList.size(); i++) {
			enermyAttack = enermyAttackList.get(i);
			enermyAttack.fire();
			
			if(enermyAttack.x > playerX  && enermyAttack.x < playerX + playerWidth &&
					enermyAttack.y > playerY && enermyAttack.y < playerY + playerHeight) {
				playerHp -= enermyAttack.attack;
				hitSound.start();
				enermyAttackList.remove(enermyAttack);
				if(playerHp <= 0) isOver = true;
			}
			
			}
		}
	
	public void gameDraw(Graphics g) { // 앞으로 만들 게임안의 모든 요소들을 그려주는 메서드는
										// 전부 이 메서드에 저장될것!
		playerDraw(g);
		enermyDraw(g);
		infoDraw(g);
	}

	public void infoDraw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Cookierun", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 40, 80);
		
		if(isOver) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Cookierun", Font.BOLD, 80));
			g.drawString("Press R to restart", 295, 380);
		}
		
	}
	
	
	
	public void playerDraw(Graphics g) { // 플레이어 이미지를 x,y에 대입
		g.drawImage(player, playerX, playerY, null);
		g.setColor(Color.GREEN);
		g.fillRect(playerX-1, playerY - 40 , playerHp*6, 20);
		for(int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);// get메서드를 통해 객체하나하나에 접근해 fire 메서드 실행
			g.drawImage(playerAttack.image, playerAttack.x, playerAttack.y, null);
			}
	}
	
	public void enermyDraw(Graphics g) { // 적의 공격과 움직임을 그려주는 메서드
		for (int i = 0; i < enermyList.size(); i++) {
			enermy = enermyList.get(i);
			g.drawImage(enermy.image, enermy.x, enermy.y, null);
			g.setColor(Color.RED);
			g.fillRect(enermy.x+1, enermy.y-40, enermy.enemy_hp*15, 20);
		}
		for (int i = 0; i < enermyAttackList.size(); i++) {
			enermyAttack = enermyAttackList.get(i);
			g.drawImage(enermyAttack.image, enermyAttack.x, enermyAttack.y, null);
			
		}
	}

	
	
	

	
	
	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
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
