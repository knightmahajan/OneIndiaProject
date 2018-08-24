package airlineBooking;

public class Ticket { 
	static int ticketCount = 5181993;
	
	private int date;
	private int flightNumber;
	private String flightLength;
	private String timeDeparture;
	private String origin;
	private String destination;
	private String airline;
	private double price;
	private int ticketNum;
	private Flight myFlight;
	
	public Ticket( Flight flight) {
		myFlight = flight;
		date = flight.getDate();
		origin = flight.getOrigin();
		destination = flight.getDestination();
		flightNumber = flight.getFlightNum();
		flightLength = flight.getFlightLength();
		timeDeparture = flight.getTimeDeparture();
		airline = flight.getAirline();
		price = genPrice(flight);
		ticketNum = ticketCount;
		ticketCount++;
		flight.resetSeat();
		flight.resetPrice();
	}
	
	public double genPrice (Flight flight) {
		double Price = flight.getPrice();
		double inflation = Price*((30-flight.getSeat())*0.05); 
		return Price + inflation;
	}
	
	public Flight getFlight() {
		return myFlight;
	}

	public double getPrice() {
		return price;
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
	public String getTimeDeparture(){
		return timeDeparture;
	}
	public String getFlightLength(){
		return flightLength;
	}
	public int getTicketNum() {
		return ticketNum;
	}
	 
}
