package Service;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enermy {
	
	Image image = new ImageIcon("src/images/enermy.png").getImage();
	int x, y;
	int width = image.getWidth(null);
	int height = image.getHeight(null);
	int enemy_hp = 10;

	
	public Enermy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void move() { // 적 기체는 왼쪽으로 움직이니까 -로 잡아줌
		this.x -= 7;
	}

	

}
