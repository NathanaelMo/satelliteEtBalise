package nicellipse.v1;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Plan {
	NiSpace space = new NiSpace("Exercice 1", new Dimension(600, 450));
	Satellite satellite = new Satellite(4);
	Balise balise ;

	void moveSatellite(Satellite satellite) {
		final Runnable doit = new Runnable() {
			public void run() {
				satellite.setLocation(new Point(satellite.move()));
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

	public Plan() {
		this.init();
		space.openInWindow();
		this.movement();

	}

	private void init(){
		space.setBackground(Color.lightGray);
		this.balise = new Balise(2,50, space.getHeight());
		space.add(balise);
		space.add(satellite);
	}

	private void movement(){
		while (true) {
			this.moveSatellite(satellite);
			if(satellite.getX() >= space.getWidth() - satellite.getWidth()){
				satellite.reset();
			}
			this.balise.parcours();

		}
	}

	public static void main(String[] args) {
		new Plan();
	}

}
