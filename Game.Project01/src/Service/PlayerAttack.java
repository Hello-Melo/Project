package Service;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PlayerAttack { // 플레이어 공격 클라스
	
	Image image = new ImageIcon("src/images/player_attack.png").getImage(); // 무기 이미지
	int x, y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 5; // 무기 공격력
	
	
	public PlayerAttack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
 public void fire() {//플레이어는 오른쪽으로 나가니까  x축을 증가시켜줌
	 this.x += 15;
 }

	
}
