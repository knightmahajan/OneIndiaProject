package airlineBooking;

import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AlertBox { 
		static boolean answer;
	
	public static boolean confirmBox(String title, String message) {
		Stage window = new Stage();
		window.setTitle(title);
		Label confirmMessage = new Label(message);
		window.initModality(Modality.APPLICATION_MODAL);
		
		Button exitButton = new Button("Exit"); 
		exitButton.setStyle("-fx-base: #ee4014");
		exitButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		Button stayButton = new Button("Stay");
		stayButton.setStyle("-fx-base: #ee4014");
		stayButton.setOnAction(e -> {
			answer = false; 
			window.close(); 
		});
		
		VBox confirmLayout = new VBox(20);
		confirmLayout.getChildren().addAll(confirmMessage, exitButton, stayButton);
		confirmLayout.setAlignment(Pos.CENTER);
		Scene confirmScene = new Scene(confirmLayout, 300, 200);
		window.setScene(confirmScene);
		window.showAndWait();
		 
		
		return answer;
	}
	
	public static boolean confirmBooking(String o, String d, int date, String time) {
		Stage window = new Stage();
		window.setTitle("Confirm your reservation");
		window.setOnCloseRequest(e ->{
			answer = false;
			window.close();
		});
		Label confirmBooking = new Label("Confirm your reservation from "+o+" to "+d+
				"\n on August " + date + ", 2017 at "+time);
		window.initModality(Modality.APPLICATION_MODAL);
		
		Button confirm = new Button("CONFIRM");
		confirm.setStyle("-fx-base: #ee4014");
		confirm.setOnAction(e ->{
			answer = true;
			window.close();
		});
		Button doNot = new Button("DO NOT BOOK");
		doNot.setStyle("-fx-base: #ee4014");
		doNot.setOnAction(e ->{
			answer = false;
			window.close();
		});
		
		GridPane layout = new GridPane();
		layout.addRow(2, confirmBooking);
		layout.addRow(4, confirm, doNot);
		layout.setAlignment(Pos.CENTER);
		layout.setHgap(20);
		layout.setVgap(20);
		Scene confirmScene = new Scene(layout, 500, 300);
		window.setScene(confirmScene);
		window.showAndWait();
		
		return answer;
	}
	
	public static boolean confirmCancel(String o, String d, int date) {
		Stage window = new Stage();
		window.setTitle("Confirm Cancellation");
		Label confirmCancel = new Label("Canceling flight from "+o+" to "+d+
				"\n on August " + date + ", 2017");
		window.initModality(Modality.APPLICATION_MODAL);
		
		Button confirm = new Button("CANCEL");
		confirm.setStyle("-fx-base: #ee4014");
		confirm.setOnAction(e ->{
			answer = true;
			window.close();
		});
		Button doNot = new Button("DO NOT CANCEL");
		doNot.setStyle("-fx-base: #ee4014");
		doNot.setOnAction(e ->{
			answer = false;
			window.close();
		});
		
		GridPane layout = new GridPane();
		layout.addRow(2, confirmCancel);
		layout.addRow(4, confirm, doNot);
		layout.setAlignment(Pos.CENTER);
		layout.setHgap(20);
		layout.setVgap(20);
		Scene confirmScene = new Scene(layout, 500, 300);
		window.setScene(confirmScene);
		window.showAndWait();
		
		return answer;
	}
}