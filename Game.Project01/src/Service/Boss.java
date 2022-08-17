package Service;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Boss {

	Image image = new ImageIcon("src/images/deathStar.jpg") .getImage();
	int x , y;
	int wdith = image.getWidth(null);
	int height = image.getHeight(null);
	int boss_hp = 50;
	
	
	public Boss(int x, int y) {
		this.x = x;
		this.y = y;
	}

		public void move() {
			this.x -= 5;
		}
	
}
