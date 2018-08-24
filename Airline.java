package airlineBooking;

import java.util.Random;
import java.util.ArrayList;

import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Airline {
	static String airlines[]={"Delta" , "American Airlines" , "JetBlue", "Endeavor Air", "Republic Airways"};
	private String airline; 
	static ArrayList<Flight> flights = new ArrayList<Flight>(); 
	//flight generator
	public Airline(){
		airline = pickFlight(airlines);
		//generates the flight 
		Flight flight = new Flight(airline);
		flight.genFlight();
		flights.add(flight); // save to list
		//generate a repeat flight for each day and add to list
		for(int i=0; i<30;i++) {
			Flight.dateCount++;
			Flight repeatFlight = new Flight(airline);
			repeatFlight.repeatFlight(flight);
			flights.add(repeatFlight);
		}
		Flight.dateCount=1; // reset the date to the first date
		// generates the return flight and add to list
		Flight returnFlight = new Flight(airline);
		returnFlight.genReturnFlight(flight.getDestination(), flight.getOrigin(), flight.getDeparture());
		flights.add(returnFlight);
		//generate a repeat the return flight for each day and add to list
		for(int i=0; i<30;i++) {
			Flight.dateCount++;
			Flight repeatReturn = new Flight(airline);
			repeatReturn.repeatFlight(returnFlight);
			flights.add(repeatReturn);
		}
		Flight.dateCount=1;
	}
	//randomly pick the flight
	public String pickFlight(String []airlines){
		Random x = new Random();
		return airlines[x.nextInt(airlines.length)];
	}
	//print the flight info on screen
	public static void printFlight(Flight flight) {
		System.out.printf("%s-----Flight#: %s-----August %s, 2017\n", flight.getAirline(), flight.getFlightNum(), flight.getDate());
		System.out.printf("Origin: %s ----- Destination: %s  Time: %s    Duration: %s\n", 
				flight.getOrigin(), flight.getDestination(), flight.getTimeDeparture(), flight.getFlightLength());
		System.out.printf("Seats avilable: %s\nSeats Filled: %s\n\n", flight.getSeat(), flight.getSeatFilled());

	}
	
	public static String toStringFlight(Flight flight) { 
		String print = flight.getAirline() +"-----Flight#: "+flight.getFlightNum()+"-----August "+flight.getDate()+", 2017\n"
				+ "Origin: "+flight.getOrigin()+ "----- Destination: "+flight.getDestination()+"  Time: "+flight.getTimeDeparture()+
				"    Duration: "+flight.getFlightLength()+"\n"+"Seats avilable: "+ flight.getSeat() + "\nPrice: " + flight.getPrice();
		return print;
	}
	
	public static void findFlight(String origin, String destination, int date, int time) {
		
		
		Stage window = new Stage();
		window.setTitle("Flights from " + origin + " to " + destination);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setOnCloseRequest(e ->{
			e.consume();
			Boolean answer = AlertBox.confirmBox("Are you sure you want to exit", "Confirm exit?");
			if(answer) window.close();
		});
		
		ListView<String> flightList= new ListView<>();
		ArrayList<Flight> searchFlight = new ArrayList<Flight>();
		for(int i= 0 ; i<flights.size();i++) {
			Flight flight = flights.get(i); 
			if(flight.getDate()==date) 
				if(flight.getOrigin()==origin) 
					if(flight.getDestination()==destination)
						if((flight.getDeparture()>=(time-4) ) && ( flight.getDeparture() <= (time+4) ) )
							if(flight.getSeatFilled()<30){ 
							flightList.getItems().add(toStringFlight(flight));
							searchFlight.add(flight);
						}
		}
		flightList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		flightList.setMaxSize(500, 400);
		Button confirm = new Button("Confirm");
		confirm.setStyle("-fx-base: #667f96");
		confirm.setOnAction(e ->{
			int index=flightList.getSelectionModel().getSelectedIndex();
			Main.chosenFlight=searchFlight.get(index);
			window.close();
		});
		Button exit = new Button("Exit");
		exit.setStyle("-fx-base: #667f96");
		exit.setOnAction(e ->{
			Boolean answer = AlertBox.confirmBox("Are you sure you want to exit", "Confirm exit?");
			if(answer) window.close();
		});
		BorderPane border = new BorderPane();
		border.setStyle("-fx-background: #174fae");
		HBox button = new HBox();
		button.getChildren().addAll(confirm, exit);
		button.setAlignment(Pos.TOP_CENTER);
		button.setSpacing(125);
		
		VBox list = new VBox(10);
		list.getChildren().addAll(flightList); 
		border.setCenter(list);
		border.setBottom(button);
		Scene flightScene = new Scene (border, 500, 500); 
		window.setScene(flightScene);
		window.showAndWait();
		
		 
	}
	

}