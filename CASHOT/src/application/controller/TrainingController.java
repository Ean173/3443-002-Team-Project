package application.controller;

import java.io.IOException;
import java.util.ArrayList;

import application.model.CashotSystem;
import application.model.Item;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * This is the controller for the training page. Like the CashierController, it
 * logs the trainee's inputs for items being purchased and calculates them.
 * 
 * @author Darean Wilde grl167 63678621
 * @author Jacob Shawver fww704 36242636
 * @author Majerus Sims hug180 79595196
 * @author Alexander Delgado tvh991 79595706
 *
 */

public class TrainingController implements EventHandler {

	@FXML
	Button button00;
	@FXML
	Button button01;
	@FXML
	Button button02;
	@FXML
	Button button03;
	@FXML
	Button button10;
	@FXML
	Button button11;
	@FXML
	Button button12;
	@FXML
	Button button13;
	@FXML
	Button button20;
	@FXML
	Button button21;
	@FXML
	Button button22;
	@FXML
	Button button23;
	@FXML
	Button button30;
	@FXML
	Button button31;
	@FXML
	Button button32;
	@FXML
	Button button33;
	@FXML
	Button button40;
	@FXML
	Button button41;
	@FXML
	Button button42;
	@FXML
	Button button43;
	@FXML
	Button button50;
	@FXML
	Button button51;
	@FXML
	Button button52;
	@FXML
	Button button53;

	@FXML
	TextArea receiptNames;
	@FXML
	TextArea receiptPrices;
	@FXML
	TextArea receiptTotal;

	Button trainingButtons[][];

	static ArrayList<Item> itemsInOrder;

	@FXML
	private AnchorPane content;
	CashotSystem system;

	/**
	 * Starts a new order with the trainee instead of an established employee
	 * 
	 * @throws IOException
	 */

	public void initialize() throws IOException {
		// Load items ?
		system = CashotSystem.getInstance();

		system.setController(this);

		trainingButtons = new Button[6][4];
		buttonToMatrix();
		// System.out.println(button00);

		system.getItemsInButtons("training");
		system.newOrder("training");
		itemsInOrder = new ArrayList<Item>();

	}

	/**
	 * 
	 * Handle sets the local names to blank, addes items to the buttons and
	 * initializes the order.
	 * 
	 * @param event
	 */
	@Override
	public void handle(Event event) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (trainingButtons[i][j] == event.getSource()) {
					itemsInOrder = system.addItemToOrder(i, j);
					// receiptList.setText(itemsInOrder.toString());
					String strName = "";
					String strPrice = "";
					String strTotal = "";
					double price = 0;
					String moneyString = "";
					for (Item item : itemsInOrder) {
						price = item.getPrice();
						moneyString = CashotSystem.dblToMoneyString((price));
						// str += String.format("%-50s %15s\n", item.getName(),
						// moneyString);
						strName += item.getName() + "\n";
						strPrice += moneyString + "\n";
						// System.out.printf("%-50s %15s\n", item.getName(),
						// moneyString);
						// System.out.print(str);
						// receiptList.setText(str);
					}
					double total;
					receiptNames.setText(strName);
					receiptPrices.setText(strPrice);
					total = system.getOrderTotal();
					strTotal = CashotSystem.dblToMoneyString((total));
					receiptTotal.setText(strTotal);
				}
			}
		}
	}

	/**
	 * loads the training ring up customer screen.
	 * 
	 * @param event
	 * @throws IOException
	 */

	public void loadTrainingRingUpCustomer(Event event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/view/TrainingRingUpCustomer.fxml"));
		content.getChildren().setAll(pane);
	}

	/**
	 * Loads the main screen.
	 * 
	 * @param event
	 * @throws IOException
	 */

	public void loadMain(Event event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/view/main.fxml"));
		content.getChildren().setAll(pane);
	}

	/**
	 * loads the Cashier screen.
	 * 
	 * @param event
	 * @throws IOException
	 */

	public void loadCashier(Event event) throws IOException {
		try {
			if (system.getSignedIn().isAdmin().equals("TRUE") || system.getSignedIn().getCashier().equals("TRUE")) {
				bypassEmployeeLogin(event);
			} else {
				loadEmployeeLogin(event);
			}
		} catch (Exception e) {
			loadEmployeeLogin(event);
		}
	}

	/**
	 * Loads the employee login screen.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void loadEmployeeLogin(Event event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/view/employeeLoginScreen.fxml"));
		content.getChildren().setAll(pane);
	}

	/**
	 * Passes the employee login screen if called.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void bypassEmployeeLogin(Event event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/view/cashier.fxml"));
		content.getChildren().setAll(pane);
	}

	/**
	 * Loads the Administer screen. If the user is already an administrator,
	 * they do not need to log in again.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void loadAdminister(Event event) throws IOException {
		try {
			if (system.getSignedIn().isAdmin().equals("TRUE")) {
				bypassAdminLogin(event);
			} else {
				loadAdminLogin(event);
			}
		} catch (Exception e) {
			loadAdminLogin(event);
		}
	}

	/**
	 * Passes the admin login screen if called.
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void bypassAdminLogin(Event event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/view/administor.fxml"));
		content.getChildren().setAll(pane);
	}

	/**
	 * Loads the admin screen.
	 * 
	 * @param event
	 * @throws IOException
	 */

	public void loadAdminLogin(Event event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/view/adminLoginScreen.fxml"));
		content.getChildren().setAll(pane);
	}

	/**
	 * Hides buttons that are not in use.
	 */
	public void hideUnimplementedButtons() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				Button button = trainingButtons[i][j];
				if (button.getText().equals("")) {
					button.setVisible(false);
				}
			}
		}
	}

	/**
	 * sets all of the buttons to a 2-dimentional array.
	 */
	public void buttonToMatrix() {

		trainingButtons[0][0] = button00;
		trainingButtons[0][1] = button01;
		trainingButtons[0][2] = button02;
		trainingButtons[0][3] = button03;
		trainingButtons[1][0] = button10;
		trainingButtons[1][1] = button11;
		trainingButtons[1][2] = button12;
		trainingButtons[1][3] = button13;
		trainingButtons[2][0] = button20;
		trainingButtons[2][1] = button21;
		trainingButtons[2][2] = button22;
		trainingButtons[2][3] = button23;
		trainingButtons[3][0] = button30;
		trainingButtons[3][1] = button31;
		trainingButtons[3][2] = button32;
		trainingButtons[3][3] = button33;
		trainingButtons[4][0] = button40;
		trainingButtons[4][1] = button41;
		trainingButtons[4][2] = button42;
		trainingButtons[4][3] = button43;
		trainingButtons[5][0] = button50;
		trainingButtons[5][1] = button51;
		trainingButtons[5][2] = button52;
		trainingButtons[5][3] = button53;

	}

	/**
	 * Sets the button to the item passed in.
	 * 
	 * @param item
	 */
	public void setButton(Item item) {
		Button button = trainingButtons[item.getRow()][item.getColumn()];
		// System.out.println(button);
		double price = item.getPrice();
		String moneyString = CashotSystem.dblToMoneyString((price));

		button.setText(item.getName() + "\n" + moneyString);

	}
}
