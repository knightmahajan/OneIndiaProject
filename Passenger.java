package airlineBooking;

import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Passenger {
	
	private ArrayList<Ticket> myTickets = new ArrayList<Ticket>();
	private String firstName;
	private String lastName;
	private String address; 
	private long phone;

	public Passenger(String first, String last, String address, long phone) {
		firstName = first;
		lastName = last;
		this.address = address;
		this.phone = phone; 
	}
	public void genTicket(Flight myFlight) {
		Ticket myTicket = new Ticket(myFlight);
		myTickets.add(myTicket);
		myFlight.setTicket(myTicket);
		printTicket(myTicket);
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {    
		return address;
	}
	public long getPhone() {
		return phone;
	}
	public int numTickets() {
		return myTickets.size();
	}
	public Ticket getTicket(int i) {
		return myTickets.get(i);
	}
	public String toStringTicket(Ticket tic) {
		return ("TICKET#"+tic.getTicketNum()+"\nPassenger: " + firstName+ " " +lastName+"\n"+ tic.getAirline() +"-----Flight#: "+tic.getFlightNum()+"-----August "+tic.getDate()+", 2017\n"
				+ "Origin: "+tic.getOrigin()+ "----- Destination: "+tic.getDestination()+"  Time: "+tic.getTimeDeparture()+
				"    Duration: "+tic.getFlightLength()+"\n" +"Price = $" + tic.getPrice() +" Balance = $0.00");
	}
	public void printTicket(Ticket tic) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("YOU BOOKED A FLIGHT");
		Label success = new Label("YOU SUCCESSFULLY BOOK YOUR FLIGHT FROM "+tic.getOrigin()+" TO "+ tic.getDestination());
		Text ticket = new Text();
		ticket.setText(toStringTicket(tic));
		VBox pane = new VBox(20);
		pane.setStyle("-fx-background: #174fae");
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(success, ticket);
		Scene scene = new Scene(pane,700,250);
		window.setScene(scene);
		window.show(); 
	}

	public void cancelTicket(Ticket tic) {
		Flight canFlightTicket = tic.getFlight();
		canFlightTicket.cancelTicket(tic);
		myTickets.remove(tic);
		
	}
}
