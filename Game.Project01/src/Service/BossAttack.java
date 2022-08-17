package Service;

import java.awt.Image;

import javax.swing.ImageIcon;

public class BossAttack {

	Image image = new ImageIcon("src/images/deathStar Attack.png").getImage();
	int x, y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int attack = 15;
	
	public BossAttack(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	
	public void fire() {
		this.x = 11;
	}
	
}
