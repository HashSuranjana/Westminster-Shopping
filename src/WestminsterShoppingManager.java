import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{

    ArrayList<Product> productsList ; //Adding Arraylist to store the products.
    private int products ; // Declaring the variable to initialize the number of products.
    private String product_id; //declaring the product_id
    static Scanner input = new Scanner(System.in); // new Scanner object for Scanner class



    //Constructor for Westminstershoppingmanager
    public WestminsterShoppingManager(int products){
        this.products = products ;
        productsList  = new ArrayList<Product>();
    }

    //
    @Override
    public void Add_item(Product product) {
        if(productsList.size() < products){ //check whether productList size is less than 50
            productsList.add(product);  //Add the item to productList
            System.out.println("\n< The details of product ID :"+ product.getProduct_ID() + " was added Successfully! >");
        }else{
            System.out.println("< No more Space to add an Item. >"); //if productList.length >50 this will print
        }
    }

    @Override
    public void Remove_item() {
        int count=0;

        for (Product product:productsList) {
            count++;

            if (product.getProduct_ID().equals(product_id)) {
                productsList.remove(product); // remove product item from the productList
                System.out.println("\nProduct Has Successfully Removed !\n"); //Gives the message for success deletion
                System.out.println("Remaining Items : "+productsList.size()+"\n"); //Print the number of items remaining
                break;

            } if(productsList.size() ==count && !product.getProduct_ID().equals(product_id)){
                System.out.println("< No product Id found. Please Check again ! > ");
                break;
            }
        }
    }

    @Override
    public void Print_items() {

        productsList.sort(new Comparator<Product>() {

            @Override
            public int compare(Product product1, Product product2) {
                return product1.getProduct_ID().compareTo(product2.getProduct_ID()); //compare the Id of two products
            }
        } );

        //Iterate the productList
        for (Product product : productsList) {

            //Check if it is a Cloth or an Electronic
            if (product.getCategory().equals("Clothing")) {
                System.out.println(product); //This calls the toString method of Clothing class
            }
            if (product.getCategory().equals("Electronics")) {
                System.out.println(product); //This calls toString method of Electronics Class
            }
        }
        if(productsList.isEmpty()){
            System.out.println("\n< No items to show! >"); //if no items has added this will print
        }
    }

    @Override
    public void save_items() {

        //creating a file_write object of FileWriter class and create a text file called data.txt
        try(FileWriter file_write = new FileWriter("data.txt")) {

            for(Product product:productsList){
                if(product.getCategory().equals("Clothing")){ //check the category of the product
                    file_write.write(product.tofileString()); //calls the method to print into the file

                }else if (product.getCategory().equals("Electronics")){//check the category of the product
                    file_write.write(product.tofileString());//calls the method to print into the file
                }
            }
            file_write.write("\n"); //add a new line to separate the product data
            file_write.close(); //closing the file
            System.out.println("< Write to the file Completed! >");

        }catch (IOException e2){ //will continue if there is an error of creating the file and show below error message
            System.out.println("< Error found when write to the file! >");
        }
    }

    @Override
    public void load_item()  {

        File file = new File("data.txt"); //getting the file's name which contains the saved data

        try (Scanner file_reader = new Scanner(file)) {
            while (file_reader.hasNextLine()) { //check whether there is a next line in the file
                String line = file_reader.nextLine();

                if (line.startsWith("ProductCategory:")) { //check whether if the line starts with this String quote
                    String[] data = line.replace("ProductCategory:", "").split(", "); //adding items separate by , into data array

                    try {
                        String product_category = data[0]; //assign category data to 1st element of the data array
                        String Name = data[1]; //assign name data to 2nd element of the data array
                        String id = data[2]; //assign id data to 3rd element of the data array
                        int quantity = Integer.parseInt(data[5]); //assign quantity to 4th element of the data array and convert it into integer type
                        double price = Double.parseDouble(data[6]); //assign price quantity to 5th element of the data array and convert it into double type

                        //check the category to get size and color or brand and warranty for next elements
                        if (product_category.equals("Clothing")) {
                            String size = data[3];
                            String color = data[4];
                            productsList.add(new Clothing(id, Name, quantity, price, size, color)); //Adding them to the constructor

                        } else if (product_category.equals("Electronics")) {
                            String brand = data[3];
                            int warranty = Integer.parseInt(data[4]);
                            productsList.add(new Electronics(id, Name, quantity, price, brand, warranty)); //Adding them to the constructor
                        }

                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e2) { //array exception handling
                        System.out.println("< Error occurred >");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("< File not found. Try Again! >");
        }
    }

    public void ConsoleMenu() {

        while(true){

            // The Menu for the manager
            System.out.println("""
            \nPlease Select the Option >
            \t1. Add an Item.
            \t2. Delete an Item.
            \t3. Print the list of products.
            \t4. Save to a file.
            \t5. Load saved Items.
            \t6. Open Application.
            \t7. Exit
            """);


            int option = Input("Enter Option : ", "< Please enter number between 1-7 >", 7, 1); // Getting the option from the user

            // choose the option to implement
            switch (option){
                // Adding Items
                case 1 :
                    while (true) {
                        //get the input for the category selection and validation of the input
                        int choice = Input("\nWhat Do you want to Add [1 for Cloth/ 2 for Electronic ] : ", "< Please enter number between 1-2 >",2,1);

                        //Get the common details of the products
                        System.out.print("Enter product ID : "); //getting input for the product's id
                        product_id = input.next();

                        System.out.print("Enter Product Name : "); //getting input for the product's name
                        String product_name = input.next();

                        //getting input for the product's quantity and validation of the input
                        int available_number =Input("Enter available number : ","< Please enter a number >",20000,0);

                        double price =d_Input(); //getting the input for the price and validation to it

                        switch (choice){

                            case 1 :
                                System.out.print("Enter the Size : ");
                                String size = input.next(); //getting size of the cloth product from the user
                                System.out.print("Enter the color of cloth : ");
                                String color = input.next(); //getting color of the cloth product from the user

                                Clothing cloth = new Clothing(product_id,product_name,available_number,price,size,color) ; //Creating new Clothing object called cloth
                                this.Add_item(cloth); //Add cloth object to the productList

                                break;

                            case 2 :
                                System.out.print("Enter brand name : ");
                                String brand = input.next(); //getting brand of the product from the user
                                int warranty =Input("Enter warranty period : ","< Please enter a number >",50,0);

                                //creating new object of Electronics called electronic_1
                                Electronics electronic_1 = new Electronics(product_id,product_name,available_number,price,brand,warranty);
                                this.Add_item(electronic_1); //add electronic_1 object into productList
                        }
                        //Check if manager wants to add another item
                        String desire;
                        while(true){
                            System.out.print("\nWant to add more items[y/n] : "); //Getting the input from user yes or no
                            desire = input.next();
                            if(desire.equalsIgnoreCase("y")){ //check if it is y for yes
                                break; //stop the input check iteration
                            }if(desire.equalsIgnoreCase("n")){ //check if it is n for no
                                break; // stop the input check iteration
                            }else{
                                System.out.println("< Please enter relevant letters ! >"); //print error message if user enter anything instead of y or n
                            }
                        }
                        if(desire.equals("y")){ //Iterate case 1 if it is yes (y)
                            continue;
                        }if(desire.equals("n")){ //Stop iterate of case 1 if user don't want to continue
                            break;
                        }
                    }break;

                // Removing Items
                case 2 :
                    System.out.print("Enter Product ID : "); //getting the product id to delete product
                    product_id =input.next();

                    this.Remove_item();

                    break;

                // Printing Items
                case 3 :
                    this.Print_items(); //Call the Print_item method to print the productList
                    break;
                case 4 :
                    save_items();   //Saving Items to the file
                    break;
                case 5:
                    load_item();   //Load items from the file
                    System.out.println("< Load Data Completed! >");
                    break;
                case 6:
                    //If manager wants to run GUI
                    Westminster_Frame westminster_frame = new Westminster_Frame();
                    westminster_frame.setTitle("Westminster Shopping Centre");
                    westminster_frame.getContentPane().setBackground(new Color(239, 124, 217));
                    westminster_frame.setSize(1000, 600);
                    westminster_frame.setVisible(true);
                    westminster_frame.setLocationRelativeTo(null);
                    westminster_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    break;

            }
            if (option ==7){ //if manager input 7 programme ends
                break;
            }
        }
    }

    // Method that get integer values from the user and do validations
    static int Input(String message, String error, int max , int min){

        while (true){
            try{
                System.out.print(message);
                int entry = input.nextInt(); //getting the input from the user

                //Check the input range which user has entered
                if(entry >max) {
                    System.out.println(error);
                    continue;
                }
                if(entry <min){
                    System.out.println(error);
                    continue;
                }
                return entry; //returns the input

            }catch (InputMismatchException e1){ //Throw an exception using exception Handling
                System.out.println("< Please enter an Integer.! >");
                input.next();

            }
        }
    }

    //Method that gets double inputs from the user and do validations
    static double d_Input(){
        while (true){
            try{
                System.out.print("Enter the Price : ");

                return input.nextDouble(); //getting the input from the user

            }catch (InputMismatchException e1){ //Throw an exception using exception Handling
                System.out.println("< Please enter a Number.! >");
                input.next();
            }
        }

    }

}
