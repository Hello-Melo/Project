package Service;

import java.awt.Image;

import javax.swing.ImageIcon;

public class EnermyAttack {
	
	Image image = new ImageIcon("src/images/enermy_attack.png").getImage();
	int x, y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack =5;
	
	public EnermyAttack(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void fire() { //왼쪽으로 공격하기 때문에 -로 잡아줌
		this.x -= 12;
	}
	

}
