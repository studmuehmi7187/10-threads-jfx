package de.thro.inf.prg3.a10.kitchen.workers;

import de.thro.inf.prg3.a10.internals.displaying.ProgressReporter;
import de.thro.inf.prg3.a10.kitchen.KitchenHatch;
import de.thro.inf.prg3.a10.model.Dish;

import java.util.Random;

public class Waiter implements Runnable{

	String name;
	ProgressReporter progressReporter;
	KitchenHatch kitchenHatch;

	public Waiter(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
		this.name = name;
		this.progressReporter = progressReporter;
		this.kitchenHatch = kitchenHatch;
	}

	@Override
	public void run() {
		while(true){
			Dish d = kitchenHatch.dequeueDish();
			if(d == null){
				d = kitchenHatch.dequeueDish(5000);
				System.out.println("am I still null");
			}
			if(d == null){
				System.out.println("ight imma head out");
				break;
			}

			Random r = new Random();
			int time = r.nextInt(10000);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressReporter.updateProgress();
		}
		progressReporter.notifyWaiterLeaving();
	}
}
