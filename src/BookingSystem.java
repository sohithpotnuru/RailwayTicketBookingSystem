import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.UUID;

public class BookingSystem {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private ArrayList<User> users;
    private ArrayList<Train> trains;
    private ArrayList<Ticket> tickets;

    private User loggedInUser;
    private final Admin defaultAdmin = new Admin("admin", "admin123");
    private final PaymentService paymentService = new PaymentService();

    private JTable searchTable;
    private DefaultTableModel searchTableModel;

    private JTable ticketTable;
    private DefaultTableModel ticketTableModel;

    private JTable adminBookingTable;
    private DefaultTableModel adminBookingTableModel;

    private JTextField bookingTrainNoField;

    public BookingSystem() {
        users = DataManager.loadUsers();
        trains = DataManager.loadTrains();
        tickets = DataManager.loadTickets();

        if (trains.isEmpty()) {
            loadDefaultTrains();
        }

        initializeUI();
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initializeUI() {
        frame = new JFrame("Railway Ticket Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1150, 740);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createWelcomePanel(), "welcome");
        mainPanel.add(createRegisterPanel(), "register");
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createAdminLoginPanel(), "adminLogin");
        mainPanel.add(createUserDashboardPanel(), "userDashboard");
        mainPanel.add(createSearchPanel(), "search");
        mainPanel.add(createBookingPanel(), "booking");
        mainPanel.add(createTicketsPanel(), "tickets");
        mainPanel.add(createCancellationPanel(), "cancel");
        mainPanel.add(createAdminDashboardPanel(), "adminDashboard");

        frame.setContentPane(mainPanel);
        cardLayout.show(mainPanel, "welcome");
    }

    private JPanel createBasePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        return panel;
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    private JLabel createHeading(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setForeground(new Color(30, 41, 59));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel createSubHeading(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(100, 116, 139));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color original = bg;
        Color hover = bg.darker();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(original);
            }
        });
        return button;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return field;
    }

    private JPanel wrapInCenter(JComponent component, int width, int height) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        component.setPreferredSize(new Dimension(width, height));
        wrapper.add(component);
        return wrapper;
    }

    private JPanel createFormRow(String labelText, JComponent component) {
        JPanel row = new JPanel(new BorderLayout(10, 5));
        row.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setPreferredSize(new Dimension(145, 35));
        row.add(label, BorderLayout.WEST);
        row.add(component, BorderLayout.CENTER);
        return row;
    }

    private JPanel createWelcomePanel() {
        JPanel panel = createBasePanel();
        JPanel card = createCardPanel();

        card.add(Box.createVerticalStrut(15));
        card.add(createHeading("Railway Ticket Booking System"));
        card.add(Box.createVerticalStrut(10));
        card.add(createSubHeading("A modern Java Swing mini project for railway reservation"));
        card.add(Box.createVerticalStrut(20));

        JLabel iconLabel = new JLabel("🚆", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 74));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(iconLabel);
        card.add(Box.createVerticalStrut(25));

        JButton loginButton = createStyledButton("User Login", new Color(37, 99, 235));
        JButton registerButton = createStyledButton("Register", new Color(16, 185, 129));
        JButton adminButton = createStyledButton("Admin Login", new Color(124, 58, 237));

        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        adminButton.addActionListener(e -> cardLayout.show(mainPanel, "adminLogin"));

        JPanel buttons = new JPanel(new GridLayout(3, 1, 12, 12));
        buttons.setOpaque(false);
        buttons.setMaximumSize(new Dimension(250, 160));
        buttons.add(loginButton);
        buttons.add(registerButton);
        buttons.add(adminButton);

        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.add(buttons);
        card.add(wrap);

        panel.add(wrapInCenter(card, 520, 470), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = createBasePanel();
        JPanel card = createCardPanel();

        JTextField usernameField = createTextField();
        JPasswordField passwordField = createPasswordField();
        JPasswordField confirmField = createPasswordField();

        card.add(createHeading("User Registration"));
        card.add(Box.createVerticalStrut(25));
        card.add(createFormRow("Username", usernameField));
        card.add(Box.createVerticalStrut(12));
        card.add(createFormRow("Password", passwordField));
        card.add(Box.createVerticalStrut(12));
        card.add(createFormRow("Confirm Password", confirmField));
        card.add(Box.createVerticalStrut(20));

        JButton registerButton = createStyledButton("Create Account", new Color(16, 185, 129));
        JButton backButton = createStyledButton("Back", new Color(100, 116, 139));

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String confirm = new String(confirmField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                showError("All fields are required.");
                return;
            }
            if (password.length() < 4) {
                showError("Password must be at least 4 characters.");
                return;
            }
            if (!password.equals(confirm)) {
                showError("Passwords do not match.");
                return;
            }
            if (findUser(username) != null || username.equalsIgnoreCase(defaultAdmin.getUsername())) {
                showError("Username already exists.");
                return;
            }

            users.add(new User(username, password));
            DataManager.saveUsers(users);
            JOptionPane.showMessageDialog(frame, "Registration successful! Please login.");
            usernameField.setText("");
            passwordField.setText("");
            confirmField.setText("");
            cardLayout.show(mainPanel, "login");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        card.add(buttonPanel);

        panel.add(wrapInCenter(card, 570, 360), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = createBasePanel();
        JPanel card = createCardPanel();

        JTextField usernameField = createTextField();
        JPasswordField passwordField = createPasswordField();

        card.add(createHeading("User Login"));
        card.add(Box.createVerticalStrut(25));
        card.add(createFormRow("Username", usernameField));
        card.add(Box.createVerticalStrut(12));
        card.add(createFormRow("Password", passwordField));
        card.add(Box.createVerticalStrut(20));

        JButton loginButton = createStyledButton("Login", new Color(37, 99, 235));
        JButton backButton = createStyledButton("Back", new Color(100, 116, 139));

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                showError("Please enter username and password.");
                return;
            }
            User user = findUser(username);
            if (user != null && user.getPassword().equals(password)) {
                loggedInUser = user;
                usernameField.setText("");
                passwordField.setText("");
                cardLayout.show(mainPanel, "userDashboard");
            } else {
                showError("Invalid username or password.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        card.add(buttonPanel);

        panel.add(wrapInCenter(card, 570, 300), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAdminLoginPanel() {
        JPanel panel = createBasePanel();
        JPanel card = createCardPanel();

        JTextField usernameField = createTextField();
        JPasswordField passwordField = createPasswordField();

        card.add(createHeading("Admin Login"));
        card.add(Box.createVerticalStrut(25));
        card.add(createFormRow("Admin Username", usernameField));
        card.add(Box.createVerticalStrut(12));
        card.add(createFormRow("Admin Password", passwordField));
        card.add(Box.createVerticalStrut(20));

        JButton loginButton = createStyledButton("Login as Admin", new Color(124, 58, 237));
        JButton backButton = createStyledButton("Back", new Color(100, 116, 139));

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (username.equals(defaultAdmin.getUsername()) && password.equals(defaultAdmin.getPassword())) {
                usernameField.setText("");
                passwordField.setText("");
                refreshAdminBookingsTable();
                cardLayout.show(mainPanel, "adminDashboard");
            } else {
                showError("Invalid admin credentials.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        card.add(buttonPanel);

        panel.add(wrapInCenter(card, 570, 300), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createUserDashboardPanel() {
        JPanel panel = createBasePanel();
        panel.add(createHeading("User Dashboard"), BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 2, 18, 18));
        grid.setOpaque(false);

        JButton searchButton = createStyledButton("Search Trains", new Color(37, 99, 235));
        JButton bookingButton = createStyledButton("Book Ticket", new Color(16, 185, 129));
        JButton viewButton = createStyledButton("View My Tickets", new Color(245, 158, 11));
        JButton cancelButton = createStyledButton("Cancel Ticket", new Color(239, 68, 68));
        JButton logoutButton = createStyledButton("Logout", new Color(100, 116, 139));

        searchButton.addActionListener(e -> cardLayout.show(mainPanel, "search"));
        bookingButton.addActionListener(e -> cardLayout.show(mainPanel, "booking"));
        viewButton.addActionListener(e -> {
            refreshTicketTable();
            cardLayout.show(mainPanel, "tickets");
        });
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "cancel"));
        logoutButton.addActionListener(e -> {
            loggedInUser = null;
            cardLayout.show(mainPanel, "welcome");
        });

        grid.add(searchButton);
        grid.add(bookingButton);
        grid.add(viewButton);
        grid.add(cancelButton);

        JPanel centerCard = createCardPanel();
        centerCard.add(createSubHeading("Welcome! Choose any option below"));
        centerCard.add(Box.createVerticalStrut(20));
        centerCard.add(grid);
        centerCard.add(Box.createVerticalStrut(20));
        JPanel wrap = new JPanel();
        wrap.setOpaque(false);
        wrap.add(logoutButton);
        centerCard.add(wrap);

        panel.add(wrapInCenter(centerCard, 720, 390), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = createBasePanel();
        panel.add(createHeading("Train Search"), BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        formPanel.setOpaque(false);
        JTextField sourceField = createTextField();
        JTextField destinationField = createTextField();
        sourceField.setPreferredSize(new Dimension(170, 35));
        destinationField.setPreferredSize(new Dimension(170, 35));

        JButton searchButton = createStyledButton("Search", new Color(37, 99, 235));
        JButton backButton = createStyledButton("Back", new Color(100, 116, 139));

        formPanel.add(new JLabel("Source:"));
        formPanel.add(sourceField);
        formPanel.add(new JLabel("Destination:"));
        formPanel.add(destinationField);
        formPanel.add(searchButton);
        formPanel.add(backButton);
        panel.add(formPanel, BorderLayout.BEFORE_FIRST_LINE);

        String[] columns = {"Train No", "Train Name", "Source", "Destination", "Departure", "Arrival", "Sleeper", "AC", "General"};
        searchTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        searchTable = new JTable(searchTableModel);
        searchTable.setRowHeight(26);
        searchTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(searchTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton useSelectedForBooking = createStyledButton("Use Selected Train For Booking", new Color(16, 185, 129));
        useSelectedForBooking.addActionListener(e -> {
            int row = searchTable.getSelectedRow();
            if (row == -1) {
                showError("Please select a train first.");
                return;
            }
            bookingTrainNoField.setText(searchTableModel.getValueAt(row, 0).toString());
            cardLayout.show(mainPanel, "booking");
        });
        JPanel south = new JPanel();
        south.setOpaque(false);
        south.add(useSelectedForBooking);
        panel.add(south, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> {
            String source = sourceField.getText().trim();
            String destination = destinationField.getText().trim();
            if (source.isEmpty() || destination.isEmpty()) {
                showError("Please enter source and destination.");
                return;
            }
            refreshSearchTable(source, destination);
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "userDashboard"));
        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = createBasePanel();
        panel.add(createHeading("Ticket Booking"), BorderLayout.NORTH);

        JPanel card = createCardPanel();
        bookingTrainNoField = createTextField();
        JTextField nameField = createTextField();
        JTextField ageField = createTextField();
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JComboBox<String> classBox = new JComboBox<>(new String[]{"Sleeper", "AC", "General"});
        JTextField dateField = createTextField();

        card.add(createFormRow("Train Number", bookingTrainNoField));
        card.add(Box.createVerticalStrut(10));
        card.add(createFormRow("Passenger Name", nameField));
        card.add(Box.createVerticalStrut(10));
        card.add(createFormRow("Age", ageField));
        card.add(Box.createVerticalStrut(10));
        card.add(createFormRow("Gender", genderBox));
        card.add(Box.createVerticalStrut(10));
        card.add(createFormRow("Travel Class", classBox));
        card.add(Box.createVerticalStrut(10));
        card.add(createFormRow("Journey Date", dateField));
        card.add(Box.createVerticalStrut(5));

        JLabel hint = new JLabel("Use format: DD-MM-YYYY");
        hint.setForeground(new Color(100, 116, 139));
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(hint);
        card.add(Box.createVerticalStrut(18));

        JButton bookButton = createStyledButton("Confirm Booking", new Color(16, 185, 129));
        JButton backButton = createStyledButton("Back", new Color(100, 116, 139));

        JTextArea receiptArea = new JTextArea(14, 40);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane receiptScroll = new JScrollPane(receiptArea);
        receiptScroll.setBorder(BorderFactory.createTitledBorder("Ticket Receipt"));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setOpaque(false);
        buttons.add(bookButton);
        buttons.add(backButton);
        card.add(buttons);

        JPanel center = new JPanel(new GridLayout(1, 2, 18, 18));
        center.setOpaque(false);
        center.add(card);
        center.add(receiptScroll);
        panel.add(center, BorderLayout.CENTER);

        bookButton.addActionListener(e -> {
            try {
                String trainText = bookingTrainNoField.getText().trim();
                String passengerName = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String gender = (String) genderBox.getSelectedItem();
                String travelClass = (String) classBox.getSelectedItem();
                String journeyDate = dateField.getText().trim();

                if (trainText.isEmpty() || passengerName.isEmpty() || ageText.isEmpty() || journeyDate.isEmpty()) {
                    showError("Please fill all booking fields.");
                    return;
                }

                int trainNo = Integer.parseInt(trainText);
                int age = Integer.parseInt(ageText);
                if (age <= 0 || age > 120) {
                    showError("Please enter a valid age.");
                    return;
                }

                Train train = findTrain(trainNo);
                if (train == null) {
                    showError("Train number not found.");
                    return;
                }

                if (train.getAvailableSeats(travelClass) <= 0) {
                    showError("No seats available in selected class.");
                    return;
                }

                double fare = calculateFare(train, travelClass);
                if (!paymentService.processPayment(fare)) {
                    showError("Payment failed.");
                    return;
                }

                if (!train.bookSeat(travelClass)) {
                    showError("Seat booking failed.");
                    return;
                }

                Passenger passenger = new Passenger(passengerName, age, gender);
                String pnr = generatePnr();
                Ticket ticket = new Ticket(pnr, loggedInUser.getUsername(), train, passenger,
                        travelClass, journeyDate, fare, "SUCCESS");
                tickets.add(ticket);

                DataManager.saveTrains(trains);
                DataManager.saveTickets(tickets);

                receiptArea.setText(ticket.getFormattedReceipt());
                JOptionPane.showMessageDialog(frame, "Booking successful! PNR: " + pnr);

                bookingTrainNoField.setText("");
                nameField.setText("");
                ageField.setText("");
                dateField.setText("");
            } catch (NumberFormatException ex) {
                showError("Train number and age must be valid numbers.");
            } catch (Exception ex) {
                showError("Something went wrong while booking.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "userDashboard"));
        return panel;
    }

    private JPanel createTicketsPanel() {
        JPanel panel = createBasePanel();
        panel.add(createHeading("My Booked Tickets"), BorderLayout.NORTH);

        String[] columns = {"PNR", "Train No", "Train Name", "Passenger", "Class", "Date", "Fare"};
        ticketTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ticketTable = new JTable(ticketTableModel);
        ticketTable.setRowHeight(26);
        panel.add(new JScrollPane(ticketTable), BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back", new Color(100, 116, 139));
        JButton receiptButton = createStyledButton("Show Ticket Receipt", new Color(37, 99, 235));

        receiptButton.addActionListener(e -> {
            int row = ticketTable.getSelectedRow();
            if (row == -1) {
                showError("Please select a ticket first.");
                return;
            }
            String pnr = ticketTableModel.getValueAt(row, 0).toString();
            Ticket ticket = findTicketByPnr(pnr);
            if (ticket != null) {
                JTextArea area = new JTextArea(ticket.getFormattedReceipt());
                area.setEditable(false);
                area.setFont(new Font("Monospaced", Font.PLAIN, 13));
                JOptionPane.showMessageDialog(frame, new JScrollPane(area), "Ticket Receipt", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "userDashboard"));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottom.setOpaque(false);
        bottom.add(receiptButton);
        bottom.add(backButton);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createCancellationPanel() {
        JPanel panel = createBasePanel();
        JPanel card = createCardPanel();

        JTextField pnrField = createTextField();
        card.add(createHeading("Ticket Cancellation"));
        card.add(Box.createVerticalStrut(25));
        card.add(createFormRow("Enter PNR", pnrField));
        card.add(Box.createVerticalStrut(20));

        JButton cancelButton = createStyledButton("Cancel Ticket", new Color(239, 68, 68));
        JButton backButton = createStyledButton("Back", new Color(100, 116, 139));

        cancelButton.addActionListener(e -> {
            String pnr = pnrField.getText().trim();
            if (pnr.isEmpty()) {
                showError("Please enter a PNR number.");
                return;
            }
            Ticket ticket = findTicketByPnr(pnr);
            if (ticket == null || loggedInUser == null || !ticket.getBookedBy().equals(loggedInUser.getUsername())) {
                showError("PNR not found for your account.");
                return;
            }
            ticket.getTrain().restoreSeat(ticket.getTravelClass());
            tickets.remove(ticket);
            DataManager.saveTrains(trains);
            DataManager.saveTickets(tickets);
            JOptionPane.showMessageDialog(frame, "Ticket cancelled successfully.");
            pnrField.setText("");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "userDashboard"));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttons.setOpaque(false);
        buttons.add(cancelButton);
        buttons.add(backButton);
        card.add(buttons);

        panel.add(wrapInCenter(card, 570, 260), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAdminDashboardPanel() {
        JPanel panel = createBasePanel();
        panel.add(createHeading("Admin Dashboard"), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JPanel addTrainPanel = new JPanel();
        addTrainPanel.setBackground(Color.WHITE);
        addTrainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        addTrainPanel.setLayout(new BoxLayout(addTrainPanel, BoxLayout.Y_AXIS));

        JTextField trainNoField = createTextField();
        JTextField trainNameField = createTextField();
        JTextField sourceField = createTextField();
        JTextField destinationField = createTextField();
        JTextField departureField = createTextField();
        JTextField arrivalField = createTextField();
        JTextField sleeperField = createTextField();
        JTextField acField = createTextField();
        JTextField generalField = createTextField();

        addTrainPanel.add(createFormRow("Train Number", trainNoField));
        addTrainPanel.add(Box.createVerticalStrut(8));
        addTrainPanel.add(createFormRow("Train Name", trainNameField));
        addTrainPanel.add(Box.createVerticalStrut(8));
        addTrainPanel.add(createFormRow("Source", sourceField));
        addTrainPanel.add(Box.createVerticalStrut(8));
        addTrainPanel.add(createFormRow("Destination", destinationField));
        addTrainPanel.add(Box.createVerticalStrut(8));
        addTrainPanel.add(createFormRow("Departure Time", departureField));
        addTrainPanel.add(Box.createVerticalStrut(8));
        addTrainPanel.add(createFormRow("Arrival Time", arrivalField));
        addTrainPanel.add(Box.createVerticalStrut(8));
        addTrainPanel.add(createFormRow("Sleeper Seats", sleeperField));
        addTrainPanel.add(Box.createVerticalStrut(8));
        addTrainPanel.add(createFormRow("AC Seats", acField));
        addTrainPanel.add(Box.createVerticalStrut(8));
        addTrainPanel.add(createFormRow("General Seats", generalField));
        addTrainPanel.add(Box.createVerticalStrut(15));

        JButton addTrainButton = createStyledButton("Add Train", new Color(16, 185, 129));
        addTrainButton.addActionListener(e -> {
            try {
                int trainNo = Integer.parseInt(trainNoField.getText().trim());
                if (findTrain(trainNo) != null) {
                    showError("Train number already exists.");
                    return;
                }
                String trainName = trainNameField.getText().trim();
                String source = sourceField.getText().trim();
                String destination = destinationField.getText().trim();
                String departure = departureField.getText().trim();
                String arrival = arrivalField.getText().trim();
                int sleeper = Integer.parseInt(sleeperField.getText().trim());
                int ac = Integer.parseInt(acField.getText().trim());
                int general = Integer.parseInt(generalField.getText().trim());
                if (trainName.isEmpty() || source.isEmpty() || destination.isEmpty() || departure.isEmpty() || arrival.isEmpty()) {
                    showError("Please fill all train fields.");
                    return;
                }
                trains.add(new Train(trainNo, trainName, source, destination, departure, arrival, sleeper, ac, general));
                DataManager.saveTrains(trains);
                JOptionPane.showMessageDialog(frame, "Train added successfully.");
                trainNoField.setText("");
                trainNameField.setText("");
                sourceField.setText("");
                destinationField.setText("");
                departureField.setText("");
                arrivalField.setText("");
                sleeperField.setText("");
                acField.setText("");
                generalField.setText("");
            } catch (NumberFormatException ex) {
                showError("Please enter valid numeric values.");
            }
        });
        JPanel addWrap = new JPanel();
        addWrap.setOpaque(false);
        addWrap.add(addTrainButton);
        addTrainPanel.add(addWrap);

        JPanel managePanel = new JPanel();
        managePanel.setBackground(Color.WHITE);
        managePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        managePanel.setLayout(new BoxLayout(managePanel, BoxLayout.Y_AXIS));

        JTextField manageTrainNoField = createTextField();
        JTextField newDepartureField = createTextField();
        JTextField newArrivalField = createTextField();
        JComboBox<String> classBox = new JComboBox<>(new String[]{"Sleeper", "AC", "General"});
        JTextField seatsField = createTextField();

        managePanel.add(createFormRow("Train Number", manageTrainNoField));
        managePanel.add(Box.createVerticalStrut(8));
        managePanel.add(createFormRow("New Departure", newDepartureField));
        managePanel.add(Box.createVerticalStrut(8));
        managePanel.add(createFormRow("New Arrival", newArrivalField));
        managePanel.add(Box.createVerticalStrut(8));
        managePanel.add(createFormRow("Class", classBox));
        managePanel.add(Box.createVerticalStrut(8));
        managePanel.add(createFormRow("Seat Availability", seatsField));
        managePanel.add(Box.createVerticalStrut(15));

        JButton updateButton = createStyledButton("Update Train", new Color(37, 99, 235));
        JButton deleteButton = createStyledButton("Delete Train", new Color(239, 68, 68));

        updateButton.addActionListener(e -> {
            try {
                int trainNo = Integer.parseInt(manageTrainNoField.getText().trim());
                Train train = findTrain(trainNo);
                if (train == null) {
                    showError("Train not found.");
                    return;
                }
                String dep = newDepartureField.getText().trim();
                String arr = newArrivalField.getText().trim();
                String selectedClass = (String) classBox.getSelectedItem();
                String seatsText = seatsField.getText().trim();
                if (!dep.isEmpty()) train.setDepartureTime(dep);
                if (!arr.isEmpty()) train.setArrivalTime(arr);
                if (!seatsText.isEmpty()) train.setAvailableSeats(selectedClass, Integer.parseInt(seatsText));
                DataManager.saveTrains(trains);
                JOptionPane.showMessageDialog(frame, "Train updated successfully.");
            } catch (NumberFormatException ex) {
                showError("Enter valid train number and seat count.");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int trainNo = Integer.parseInt(manageTrainNoField.getText().trim());
                Train train = findTrain(trainNo);
                if (train == null) {
                    showError("Train not found.");
                    return;
                }
                trains.remove(train);
                DataManager.saveTrains(trains);
                JOptionPane.showMessageDialog(frame, "Train deleted successfully.");
            } catch (NumberFormatException ex) {
                showError("Enter a valid train number.");
            }
        });

        JPanel manageButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        manageButtons.setOpaque(false);
        manageButtons.add(updateButton);
        manageButtons.add(deleteButton);
        managePanel.add(manageButtons);

        JPanel bookingsPanel = new JPanel(new BorderLayout(10, 10));
        bookingsPanel.setBackground(Color.WHITE);
        bookingsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        String[] bookingCols = {"PNR", "User", "Train No", "Train Name", "Passenger", "Class", "Date", "Fare"};
        adminBookingTableModel = new DefaultTableModel(bookingCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        adminBookingTable = new JTable(adminBookingTableModel);
        adminBookingTable.setRowHeight(26);
        bookingsPanel.add(new JScrollPane(adminBookingTable), BorderLayout.CENTER);

        JButton refreshButton = createStyledButton("Refresh Bookings", new Color(16, 185, 129));
        refreshButton.addActionListener(e -> refreshAdminBookingsTable());
        JPanel refreshWrap = new JPanel();
        refreshWrap.setOpaque(false);
        refreshWrap.add(refreshButton);
        bookingsPanel.add(refreshWrap, BorderLayout.SOUTH);

        tabs.addTab("Add Train", new JScrollPane(addTrainPanel));
        tabs.addTab("Update / Delete", managePanel);
        tabs.addTab("View All Bookings", bookingsPanel);
        panel.add(tabs, BorderLayout.CENTER);

        JButton logoutButton = createStyledButton("Admin Logout", new Color(100, 116, 139));
        logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        JPanel south = new JPanel();
        south.setOpaque(false);
        south.add(logoutButton);
        panel.add(south, BorderLayout.SOUTH);
        return panel;
    }

    private void loadDefaultTrains() {
        trains.add(new Train(10101, "Rajdhani Express", "Delhi", "Mumbai", "06:00", "18:00", 120, 60, 150));
        trains.add(new Train(10202, "Shatabdi Express", "Delhi", "Lucknow", "08:30", "14:00", 90, 40, 100));
        trains.add(new Train(10303, "Duronto Express", "Kolkata", "Delhi", "07:00", "21:30", 110, 50, 140));
        trains.add(new Train(10404, "Intercity Express", "Noida", "Kanpur", "09:15", "13:45", 80, 30, 90));
        trains.add(new Train(10505, "Superfast Express", "Mumbai", "Pune", "10:00", "13:20", 70, 25, 80));
        // Additional trains below
        trains.add(new Train(10606, "Vande Bharat Express", "Delhi", "Varanasi", "06:00", "14:00", 100, 50, 120));
        trains.add(new Train(10707, "Garib Rath Express", "Mumbai", "Delhi", "12:00", "08:00", 150, 30, 200));
        trains.add(new Train(10808, "Coromandel Express", "Chennai", "Howrah", "08:45", "16:00", 130, 45, 160));
        trains.add(new Train(10909, "Tejas Express", "Ahmedabad", "Mumbai", "06:40", "13:05", 80, 60, 40));
        trains.add(new Train(11011, "Gatimaan Express", "Delhi", "Jhansi", "08:10", "12:35", 90, 40, 50));
        DataManager.saveTrains(trains);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    private Train findTrain(int trainNumber) {
        for (Train train : trains) {
            if (train.getTrainNumber() == trainNumber) {
                return train;
            }
        }
        return null;
    }

    private Ticket findTicketByPnr(String pnr) {
        for (Ticket ticket : tickets) {
            if (ticket.getPnr().equalsIgnoreCase(pnr)) {
                return ticket;
            }
        }
        return null;
    }

    private void refreshSearchTable(String source, String destination) {
        searchTableModel.setRowCount(0);
        for (Train train : trains) {
            if (train.getSource().equalsIgnoreCase(source) && train.getDestination().equalsIgnoreCase(destination)) {
                searchTableModel.addRow(new Object[]{
                        train.getTrainNumber(), train.getTrainName(), train.getSource(), train.getDestination(),
                        train.getDepartureTime(), train.getArrivalTime(),
                        train.getAvailableSeats("Sleeper"), train.getAvailableSeats("AC"), train.getAvailableSeats("General")
                });
            }
        }
        if (searchTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(frame, "No trains found for this route.");
        }
    }

    private void refreshTicketTable() {
        ticketTableModel.setRowCount(0);
        for (Ticket ticket : tickets) {
            if (loggedInUser != null && ticket.getBookedBy().equals(loggedInUser.getUsername())) {
                ticketTableModel.addRow(new Object[]{
                        ticket.getPnr(), ticket.getTrain().getTrainNumber(), ticket.getTrain().getTrainName(),
                        ticket.getPassenger().getName(), ticket.getTravelClass(), ticket.getJourneyDate(), ticket.getFare()
                });
            }
        }
    }

    private void refreshAdminBookingsTable() {
        adminBookingTableModel.setRowCount(0);
        for (Ticket ticket : tickets) {
            adminBookingTableModel.addRow(new Object[]{
                    ticket.getPnr(), ticket.getBookedBy(), ticket.getTrain().getTrainNumber(),
                    ticket.getTrain().getTrainName(), ticket.getPassenger().getName(),
                    ticket.getTravelClass(), ticket.getJourneyDate(), ticket.getFare()
            });
        }
    }

    private double calculateFare(Train train, String travelClass) {
        double baseFare;
        switch (travelClass) {
            case "AC":
                baseFare = 1200;
                break;
            case "Sleeper":
                baseFare = 700;
                break;
            default:
                baseFare = 350;
                break;
        }
        int nameFactor = train.getTrainName().length() % 5;
        return baseFare + (nameFactor * 50);
    }

    private String generatePnr() {
        return "PNR" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}
