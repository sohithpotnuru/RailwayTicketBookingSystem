import java.io.Serializable;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pnr;
    private String bookedBy;
    private Train train;
    private Passenger passenger;
    private String travelClass;
    private String journeyDate;
    private double fare;
    private String paymentStatus;

    public Ticket(String pnr, String bookedBy, Train train, Passenger passenger,
                  String travelClass, String journeyDate, double fare, String paymentStatus) {
        this.pnr = pnr;
        this.bookedBy = bookedBy;
        this.train = train;
        this.passenger = passenger;
        this.travelClass = travelClass;
        this.journeyDate = journeyDate;
        this.fare = fare;
        this.paymentStatus = paymentStatus;
    }

    public String getPnr() {
        return pnr;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public Train getTrain() {
        return train;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public double getFare() {
        return fare;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getFormattedReceipt() {
        return "\n============================================\n"
                + "            RAILWAY E-TICKET RECEIPT        \n"
                + "============================================\n"
                + "PNR Number      : " + pnr + "\n"
                + "Booked By       : " + bookedBy + "\n"
                + "--------------------------------------------\n"
                + "Train Number    : " + train.getTrainNumber() + "\n"
                + "Train Name      : " + train.getTrainName() + "\n"
                + "Route           : " + train.getSource() + " -> " + train.getDestination() + "\n"
                + "Departure       : " + train.getDepartureTime() + "\n"
                + "Arrival         : " + train.getArrivalTime() + "\n"
                + "Journey Date    : " + journeyDate + "\n"
                + "Travel Class    : " + travelClass + "\n"
                + "--------------------------------------------\n"
                + "Passenger Name  : " + passenger.getName() + "\n"
                + "Passenger Age   : " + passenger.getAge() + "\n"
                + "Passenger Gender: " + passenger.getGender() + "\n"
                + "--------------------------------------------\n"
                + "Fare            : Rs. " + fare + "\n"
                + "Payment Status  : " + paymentStatus + "\n"
                + "============================================\n"
                + "      Thank you for booking with us!        \n"
                + "============================================\n";
    }
}
