import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * MainApp
 */
public class MainApp {
    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }
}

class Application extends JFrame {

    public static EmptyBorder getPadding(int paddingvalue) {
        return new EmptyBorder(paddingvalue, paddingvalue, paddingvalue, paddingvalue);
    }

    private DefaultTableModel tableModel;
    private JTable contactTable;
    FlowLayout layout;
    JPanel panel;
    Font defaultTitleFont = new Font("SF Pro", Font.BOLD, 25);
    Font defaultButtonFont = new Font("SF Pro", Font.BOLD, 18);
    private ArrayList<Contatto> contacts = new ArrayList<Contatto>();

    Application() {
        setTitle("Contact Manager v2.0");
        setMinimumSize(new DimensionUIResource(720, 480));
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        this.add(panel);
        // action when closing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addLabelTitle();
        initializeTable();
        createMenuBar();
        addActionButtons();
    }

    private void addLabelTitle() {
        JLabel title = new JLabel("Contact Manager", SwingConstants.LEFT);
        title.setFont(defaultTitleFont);
        title.setBorder(getPadding(10));
        title.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(title);
    }

    /**
     * Creates and configures the menu bar for the application, including file operations and contact management.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem importMenuItem = new JMenuItem("Import CSV");
        JMenuItem exportMenuItem = new JMenuItem("Export CSV");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        // Set keyboard shortcuts for menu items
        importMenuItem
                .setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        exportMenuItem
                .setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));


        // ActionListener for Import CSV menu item
        importMenuItem.addActionListener(e->{
                // Handle import logic here
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(Application.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] data = line.split(",");
                            if (data.length == 4) {
                                String name = data[0];
                                String surname = data[1];
                                String phoneNumber = data[2];
                                String dateString = data[3];
                                try {
                                    Contatto contact = new Contatto(name, surname, phoneNumber,dateString);
                                    
                                    boolean valid = true;
                                    for (Contatto cont:contacts) {
                                        if(cont.equals(contact))
                                            valid=false;
                                        
                                    }
                                    if(! valid){
                                        JOptionPane.showMessageDialog(Application.this, "Contact is already in list, edit or delete it", "Contact already present", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                    if(valid){
                                    contacts.add(contact);
                                    tableModel.addRow(new Object[] { contact.getName(), contact.getSurname(),
                                            contact.getPhoneNumber(),contact.getBirthDate() });}
                                } catch (Exception ex) {
                                    // Handle invalid data if necessary
                                    System.out.println("Error importing contact: " + ex.getMessage());
                                }
                            } else {
                                // Handle invalid CSV format if necessary
                                System.out.println("Invalid CSV format");
                            }
                        }
                        JOptionPane.showMessageDialog(Application.this, "Contacts imported successfully.");
                    } catch (IOException ex) {
                        // Handle file read error if necessary
                        JOptionPane.showMessageDialog(Application.this, "Error reading file: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        });

        // ActionListener for Export CSV menu item
        exportMenuItem.addActionListener(e->{
                // Handle export to CSV logic here
                try {
                    FileWriter writer = new FileWriter("contacts.csv");
                    for (Contatto contact : contacts) {
                        writer.write(contact.getName() + "," + contact.getSurname() + ","
                                + contact.getPhoneNumber() + ","+ contact.getBirthDate() + "\n");
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(Application.this, "Contacts exported to contacts.csv.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(Application.this, "Error exporting contacts: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

        // ActionListener for Exit menu item
        exitMenuItem.addActionListener(e -> System.exit(0));

        // Add items to File menu
        fileMenu.add(importMenuItem);
        fileMenu.add(exportMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem addMenuItem = new JMenuItem("Add");
        JMenuItem deleteMenuItem = new JMenuItem("Delete");

        // Set keyboard shortcuts for menu items
        addMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        // ActionListener for Add menu item
        addMenuItem.addActionListener(e -> {
            // Create custom input fields for name, surname, and phone number
            JTextField nameField = new JTextField();
            JTextField surnameField = new JTextField();
            JTextField phoneNumberField = new JTextField();
            JTextField dateField = new JTextField();

            // Create the input panel with fields
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(4, 2));
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Surname:"));
            inputPanel.add(surnameField);
            inputPanel.add(new JLabel("Phone Number:"));
            inputPanel.add(phoneNumberField);
            inputPanel.add(new JLabel("Date (yyyy-mm-dd):"));
            inputPanel.add(dateField);

            // Show the input dialog and wait for user input
            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter Contact Details",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // Process user input
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String dateString = dateField.getText();
                

                try {
                    // Validate input and create a new Contact object
                    Contatto contact = new Contatto(name, surname, phoneNumber,dateString);

                    // Add the contact to the ArrayList
                    boolean valid = true;
                    for (Contatto cont:contacts) {
                        if(cont.equals(contact))
                            valid=false;
                        
                    }
                    if(! valid){
                        JOptionPane.showMessageDialog(Application.this, "Contact is already in list, edit or delete it", "Contact already present", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if(valid){
                    contacts.add(contact);

                    // Update the table model and refresh the table
                    tableModel.addRow(
                            new Object[] { contact.getName(), contact.getSurname(), contact.getPhoneNumber() , contact.getBirthDate()});
                            }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    // Handle validation exceptions
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid contact details.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener for Delete menu item
        deleteMenuItem.addActionListener(e->{
                // Handle delete logic here
                try {
                    int selectedRow = contactTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        contacts.remove(selectedRow);
                        tableModel.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(Application.this,
                                "No row selected. Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (HeadlessException e1) {
                }
        });

        // Add items to Edit menu
        editMenu.add(addMenuItem);
        editMenu.add(deleteMenuItem);

        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // Set the menu bar for the application window
        setJMenuBar(menuBar);
    }

    /**
     * 
     */
    private void addActionButtons() {
        JPanel buttonPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 30, 0); // 20 pixels horizontal gap, 10 pixels
                                                                          // vertical gap
        buttonPanel.setLayout(flowLayout);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(buttonPanel);

        // add, edit, delete, and search buttons
        JButton addButton = new JButton("Add");
        addButton.setFont(defaultButtonFont);
        addButton.addActionListener(e -> {
            // Create custom input fields for name, surname, and phone number
            JTextField nameField = new JTextField();
            JTextField surnameField = new JTextField();
            JTextField phoneNumberField = new JTextField();
            JTextField dateField = new JTextField();

            // Create the input panel with fields
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(4, 2));
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Surname:"));
            inputPanel.add(surnameField);
            inputPanel.add(new JLabel("Phone Number:"));
            inputPanel.add(phoneNumberField);
            inputPanel.add(new JLabel("Date (yyyy-mm-dd):"));
            inputPanel.add(dateField);

            // Show the input dialog and wait for user input
            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter Contact Details",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // Process user input
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String phoneNumber = phoneNumberField.getText();
                String dateString = dateField.getText();
                try {
                    // Validate input and create a new Contact object
                    Contatto contact = new Contatto(name, surname, phoneNumber,dateString);
                    // Add the contact to the ArrayList
                    boolean valid = true;
                    for (Contatto cont:contacts) {
                        if(cont.equals(contact))
                            valid=false;
                        
                    }
                    if(! valid){
                        JOptionPane.showMessageDialog(Application.this, "Contact is already in list, edit or delete it", "Contact already present", JOptionPane.INFORMATION_MESSAGE);
                    }
                    if(valid){
                    contacts.add(contact);
                    // Update the table model and refresh the table
                    tableModel.addRow(
                            new Object[] { contact.getName(), contact.getSurname(), contact.getPhoneNumber(),contact.getBirthDate() });
                            }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    // Handle validation exceptions
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid contact details.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(addButton);
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(defaultButtonFont);
        deleteButton.addActionListener(e -> {
            try {
                int selectedRow = contactTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Get the index of the selected row in the ArrayList
                    int modelRowIndex = contactTable.convertRowIndexToModel(selectedRow);
                    // Remove the selected contact from the ArrayList and the table model
                    contacts.remove(modelRowIndex);
                    tableModel.removeRow(selectedRow);
                } else {
                    // If no row is selected, show an error dialog
                    JOptionPane.showMessageDialog(this, "No row selected. Please select a row to delete.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (HeadlessException e1) {
            }
        });
        buttonPanel.add(deleteButton);
        JTextField searchField = new JTextField("Search...");
        searchField.setFont(defaultButtonFont);
        searchField.setForeground(Color.GRAY); // Set the text color to gray for placeholder
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text and change text color to black when the field
                // gains focus
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Set the placeholder text and change text color to gray when the field loses
                // focus and no input is given
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Enter name or surname");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        // Add a DocumentListener to update the table as the user types in the search
        // field
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }

            // Update the table based on the search criteria
            private void updateTable() {
                String searchQuery = searchField.getText().toLowerCase();
                // Clear the table
                tableModel.setRowCount(0);

                // Filter and add contacts that match the search query
                for (Contatto contact : contacts) {
                    String name = contact.getName().toLowerCase();
                    String surname = contact.getSurname().toLowerCase();

                    if (name.contains(searchQuery) || surname.contains(searchQuery)) {
                        tableModel.addRow(new Object[] { contact.getName(), contact.getSurname(),
                                contact.getPhoneNumber(),contact.getBirthDate() });
                    }
                }
            }
        });

        // Add the search field to the panel
        buttonPanel.add(searchField);

    }

    private void initializeTable() {
        String[] columnNames = { "Name", "Surname", "Phone Number", "Date of birth" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Allow all cells to be editable
            }
        };

        contactTable = new JTable(tableModel);
        contactTable.setFillsViewportHeight(true);

        // Add a TableModelListener to handle changes in the table model
        tableModel.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            if (row >= 0 && column >= 0) {
                Object updatedValue = tableModel.getValueAt(row, column);

                try {
                    // Assuming column indices: 0 for Name, 1 for Surname, 2 for Phone Number
                    if (column == 0) {
                        contacts.get(row).setName(updatedValue.toString());
                    } else if (column == 1) {
                        contacts.get(row).setSurname(updatedValue.toString());
                    } else if (column == 2) {
                        contacts.get(row).setPhoneNumber(updatedValue.toString());
                    } else if (column == 3) {
                        contacts.get(row).setDataNascita(updatedValue.toString());
                    }
                    
                } catch (Exception ex) {
                    // Handle validation exceptions
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);

                    // Optionally, you can revert the table cell value to its original state here if
                    // needed:
                    // tableModel.setValueAt(originalValue, row, column);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(contactTable);
        panel.add(scrollPane);
    }

    public void start() {
        setVisible(true);
    }
}