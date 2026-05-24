/**
 * 
 */
package shoppingCart;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class ShoppingCartSystem extends JFrame {

    // ===================== ARRAYLIST =====================
    private ArrayList<Item> cart = new ArrayList<>();

    // ===================== GUI COMPONENTS =====================
    private JTextArea displayArea;

    // ===================== CONSTRUCTOR =====================
    public ShoppingCartSystem() {

        setTitle("Shopping Cart System");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(18, 25, 40));

        // ===================== HEADER =====================
        JLabel header = new JLabel(
                "SHOPPING CART MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        header.setFont(new Font("Arial", Font.BOLD, 30));
        header.setForeground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 10, 20, 10));

        mainPanel.add(header, BorderLayout.NORTH);

        // ===================== MENU PANEL =====================
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(10, 1, 10, 10));
        menuPanel.setBackground(new Color(30, 40, 60));
        menuPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton addBtn = createButton("1 TO ADD NEW ITEM TO CART");
        JButton viewBtn = createButton("2 TO VIEW YOUR SHOPPING CART");
        JButton updateBtn = createButton("3 TO UPDATE AN ITEM");
        JButton removeBtn = createButton("4 TO REMOVE AN ITEM");
        JButton saveBtn = createButton("5 TO SAVE ITEMS TO TEXT FILE");
        JButton emptyBtn = createButton("6 EMPTY SHOPPING CART");
        JButton checkoutBtn = createButton("7 TO CHECKOUT YOUR SHOPPING CART");
        JButton typeBtn = createButton("8 TO VIEW ITEMS BY TYPE");
        JButton searchBtn = createButton("9 TO SEARCH AN ITEM BY ID");
        JButton exitBtn = createButton("10 TO EXIT THE PROGRAM");

        menuPanel.add(addBtn);
        menuPanel.add(viewBtn);
        menuPanel.add(updateBtn);
        menuPanel.add(removeBtn);
        menuPanel.add(saveBtn);
        menuPanel.add(emptyBtn);
        menuPanel.add(checkoutBtn);
        menuPanel.add(typeBtn);
        menuPanel.add(searchBtn);
        menuPanel.add(exitBtn);

        mainPanel.add(menuPanel, BorderLayout.WEST);

        // ===================== DISPLAY AREA =====================
        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(245, 245, 245));
        displayArea.setForeground(Color.BLACK);
        displayArea.setMargin(new Insets(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(new LineBorder(Color.GRAY, 2));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // ===================== BUTTON ACTIONS =====================
        addBtn.addActionListener(e -> addItem());
        viewBtn.addActionListener(e -> viewCart());
        updateBtn.addActionListener(e -> updateItem());
        removeBtn.addActionListener(e -> removeItem());
        saveBtn.addActionListener(e -> saveItemsToFile());
        emptyBtn.addActionListener(e -> emptyCart());
        checkoutBtn.addActionListener(e -> checkout());
        typeBtn.addActionListener(e -> viewItemsByType());
        searchBtn.addActionListener(e -> searchItemByID());
        exitBtn.addActionListener(e -> exitProgram());

        // Welcome Message
        displayArea.setText("""
                ==========================================
                    WELCOME TO SHOPPING CART SYSTEM
                ==========================================
                
                SELECT AN OPERATION FROM THE MENU
                """);
    }

    // ===================== CREATE BUTTON =====================
    private JButton createButton(String text) {

        JButton btn = new JButton(text);

        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(0, 153, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    // ===================== ADD ITEM =====================
    private void addItem() {

        try {

            String[] itemTypes = {"Book", "Electronic", "Grocery"};

            String type = (String) JOptionPane.showInputDialog(
                    this,
                    "Select Item Type",
                    "Item Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    itemTypes,
                    itemTypes[0]
            );

            if (type == null) return;

            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Enter Item ID")
            );

            String name = JOptionPane.showInputDialog("Enter Item Name");

            double price = Double.parseDouble(
                    JOptionPane.showInputDialog("Enter Item Price")
            );

            int quantity = Integer.parseInt(
                    JOptionPane.showInputDialog("Enter Quantity")
            );

            if (price < 0 || quantity < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Price and Quantity cannot be negative!",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Item item;

            switch (type) {

                case "Book":
                    item = new BookItem(id, name, price, quantity);
                    break;

                case "Electronic":
                    item = new ElectronicItem(id, name, price, quantity);
                    break;

                default:
                    item = new GroceryItem(id, name, price, quantity);
                    break;
            }

            cart.add(item);

            displayArea.setText("""
                    ==========================================
                          ITEM ADDED SUCCESSFULLY
                    ==========================================
                    """ + "\n" + item);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Number Input!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== VIEW CART =====================
    private void viewCart() {

        if (cart.isEmpty()) {

            displayArea.setText("""
                    ==========================================
                           SHOPPING CART IS EMPTY
                    ==========================================
                    """);

            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("""
                ==========================================
                        SHOPPING CART ITEMS
                ==========================================
                
                """);

        for (Item item : cart) {
            sb.append(item).append("\n");
        }

        displayArea.setText(sb.toString());
    }

    // ===================== UPDATE ITEM =====================
    private void updateItem() {

        try {

            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Enter Item ID")
            );

            boolean found = false;

            for (Item item : cart) {

                if (item.getId() == id) {

                    int quantity = Integer.parseInt(
                            JOptionPane.showInputDialog("Enter New Quantity")
                    );

                    if (quantity < 0) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Quantity cannot be negative!",
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }

                    item.setQuantity(quantity);

                    displayArea.setText("""
                            ==========================================
                                  ITEM UPDATED SUCCESSFULLY
                            ==========================================
                            """ + "\n" + item);

                    found = true;
                    break;
                }
            }

            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "Item ID Not Found!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Number Input!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== REMOVE ITEM =====================
    private void removeItem() {

        try {

            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Enter Item ID")
            );

            Item removeItem = null;

            for (Item item : cart) {

                if (item.getId() == id) {
                    removeItem = item;
                    break;
                }
            }

            if (removeItem != null) {

                cart.remove(removeItem);

                displayArea.setText("""
                        ==========================================
                              ITEM REMOVED SUCCESSFULLY
                        ==========================================
                        """);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Item Not Found!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Number Input!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== SAVE ITEMS =====================
    private void saveItemsToFile() {

        try {

            FileWriter writer = new FileWriter("shopping_cart.txt");

            for (Item item : cart) {
                writer.write(item.toString() + "\n");
            }

            writer.close();

            displayArea.setText("""
                    ==========================================
                         ITEMS SAVED TO TEXT FILE
                    ==========================================
                    File Name : shopping_cart.txt
                    """);

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error Saving File!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== EMPTY CART =====================
    private void emptyCart() {

        if (cart.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cart Already Empty!",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to empty the cart?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

            cart.clear();

            displayArea.setText("""
                    ==========================================
                        SHOPPING CART EMPTIED
                    ==========================================
                    """);
        }
    }

    // ===================== CHECKOUT =====================
    private void checkout() {

        if (cart.isEmpty()) {

            displayArea.setText("""
                    ==========================================
                          SHOPPING CART IS EMPTY
                    ==========================================
                    """);

            return;
        }

        double total = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("""
                ==========================================
                           CHECKOUT RECEIPT
                ==========================================
                
                """);

        for (Item item : cart) {

            sb.append(item).append("\n");

            total += item.getPrice() * item.getQuantity();
        }

        sb.append("\nTOTAL AMOUNT : $").append(total);

        displayArea.setText(sb.toString());
    }

    // ===================== VIEW ITEMS BY TYPE =====================
    private void viewItemsByType() {

        String[] types = {"Book", "Electronic", "Grocery"};

        String type = (String) JOptionPane.showInputDialog(
                this,
                "Select Item Type",
                "View By Type",
                JOptionPane.QUESTION_MESSAGE,
                null,
                types,
                types[0]
        );

        if (type == null) return;

        StringBuilder sb = new StringBuilder();

        sb.append("""
                ==========================================
                        ITEMS BY TYPE
                ==========================================
                
                """);

        boolean found = false;

        for (Item item : cart) {

            if (item.getType().equalsIgnoreCase(type)) {

                sb.append(item).append("\n");
                found = true;
            }
        }

        if (!found) {
            sb.append("No ").append(type).append(" Items Found!");
        }

        displayArea.setText(sb.toString());
    }

    // ===================== SEARCH ITEM =====================
    private void searchItemByID() {

        try {

            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("Enter Item ID")
            );

            boolean found = false;

            for (Item item : cart) {

                if (item.getId() == id) {

                    displayArea.setText("""
                            ==========================================
                                     ITEM FOUND
                            ==========================================
                            """ + "\n" + item);

                    found = true;
                    break;
                }
            }

            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "Item Not Found!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Number Input!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ===================== EXIT PROGRAM =====================
    private void exitProgram() {

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Do you want to exit the program?",
                "Exit Program",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ShoppingCartSystem().setVisible(true);
        });
    }
}
