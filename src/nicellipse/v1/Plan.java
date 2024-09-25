package nicellipse.v1;

import nicellipse.component.NiRectangle;
import nicellipse.component.NiSpace;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class Plan {
	NiSpace space = new NiSpace("Exercice 1", new Dimension(600, 450));
	Satellite satellite = new Satellite(75);
	Satellite satellite2 = new Satellite(150);
	ArrayList<Satellite> list = new ArrayList<>();


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
		this.satellite2.setVitesse(2);
		list.add(satellite);
		list.add(satellite2);
		space.add(satellite);
		space.add(satellite2);
	}

	private void movement(){
		while (true) {

			for(int i = 0; i < list.size(); i++){
				this.moveSatellite(list.get(i));
				if(list.get(i).getX() >= space.getWidth() - list.get(i).getWidth()){
					list.get(i).reset();
				}
			}
		}
	}

	public static void main(String[] args) {
		new Plan();
	}

}
