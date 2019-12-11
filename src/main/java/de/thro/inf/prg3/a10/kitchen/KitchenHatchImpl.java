package de.thro.inf.prg3.a10.kitchen;

import de.thro.inf.prg3.a10.model.Dish;
import de.thro.inf.prg3.a10.model.Order;

import java.util.LinkedList;

import static de.thro.inf.prg3.a10.KitchenHatchConstants.KITCHEN_HATCH_SIZE;
import static de.thro.inf.prg3.a10.KitchenHatchConstants.ORDER_COUNT;

public class KitchenHatchImpl implements KitchenHatch {

	private int orderCount = ORDER_COUNT;
	private int dishesCount = 0;
	private LinkedList<Dish> dishQueue = new LinkedList<Dish>();
	private LinkedList<Order> orderQueue = new LinkedList<Order>();


	@Override
	public void setOrder(Order o) {
		orderQueue.add(o);
	}

	@Override
	public int getMaxDishes() {
		return KITCHEN_HATCH_SIZE;
	}

	@Override
	public synchronized Order dequeueOrder(long timeout) {
		if(orderCount > 0){
			orderCount--;
			return orderQueue.removeFirst();
		}
		else{
			return null;
		}
	}

	@Override
	public int getOrderCount() {
		return orderCount;
	}

	@Override
	public synchronized Dish dequeueDish(long timeout) {
		if(dishesCount == 0){
			try {
				wait(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(dishesCount != 0) {
			dishesCount--;
			notifyAll();
			return dishQueue.removeFirst();
		}else{
			return null;
		}
	}

	@Override
	public synchronized void enqueueDish(Dish m) {
		if(dishesCount >= KITCHEN_HATCH_SIZE) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(dishesCount < KITCHEN_HATCH_SIZE) {
			dishQueue.add(m);
			dishesCount++;
		}
	}

	@Override
	public int getDishesCount() {
		return dishesCount;
	}
}
