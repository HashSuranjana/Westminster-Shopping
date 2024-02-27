import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Westminster_Frame extends JFrame implements ActionListener {

    ViewCartFrame viewCartFrame;
    private JTable table;
    private JButton add_cart;
    private JComboBox<String> comboBox;
    private int select_row = -1;
    private JTextArea txtarea;
    private ImageIcon resizedic;

    Westminster_Frame() {
        setLayout(null);

        // Initializing the label for selecting product category
        JLabel label1 = new JLabel("Select Product Category : ");
        label1.setFont(label1.getFont().deriveFont(Font.PLAIN, 16));
        label1.setBounds(200, 30, 200, 30);

        // Creating a combo box for product category selection
        comboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        comboBox.setBounds(410, 30, 200, 30);
        comboBox.setFont(label1.getFont().deriveFont(Font.PLAIN, 14));

        // Adding components to the frame
        this.add(label1);
        this.add(comboBox);

        // Adding a shopping cart icon
        JLabel label2 = new JLabel();
        ImageIcon cart = new ImageIcon("cart.png");
        Image original = cart.getImage();
        Image resized = original.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        resizedic = new ImageIcon(resized);
        label2.setIcon(resizedic);
        label2.setBounds(900, 23, 40, 40);
        this.add(label2);

        // Adding an event listener to show the cart frame when the cart icon is clicked
        label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewCartFrame.setVisible(true);
                viewCartFrame.getContentPane().setBackground(new Color(143, 2, 115));
            }
        });

        // Creating the table
        String[] columns = {"Product ID", "Product Name", "Product Category", "Price($)", "Info"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 900, 200);
        this.add(scrollPane);

        // Load data from file into the table
        tableData();

        // Adding label for selected item details
        JLabel label3 = new JLabel("Selected Item Details");
        label3.setFont(label1.getFont().deriveFont(Font.BOLD, 17));
        label3.setBounds(50, 320, 200, 30);
        this.add(label3);

        // Creating text area and the "Add to Cart" button
        txtarea = new JTextArea(); // Creating a text area to preview the selected items
        txtarea.setBounds(50, 360, 900, 100);
        txtarea.setBackground(new Color(0xBEBEEE));
        txtarea.setEditable(false); // Set that the user can't edit the text area

        add_cart = new JButton("Add to Cart"); // button that adds selected items to the cart
        add_cart.setBounds(420, 520, 150, 30);

        // Adding components to the frame
        this.add(txtarea);
        this.add(add_cart);

        // Adding an event listener for combo box and "Add to Cart" button
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        select_row = row; // Update select_row when a row is selected
                        displayRow(row);
                    }
                }
            }
        });
        comboBox.addActionListener(this);
        add_cart.addActionListener(this);

        // Initializing the ViewCartFrame
        viewCartFrame = new ViewCartFrame();
    }

    // Method to display details of the selected row in the text area
    private void displayRow(int row) {
        StringBuilder info = new StringBuilder();
        for (int col = 0; col < table.getColumnCount() - 1; col++) {
            String colName = table.getColumnName(col);
            String value = table.getValueAt(row, col).toString();
            info.append(colName).append(" : ").append(value).append("\n");
        }
        txtarea.setText(info.toString());
        txtarea.setFont(getFont().deriveFont(Font.BOLD, 14));
    }

    // Method to read data from the file and add it to the table
    private void tableData() {
        try (BufferedReader file_reader = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = file_reader.readLine()) != null) {
                if (line.startsWith("ProductCategory:")) {
                    String[] data = line.replace("ProductCategory:", "").split(", ");
                    String category = data[0];
                    String name = data[1];
                    String id = data[2];
                    String info = "";
                    for (int i = 3; i < data.length - 2; i++) {
                        info += data[i] + " , ";
                    }
                    double price = Double.parseDouble(data[data.length - 1]);

                    if (comboBox.getSelectedItem().equals("All") || category.equals(comboBox.getSelectedItem())) {
                        Object[] rowData = {id, name, category, price, info.trim()};
                        ((DefaultTableModel) table.getModel()).addRow(rowData);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Method to filter the table based on the selected category
    private void SortTable(String selectedCategory) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows in the table
        tableData(); // Reload the data into the table based on the selected category
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox) {
            SortTable((String) comboBox.getSelectedItem());
        }

        if (e.getSource() == add_cart) {
            if (select_row != -1) {
                addSelectedItemToCart();
            } else {
                JOptionPane.showMessageDialog(Westminster_Frame.this, "Please Select a Row to Add !");
            }
        }
    }

    // Method to add the selected item to the cart
    private void addSelectedItemToCart() {
        DefaultTableModel cartTableModel = (DefaultTableModel) viewCartFrame.cartTable.getModel();

        Object[] rowData = new Object[table.getColumnCount()];
        for (int col = 0; col < table.getColumnCount(); col++) {
            rowData[col] = table.getValueAt(select_row, col);
        }

        cartTableModel.addRow(rowData);
        viewCartFrame.updateDetailedCart(); // Update the detailed cart table
        JOptionPane.showMessageDialog(this, "Item added to cart!");
    }
}