package nicellipse.v1;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Exercice1V1 {
	NiSpace space = new NiSpace("Exercice 1", new Dimension(600, 450));
	NiRectangle robi = new NiRectangle();
	NiRectangle robi2 = new NiRectangle();
	NiRectangle robi3 = new NiRectangle();

	void moveRobi(int x, int y, NiRectangle r) {
		final Runnable doit = new Runnable() {
			public void run() {
				r.setLocation(new Point(x, y));
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		try {
			SwingUtilities.invokeAndWait(doit);
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public Exercice1V1() {
		this.init();

		space.openInWindow();

		this.movement();

	}

	private void init(){
		space.setBackground(Color.lightGray);
		robi.setBackground(Color.red);
		robi2.setBackground(Color.blue);
		robi3.setBackground(Color.green);
		robi.setSize(20, 20);
		robi2.setSize(20, 20);
		robi3.setSize(20, 20);
		space.add(robi);
		space.add(robi2);
		space.add(robi3);
	}

	private void movement(){
		Random random = new Random();
		int x = 0;
		int x2 = 0;
		int x3 = 0;
		int y = random.nextInt(56);
		int y2 = random.nextInt(76) + 65;
		int y3 = random.nextInt(76) + 150;

		int vitesse = random.nextInt(2) + 1;
		int vitesse2 = random.nextInt(2) + 1;
		int vitesse3 = random.nextInt(2) + 1;

		while (true) {
			while (x < space.getWidth() - robi.getWidth()) {
				x = x + vitesse;
				x2 = x2 + vitesse2;
				x3 = x3 + vitesse3;
				this.moveRobi(x, y, robi);
				this.moveRobi(x2, y2, robi2);
				this.moveRobi(x3, y3, robi3);
			}
			x = 0;
			x2 = 0;
			x3 = 0;
			y = random.nextInt(66);
			y2 = random.nextInt(76) + 65;
			y3 = random.nextInt(76) + 150;
			this.moveRobi(x, y, robi);
			this.moveRobi(x2, y2, robi2);
			this.moveRobi(x3, y3, robi3);
			vitesse = random.nextInt(2) + 1;
			vitesse2 = random.nextInt(2) + 1;
			vitesse3 = random.nextInt(2) + 1;
		}
	}

	public static void main(String[] args) {
		new Exercice1V1();
	}

}
