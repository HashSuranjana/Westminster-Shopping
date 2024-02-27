import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

// Define a test class named WestminsterUnitTesting to test the WestminsterShoppingManager
public class WestminsterUnitTesting {


    @Test
    void testAddItem() { // Testing the method of adding items to the shopping manager
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager(50); // Creating a new object of WestminsterShoppingManager with a capacity of 50
        Clothing clothing = new Clothing("C001", "Shirt", 10, 20.0, "M", "Blue"); // Creating a new Clothing object

        shoppingManager.Add_item(clothing); // Add the clothing item to the product list

        assertEquals(1, shoppingManager.productsList.size()); // Assert that the size of the product list is 1 after adding an item

        assertEquals(clothing, shoppingManager.productsList.get(0)); // Assert that the first item in the product list is the added clothing object
    }

    // Testing the method of removing items from the shopping manager
    @Test
    void testRemoveItem() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager(50);

        shoppingManager.Remove_item(); // Remove an item from the product list

        assertEquals(0, shoppingManager.productsList.size()); // Assert that the size of the product list is 0 after removing an item
    }

    // Testing the method of printing items
    @Test
    void testPrintItems() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager(50);
        Clothing clothing = new Clothing("C001", "Shirt", 10, 20.0, "M", "Blue");
        Electronics electronics = new Electronics("E001", "Laptop", 5, 1000.0, "Dell", 2);

        shoppingManager.Add_item(clothing); // Add a clothing item to the product list
        shoppingManager.Add_item(electronics); // Add an electronics item to the product list

        // Redirect System.out to capture printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));


        shoppingManager.Print_items(); // Call the method that prints items


        System.setOut(System.out); // Reset System.out to its original value


        String printedOutput = outputStream.toString(); // Convert the captured output to a string for assertions

        // Define the expected output based on the items you added
        String expectedOutput = "Product Category : Clothing" + "\n"+ "Product Name : Shirt"+ "\n"+ "Product ID : C001"+ "\n"+
        "Size : M"+ "\n"+ "Color : Blue"+ "\n"+ "Available Quantity : 10"+ "\n"+ "Price : 20.0$\n"+"\nProduct Category : Electronics" + "\n"+ "Product Name : Laptop"+ "\n"+ "Product ID : E001"+ "\n"+
                "Brand : Dell"+ "\n"+ "Warranty : 2 Year(s)"+ "\n"+ "Available Quantity : 5"+ "\n"+ "Price : 1000.0$\n\n";

        // Assert that the printed output matches the expected output
        assertEquals(expectedOutput, printedOutput);
    }

    // Testing the method of saving items to the file
    @Test
    void testSaveItems() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager(50);
        Clothing clothing = new Clothing("C001", "Shirt", 10, 20.0, "M", "Blue");

        shoppingManager.Add_item(clothing); // Add a clothing item to the product list
        shoppingManager.save_items(); // Save the items to a file

        File file = new File("data.txt");
        try {
            Scanner file_Reader = new Scanner(file);
            String content ="";
            while(file_Reader.hasNextLine()){
                content += file_Reader.nextLine(); //getting actual output
            }
            String expected = "ProductCategory:Clothing, Shirt, C001, M, Blue, 10, 20.0"; //expected output of the result
            assertEquals(expected,content); //Compare the difference between the lines

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");;
        }

    }

    // Test method for loading items into the shopping manager
    @Test
    void testLoadItem() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager(50);

        shoppingManager.load_item(); // Load items into the shopping manager

        // Perform assertions based on the expected state after loading items
    }

}
