package de.thro.inf.prg3.a10.kitchen.workers;

import de.thro.inf.prg3.a10.internals.displaying.ProgressReporter;
import de.thro.inf.prg3.a10.kitchen.KitchenHatch;
import de.thro.inf.prg3.a10.model.Dish;
import de.thro.inf.prg3.a10.model.Order;

public class Cook implements Runnable {

	String name;
	ProgressReporter progressReporter;
	KitchenHatch kitchenHatch;

	public Cook(String name, KitchenHatch kitchenHatch, ProgressReporter progressReporter){
		this.name = name;
		this.progressReporter = progressReporter;
		this.kitchenHatch = kitchenHatch;
	}

	@Override
	public void run() {
		while(kitchenHatch.getOrderCount() != 0){
			Order o = kitchenHatch.dequeueOrder();
			if(o == null){
				break;
			}
			Dish d = new Dish(o.getMealName());
			try {
				Thread.sleep(d.getCookingTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			kitchenHatch.enqueueDish(d);
			progressReporter.updateProgress();
		}
		progressReporter.notifyCookLeaving();
	}
}
