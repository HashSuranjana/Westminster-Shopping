import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class ViewCartFrame extends JFrame implements ActionListener {

    JTable cartTable;
    private JTable detailedCartTable;
    private JLabel totalPriceLabel, discountLabel, finalTotalLabel;
    private JButton checkout;

    public ViewCartFrame() {

        setTitle(" Westminster Shopping Cart ");
        setSize(1000, 600);
        setLayout(null);

        // Creating the main cart table
        cartTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Name", "Category", "Price($)", "Info"}, 0));
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        this.add(cartScrollPane);

        // Creating the detailed cart table
        detailedCartTable = new JTable(new DefaultTableModel(new Object[]{"Product", "Quantity", "Price"}, 0));
        JScrollPane detailedCartScrollPane = new JScrollPane(detailedCartTable);
        detailedCartScrollPane.setBounds(5, 60, 900, 250);
        this.add(detailedCartScrollPane);

        // Adding a button to trigger the discount calculation
        checkout = new JButton("Checkout");
        checkout.setBounds(750, 20, 200, 30);
        checkout.addActionListener(this);

        this.add(checkout);


        totalPriceLabel = new JLabel();
        totalPriceLabel.setFont(totalPriceLabel.getFont().deriveFont(Font.PLAIN, 16));
        totalPriceLabel.setBounds(100,400,200,40);

        discountLabel = new JLabel();
        discountLabel.setFont(discountLabel.getFont().deriveFont(Font.PLAIN, 16));
        discountLabel.setBounds(100,450,200,40);

        finalTotalLabel = new JLabel();
        finalTotalLabel.setFont(finalTotalLabel.getFont().deriveFont(Font.PLAIN, 16));
        finalTotalLabel.setBounds(100,500,200,40);

        //adding labels
        this.add(totalPriceLabel);
        this.add(discountLabel);
        this.add(finalTotalLabel);


        setVisible(false); //Initially set the frame to not visible
    }

    @Override
    public void actionPerformed(ActionEvent e) { //triggered when checkout button clicked
        if (e.getSource() == checkout) {
            calculateDiscount();
        }
    }

    // Method to calculate discount and update labels
    private void calculateDiscount() {
        DefaultTableModel cartTableModel = (DefaultTableModel) cartTable.getModel();
        int rowCount = cartTableModel.getRowCount();


        Map<String, Integer> categoryCount = new HashMap<>(); // Map to store the count of each category

        // Iterate through the cartTable and count the occurrences of each category
        for (int i = 0; i < rowCount; i++) {
            String category = (String) cartTableModel.getValueAt(i, 2); // Assuming category is at column index 2
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        // Calculate discount for categories with 3 or more occurrences
        double totalDiscount = 0;

        for (int count : categoryCount.values()) {
            if (count >= 3) {
                totalDiscount += 0.2 * getPriceForCategory(cartTable, categoryCount); //Calculate the total discount
            }
        }

        // Update discountLabel
        if (totalDiscount != 0){
            discountLabel.setText("Discount of 20% : $ " + String.format("%.2f", totalDiscount));
        } else{
            discountLabel.setText("Discount : $ " + String.format("%.2f", totalDiscount));
        }

        // Update finalTotalLabel
        double totalPrice = getTotalPrice(cartTable);
        double finalTotal = totalPrice - totalDiscount;
        finalTotalLabel.setText("Final Total : $ " + String.format("%.2f", finalTotal)); //Label for the final total
    }

    // Method to get the total price from the cartTable
    private double getTotalPrice(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        double totalPrice = 0;

        for (int i = 0; i < rowCount; i++) {
            double price = (double) model.getValueAt(i, 3); // Assuming price is at column index 3
            totalPrice += price;
        }

        return totalPrice;
    }

    // Method to get the price for a specific category from the cartTable
    private double getPriceForCategory(JTable table, Map<String, Integer> categoryCount) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        double priceForCategory = 0;

        for (int i = 0; i < rowCount; i++) {
            String category = (String) model.getValueAt(i, 2); // Assuming category is at column index 2
            int count = categoryCount.getOrDefault(category, 0);

            if (count >= 3) {
                double price = (double) model.getValueAt(i, 3); // Assuming price is at column index 3
                priceForCategory += price;
            }
        }

        return priceForCategory;
    }
    void updateDetailedCart() {
        DefaultTableModel detailedCartTableModel = (DefaultTableModel) detailedCartTable.getModel();
        detailedCartTableModel.setRowCount(0);

        Map<String, Integer> productQuantityMap = new HashMap<>(); //Map to store the product quantity
        Map<String, Double> rowTotalMap = new HashMap<>(); //Map to store row total amount

        // Iterate over the main cart table
        for (int row = 0; row < cartTable.getRowCount(); row++) {
            String productInfo = cartTable.getValueAt(row, 4).toString();
            String key = cartTable.getValueAt(row, 1) + " - " + cartTable.getValueAt(row, 2) + " , " + productInfo;

            int quantity = productQuantityMap.getOrDefault(key, 0) + 1;
            double totalPrice = rowTotalMap.getOrDefault(key, 0.0) + Double.parseDouble(cartTable.getValueAt(row, 3).toString());
            productQuantityMap.put(key, quantity);
            rowTotalMap.put(key, totalPrice);
        }


        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {// Update the detailed cart table
            String[] parts = entry.getKey().split(" - ");
            String product = parts[0];

            int quantity = entry.getValue();
            double totalPrice = rowTotalMap.get(entry.getKey());

            Object[] rowData = {product, quantity, totalPrice};
            detailedCartTableModel.addRow(rowData);
        }

        double totalPricesInDetailedCart = getTotalPricesInDetailedCart(detailedCartTableModel); // Calculate and update the total of prices in the detailed cart table
        totalPriceLabel.setText("Total Price : $ " + String.format("%.2f", totalPricesInDetailedCart)); //Show in the label
    }

    // Method to get the total of prices in the detailed cart table
    private double getTotalPricesInDetailedCart(DefaultTableModel detailedCartTableModel) {
        int rowCount = detailedCartTableModel.getRowCount();
        double totalPrices = 0;

        for (int i = 0; i < rowCount; i++) {
            double price = (double) detailedCartTableModel.getValueAt(i, 2); // Assuming price is at column index 2
            totalPrices += price;
        }

        return totalPrices;
    }


}
