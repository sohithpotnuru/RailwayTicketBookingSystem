import java.io.*;
import java.util.ArrayList;

public class DataManager {
    private static final String USERS_FILE = "users.dat";
    private static final String TRAINS_FILE = "trains.dat";
    private static final String TICKETS_FILE = "tickets.dat";

    public static void saveUsers(ArrayList<User> users) {
        saveObject(USERS_FILE, users);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<User> loadUsers() {
        Object obj = loadObject(USERS_FILE);
        if (obj == null) return new ArrayList<>();
        return (ArrayList<User>) obj;
    }

    public static void saveTrains(ArrayList<Train> trains) {
        saveObject(TRAINS_FILE, trains);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Train> loadTrains() {
        Object obj = loadObject(TRAINS_FILE);
        if (obj == null) return new ArrayList<>();
        return (ArrayList<Train>) obj;
    }

    public static void saveTickets(ArrayList<Ticket> tickets) {
        saveObject(TICKETS_FILE, tickets);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Ticket> loadTickets() {
        Object obj = loadObject(TICKETS_FILE);
        if (obj == null) return new ArrayList<>();
        return (ArrayList<Ticket>) obj;
    }

    private static void saveObject(String fileName, Object data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object loadObject(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}
