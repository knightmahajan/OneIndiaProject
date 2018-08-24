package airlineBooking;

import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Main extends Application {
	
	
	static Stage window;
	static Scene generateScene, mainScene, bookScene, lookUpScene, userInfoScene, itinearyScene;
	static String first, last, address;
	static long phone;
	static Flight chosenFlight;
	static ArrayList<Passenger> passengerList = new ArrayList<Passenger>();
	static Passenger currentPassenger;
	public static void main(String args[]){
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		window.setTitle("Welcome To JAVA Flight Booking");
		
		window.setOnCloseRequest(e ->{
			e.consume(); 
			closeProgram();
		});
		//1.generate flight scene ***************************************************************
		Label generateLabel = new Label("GENERATE THE FLIGHTS");
		Button generateButton = new Button("GENERATE");
		generateButton.setStyle("-fx-base: #667f96");
		generateButton.setOnAction(e -> {
			for(int i=0; i < 50;i++) {
				Airline airline = new Airline();
			}
			System.out.println(Airline.flights.size());
			window.setScene(mainScene); 
		});
		VBox pane1 = new VBox(10);
		pane1.setStyle("-fx-background: #174fae");
		pane1.setAlignment(Pos.CENTER);
		pane1.getChildren().addAll(generateLabel, generateButton);
		generateScene = new Scene(pane1, 300, 300);
		
		
		//2.option scene*************************************************************************
		
		Button lookUpButton = new Button("LOOK UP FLIGHTS");
		lookUpButton.setOnAction(e -> window.setScene(lookUpScene));
		lookUpButton.setStyle("-fx-base: #667f96");
		Button endButton = new Button("EXIT");
		endButton.setStyle("-fx-base: #667f96");
		endButton.setOnAction(e -> closeProgram());
		Label chooseLabel = new Label("Welcome. Pick an option");
		chooseLabel.setAlignment(Pos.CENTER);
		VBox pane2 = new VBox();
		pane2.getChildren().addAll(chooseLabel, lookUpButton, endButton);
		pane2.setAlignment(Pos.CENTER);
		pane2.setSpacing(20);
		pane2.setPadding(new Insets(10,10,10,10));
		pane2.setStyle("-fx-background: #174fae");
		mainScene = new Scene(pane2, 300, 300);
		
		//3.lookUp  Scene *************************************************************************
		
		Label searchLabel = new Label("Search for your flight");
		searchLabel.setAlignment(Pos.CENTER);
		ComboBox<String> origin = new ComboBox<String>();
		origin.getItems().addAll(
			"New York, NY (JFK)", "New York, NY (LGA)","Newark, NJ", "Atlantic City, NJ", "Albany, NY" , 
			"Syracuse, NY", "Allentown, PA", "Middle Town, PA"
		);
		origin.setPromptText("---Select Origin---");
		ComboBox<String> destination = new ComboBox<String>();
		destination.getItems().addAll(
				"New York, NY (JFK)", "New York, NY (LGA)","Newark, NJ", "Atlantic City, NJ", "Albany, NY" , 
				"Syracuse, NY", "Allentown, PA", "Middle Town, PA"
			);
		destination.setPromptText("---Select Destination---");
		ComboBox<Integer> date = new ComboBox<Integer>();
		date.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
		date.setPromptText("---Select a day---");
		ComboBox<String> time = new ComboBox<String>();
		time.getItems().addAll("6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM", "1 PM", "2 PM", "3 PM", "4 PM", 
				"5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM");
		time.setPromptText("---Select time---");
		Button search = new Button("Search");
		search.setStyle("-fx-base: #667f96");
		search.setOnAction(e -> {
			String flightOrigin = getCode(origin.getValue());
			String flightDestination = getCode(destination.getValue());
			chosenFlight=null;
			Airline.findFlight(flightOrigin, flightDestination, date.getValue(), getTime(time.getValue()));
			if(chosenFlight!=null) {
				origin.getSelectionModel().clearSelection();
				destination.getSelectionModel().clearSelection();
				date.getSelectionModel().clearSelection();
				time.getSelectionModel().clearSelection();
				if(currentPassenger != null) {
					if(confirmBooking(chosenFlight.getOrigin(), chosenFlight.getDestination(),
						chosenFlight.getDate(), chosenFlight.getTimeDeparture()) ) {
						currentPassenger.genTicket(chosenFlight);
						window.setScene(bookScene); 
						}else window.setScene(bookScene);
				
				
				}else window.setScene(userInfoScene);
			}else window.setScene(lookUpScene);
			});
			
		VBox searchPane = new VBox();
		searchPane.getChildren().addAll(searchLabel, origin, destination, date, time, search);
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setPadding(new Insets(10,10,10,10));
		searchPane.setSpacing(20);
		searchPane.setStyle("-fx-background: #174fae");
		lookUpScene = new Scene(searchPane, 500, 500);
		
		//4 user information**********************************************************************
		GridPane infoPane = new GridPane(); 
		infoPane.setStyle("-fx-background: #174fae");
		infoPane.setPadding(new Insets(10,10,10,10)); 
		infoPane.setHgap(20); 
		infoPane.setVgap(20);
		
		Label infoLabel = new Label("Enter your information:");
		Label fNameLabel = new Label("First Name:");
		TextField fNameInput = new TextField();
		fNameInput.setPromptText("first name");
		Label lNameLabel = new Label("Last Name:");
		TextField lNameInput = new TextField();
		lNameInput.setPromptText("last name");
		Label addressLabel = new Label("Address:");
		TextField addressInput = new TextField();
		addressInput.setPromptText("address");	
		Label phoneLabel = new Label("Phone:");
		TextField phoneInput = new TextField();
		phoneInput.setPromptText("phone");
		Button enter = new Button("Enter");
		enter.setStyle("-fx-base: #667f96");
		enter.setOnAction(e -> {
			
			if (verifyLong(phoneInput, phoneInput.getText())) {
				if(confirmBooking(chosenFlight.getOrigin(), chosenFlight.getDestination(),
						chosenFlight.getDate(), chosenFlight.getTimeDeparture()) ) {	
					first = (fNameInput.getText()).toUpperCase();
						fNameInput.clear();
					last = (lNameInput.getText()).toUpperCase();
						lNameInput.clear();
					address = (addressInput.getText()).toUpperCase();
						addressInput.clear();
					phone = Long.parseLong(phoneInput.getText());
						phoneInput.clear();
					Passenger newPassenger = new Passenger(first, last, address, phone);
					newPassenger.genTicket(chosenFlight);
					passengerList.add(newPassenger);
					currentPassenger=newPassenger;
					window.setScene(bookScene); 
				}else window.setScene(lookUpScene);
				
			} else window.setScene(userInfoScene);
			
		});

		infoPane.addRow(0, infoLabel);
		infoPane.addRow(1, fNameLabel, fNameInput);
		infoPane.addRow(2, lNameLabel, lNameInput);
		infoPane.addRow(3, addressLabel, addressInput);
		infoPane.addRow(4, phoneLabel, phoneInput);
		infoPane.addRow(5, enter);
		infoPane.setAlignment(Pos.CENTER);
		userInfoScene = new Scene(infoPane, 500, 500);	
		
		
		//5.booking scene ****************************************************************************
		Label bookingLabel = new Label("Booking");
		bookingLabel.setAlignment(Pos.CENTER);
		Button bookNewButton = new Button("BOOK ANOTHER FLIGHT");
		bookNewButton.setStyle("-fx-base: #667f96");
		bookNewButton.setOnAction(e -> window.setScene(lookUpScene));
		Button itineraryButton = new Button("VIEW ITINERARY");
		itineraryButton.setStyle("-fx-base: #667f96");
		itineraryButton.setOnAction(e -> {
			itinerary(currentPassenger);
			window.setScene(bookScene);
		});
		Button signout = new Button("Sign Out");
		signout.setStyle("-fx-base: #ee4014");
		signout.setOnAction(e->{
			currentPassenger = null;
			chosenFlight = null;
			window.setScene(mainScene);
		});
		GridPane bookingPane = new GridPane();
		bookingPane.setStyle("-fx-background: #174fae");
		bookingPane.add(bookingLabel, 0, 0);
		bookingPane.addRow(1, bookNewButton, itineraryButton);
		bookingPane.addRow(2, signout);
		bookingPane.setAlignment(Pos.CENTER);
		bookingPane.setPadding(new Insets(10,10,10,10));
		bookingPane.setHgap(20); 
		bookingPane.setVgap(20);
		bookScene = new Scene(bookingPane, 300,300);
			
		
		//***********************************************************************************************
		window.setScene(generateScene);
		window.show();
		
	}
	
	private String getCode(String x) {
		String airportCode = null; 
		if(x.contentEquals("New York, NY (JFK)")) airportCode = "JFK";
		if(x.contentEquals("New York, NY (LGA)")) airportCode = "LGA";
		if(x.contentEquals("Newark, NJ")) airportCode = "EWR";
		if(x.contentEquals("Atlantic City, NJ")) airportCode = "ACY";
		if(x.contentEquals("Albany, NY")) airportCode = "ALB";
		if(x.contentEquals("Syracuse, NY")) airportCode = "SYR";
		if(x.contentEquals("Middle Town, PA")) airportCode = "MDT";
		if(x.contentEquals("Allentown, PA")) airportCode = "ABE";
		return airportCode;
	}
	
	private void closeProgram() {
		Boolean answer = AlertBox.confirmBox("Are you sure you want to exit", "Confirm exit?");
		if(answer) window.close();
	}
	
	private boolean confirmBooking(String o, String d, int date, String time) {
		Boolean answer = AlertBox.confirmBooking(o, d, date, time);
		return answer;
	}
	
	private void itinerary(Passenger currentPassenger) {
			Stage itin = new Stage();
			itin.setTitle("Itinerary for " + currentPassenger.getFirstName()+" "+
					currentPassenger.getLastName() );
			Label itinerary = new Label("Itinerary for " + currentPassenger.getFirstName()+" "+
				currentPassenger.getLastName() +"\n"+currentPassenger.numTickets() + " tickets booked");
			ListView<String> itineraryList = new ListView<String>();
			for(int i=0; i<currentPassenger.numTickets(); i++) {
				itineraryList.getItems().add(currentPassenger.toStringTicket(currentPassenger.getTicket(i)));
			}
			itineraryList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			itineraryList.setMaxSize(500, 400);
			Button cancel = new Button("CANCEL FLIGHT");
			cancel.setStyle("-fx-base: #ee4014");
			cancel.setOnAction(e->{	
				int index = itineraryList.getSelectionModel().getSelectedIndex();
				Ticket canTicket= currentPassenger.getTicket(index);
				if( confirmCancel(canTicket.getOrigin(),canTicket.getDestination(), canTicket.getDate()) ) {
					currentPassenger.cancelTicket(canTicket);
				};
				itin.close();
			});
			Button back = new Button("BACK");
			back.setStyle("-fx-base: #667f96");
			back.setOnAction(e -> itin.close());
			
			BorderPane border = new BorderPane();
			border.setStyle("-fx-background: #174fae");
			HBox button = new HBox();
			button.getChildren().addAll(cancel, back);
			button.setAlignment(Pos.CENTER);
			button.setSpacing(125);
			
			VBox list = new VBox(10);
			list.getChildren().addAll(itineraryList); 
			border.setCenter(list);
			border.setBottom(button);
			border.setTop(itinerary);
			itinearyScene = new Scene (border, 500, 500); 
			itin.setScene(itinearyScene);
			itin.show();
	}
	
	private boolean confirmCancel(String o, String d, int date) {
		Boolean answer = AlertBox.confirmCancel(o, d, date);
		return answer;
	}
	
	private boolean verifyLong(TextField input, String x) {
		boolean answer;
		if(x.matches("\\d+") ) answer= true;
		else answer = false;
		return answer;
	}
	
	private int getTime(String x) {
		int time = 0;
		if(x=="6 AM") time = 6;
		if(x=="7 AM") time = 7;
		if(x=="8 AM") time = 8;
		if(x=="9 AM") time = 9;
		if(x=="10 AM") time = 10;
		if(x=="11 AM") time = 11;
		if(x=="12 PM") time = 12;
		if(x=="1 PM") time = 13;
		if(x=="2 PM") time = 14;
		if(x=="3 PM") time = 15;
		if(x=="4 PM") time = 16;
		if(x=="5 PM") time = 17;
		if(x=="6 PM") time = 18;
		if(x=="7 PM") time = 19;
		if(x=="8 PM") time = 20;
		if(x=="9 PM") time = 21;
		if(x=="10 PM") time = 22;
		return time;
	}
		
}