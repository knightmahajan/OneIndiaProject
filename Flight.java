package airlineBooking;

import java.util.Random;
import java.util.ArrayList;

public class Flight {
	static int flightCount = 1;
	static String []smallHubs = { "ALB", "SYR", "ACY", "MDT" , "ABE"};
	static String []largeHubs = {"JFK", "EWR", "LGA"};
	static ArrayList<Ticket> ticketIssued = new ArrayList<Ticket>(); 
	static int dateCount =1;
	
	private String airline; 
	private int date;
	private int flightNumber;
	private int seats;
	private int seatsFilled;
	private String flightLength;
	private int departure;
	private String timeDeparture;
	private String origin;
	private String destination;
	private double price;
	
	public Flight(String airline){
		this.airline = airline;
	}
	
	//generates initial flight
	public void genFlight(){
		origin = setOrigin(smallHubs, largeHubs);
		destination = setDestination(smallHubs, largeHubs, origin);
		date = dateCount;
		flightNumber = flightCount;
		flightCount ++;
		flightLength = generateFlightLength(origin, destination);
		seats = 30;
		seatsFilled=0;
		departure = genDeparture();
		timeDeparture = timeDepart(departure);
		price = 100.00;
	}
	//generates the return flight (reverse the origin with destination)
	public void genReturnFlight(String origin, String destination, double time) {
		this.origin = origin;
		this.destination = destination;
		date = dateCount;
		flightNumber = flightCount; 
		flightCount ++;
		flightLength = generateFlightLength(origin, destination);
		seats = 30;
		seatsFilled=0;
		departure = genDeparture();
		timeDeparture = timeDepart(departure);
		price = 100.00;
	}
	//repeats each flight over 31 days
	public void repeatFlight(Flight flight) {
		origin = flight.getOrigin();
		price = flight.getPrice();
		destination = flight.getDestination();
		flightNumber = flight.getFlightNum();
		flightLength = flight.getFlightLength();
		seats = flight.getSeat();
		seatsFilled=flight.getSeatFilled();
		departure = flight.getDeparture();
		timeDeparture = flight.getTimeDeparture();
		date = dateCount;
	}
	//randomly chooses the flights origins
	public String setOrigin(String []small, String []large) {
		Random rand = new Random();
		int dice = rand.nextInt(2);
		if(dice==0)return small[rand.nextInt(small.length)];
		else return large[rand.nextInt(large.length)];
	}
	//randomly chooses the flights destination
	public String setDestination(String []small, String []large, String org) {
		Random rand = new Random();
		if(org=="JFK" || org =="LGA" || org=="EWR") return small[rand.nextInt(small.length)];
		return large[rand.nextInt(large.length)];
	}
	
	//get departure
	public int genDeparture() {
		Random rand = new Random();
		return (rand.nextInt(16)+6);	
	}
	
	public String timeDepart(int x) {	
		if(x==12) return "12 PM";
		if(x>=13) return x-12 +" PM";
		return x +" AM";
	}
	
	//resets after a ticket is issued
	public void resetSeat(){
		seats--;
		seatsFilled++;
	}
	
	//reset ticket after cancel
	public void cancelTicket(Ticket tic) {
		seats++;
		seatsFilled--;
		ticketIssued.remove(tic);
		price = price - (price*((30-seats)*0.05));
	}
	
	//generates the durations of flight
	public String generateFlightLength(String O, String D){
		String length = new String();
		if(O =="ALB" || D == "ALB") length = "1h 15m";
		if(O =="SYR" || D == "SYR") length = "1h 10m";
		if(O =="ACY" || D == "ACY") length = "30m";
		if(O =="MDT" || D == "MDT") length = "2h";
		if(O =="ABE" || D == "ABE") length = "2h";
		return length;
	}
	public void setTicket(Ticket issuedTick) {
		ticketIssued.add(issuedTick);
	}
	
	//Accessors
	public int getSeat(){
		return seats;
	}
	public int getSeatFilled(){
		return seatsFilled;
	}
	public void resetPrice() {
		price = (price*(seatsFilled)*0.05) + price;
	}
	public String getAirline(){
		return airline;
	}
	public String getOrigin(){
		return origin;
	}
	public String getDestination(){
		return destination;
	}
	public int getFlightNum(){
		return flightNumber;
	}
	public int getDate(){
		return date;
	}
	public int getDeparture(){
		return departure; 
	}
	public String getFlightLength(){
		return flightLength;
	}
	public String getTimeDeparture() {
		return timeDeparture;
	}
	public double getPrice() {
		return price;
	}
 
}