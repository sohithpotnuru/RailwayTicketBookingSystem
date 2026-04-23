import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Train implements Serializable {
    private static final long serialVersionUID = 1L;

    private int trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private Map<String, Integer> classSeats;

    public Train(int trainNumber, String trainName, String source, String destination,
                 String departureTime, String arrivalTime,
                 int sleeperSeats, int acSeats, int generalSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.classSeats = new HashMap<>();
        this.classSeats.put("Sleeper", sleeperSeats);
        this.classSeats.put("AC", acSeats);
        this.classSeats.put("General", generalSeats);
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getAvailableSeats(String travelClass) {
        return classSeats.getOrDefault(travelClass, 0);
    }

    public void setAvailableSeats(String travelClass, int seats) {
        classSeats.put(travelClass, seats);
    }

    public boolean bookSeat(String travelClass) {
        int currentSeats = getAvailableSeats(travelClass);
        if (currentSeats > 0) {
            classSeats.put(travelClass, currentSeats - 1);
            return true;
        }
        return false;
    }

    public void restoreSeat(String travelClass) {
        classSeats.put(travelClass, getAvailableSeats(travelClass) + 1);
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
