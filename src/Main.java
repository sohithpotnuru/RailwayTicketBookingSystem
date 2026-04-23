import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingSystem system = new BookingSystem();
            system.show();
        });
    }
}
