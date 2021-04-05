package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;

import application.model.CashotSystem;
import application.model.Employee;
import application.model.Item;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class AdminLoginController implements EventHandler{
	@FXML TextField adminUsrName;
	@FXML TextField adminUsrPw;
	@FXML Button btnLogin;
	String inputUN;
	String inputPW;
	@FXML private AnchorPane content;
	
CashotSystem system = new CashotSystem();
	
	public void initialize( ) throws IOException{
		//Load items ?
		system.setController(this);
		
		system.getEmployees();
		

}
	public void loadMain(Event event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/view/main.fxml"));
		content.getChildren().setAll(pane);
	}
	
	public void loadAdminister(Event event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/application/view/administor.fxml"));
		content.getChildren().setAll(pane);
	}

	@Override
	public void handle(Event event) {
		//System.out.println("test1");
		inputUN = adminUsrName.getText();
		inputPW = adminUsrPw.getText();
	
		for(Employee employee: system.getEmployees()){
			//System.out.println("test2");
			if(employee.getUserName().equals(inputUN) && employee.getEmployeePassword().equals(inputPW) && employee.isAdmin().equals("TRUE")){
				try {
					loadAdminister(event);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				break;
			}
		}
		
	}
}