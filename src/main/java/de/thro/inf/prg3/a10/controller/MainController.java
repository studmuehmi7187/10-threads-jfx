package de.thro.inf.prg3.a10.controller;

import de.thro.inf.prg3.a10.internals.displaying.ProgressReporter;
import de.thro.inf.prg3.a10.kitchen.KitchenHatch;
import de.thro.inf.prg3.a10.kitchen.KitchenHatchImpl;
import de.thro.inf.prg3.a10.kitchen.workers.Cook;
import de.thro.inf.prg3.a10.kitchen.workers.Waiter;
import de.thro.inf.prg3.a10.model.Order;
import de.thro.inf.prg3.a10.util.NameGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ResourceBundle;

import static de.thro.inf.prg3.a10.KitchenHatchConstants.*;

public class MainController implements Initializable {

	private final ProgressReporter progressReporter;
	private final KitchenHatch kitchenHatch;
	private final NameGenerator nameGenerator;

	@FXML
	private ProgressIndicator waitersBusyIndicator;

	@FXML
	private ProgressIndicator cooksBusyIndicator;

	@FXML
	private ProgressBar kitchenHatchProgress;

	@FXML
	private ProgressBar orderQueueProgress;

	public MainController() {
		nameGenerator = new NameGenerator();

		//TODO assign an instance of your implementation of the KitchenHatch interface
		this.kitchenHatch = new KitchenHatchImpl();
		this.progressReporter = new ProgressReporter(kitchenHatch, COOKS_COUNT, WAITERS_COUNT, ORDER_COUNT, KITCHEN_HATCH_SIZE);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderQueueProgress.progressProperty().bindBidirectional(this.progressReporter.orderQueueProgressProperty());
		kitchenHatchProgress.progressProperty().bindBidirectional(this.progressReporter.kitchenHatchProgressProperty());
		waitersBusyIndicator.progressProperty().bindBidirectional(this.progressReporter.waitersBusyProperty());
		cooksBusyIndicator.progressProperty().bind(this.progressReporter.cooksBusyProperty());

		for(int i = 0; i < ORDER_COUNT; i++){
			kitchenHatch.setOrder(new Order(nameGenerator.getRandomDish()));
		}

		/* TODO create the cooks and waiters, pass the kitchen hatch and the reporter instance and start them */
		for(int i = 0; i < COOKS_COUNT; i++){
			Thread t = new Thread(new Cook(nameGenerator.generateName(), this.kitchenHatch, this.progressReporter));
			t.start();
		}
		for(int i = 0; i < WAITERS_COUNT; i++){
			Thread t = new Thread(new Waiter(nameGenerator.generateName(), this.kitchenHatch, this.progressReporter));
			t.start();
		}
	}
}
