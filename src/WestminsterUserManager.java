import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterUserManager extends User implements UserManager {

    static Scanner input = new Scanner(System.in);
    private ArrayList<User> user_details; //Initializing the user_Details Arraylist

    public WestminsterUserManager() {
        user_details = new ArrayList<>();
        load_user(); // Load existing user details from the file
    }

    @Override
    public void user_save(User user) {  //Method to save the user objects to the file
        if (user_details == null) {
            user_details = new ArrayList<>();
        }

        // Check if the user with the same username already exists
        boolean userExists = user_details.stream().anyMatch(existingUser ->
                existingUser.getUser_name().equals(user.getUser_name()));

        if (userExists) {
            System.out.println("< Error: User with the same username already exists! >");
        } else {
            user_details.add(user);

            try (FileWriter file_write = new FileWriter("user.txt")) {
                for (User savedUser : user_details) {
                    file_write.write(savedUser.UserString() + "\n"); //writing to the file
                }
                System.out.println("< User details saved successfully! >");
            } catch (IOException e) {
                System.out.println("< Error occurred while saving user details! >");
            }
        }
    }

    @Override
    public void load_user() {
        File file = new File("user.txt");

        try (Scanner file_reader = new Scanner(file)) {
            while (file_reader.hasNextLine()) {
                String line = file_reader.nextLine();

                if (line.startsWith("UserProfile:")) {
                    String[] user_data = line.replace("UserProfile:", "").split(", ");


                    if (user_data.length >= 3) { // Check if the array has enough elements
                        try {
                            String user_name = user_data[1];
                            String password = user_data[2];
                            User newUser = new User(user_name, password);

                            // Check if the user with the same username already exists in the ArrayList
                            boolean userExists = user_details.stream().anyMatch(existingUser ->
                                    existingUser.getUser_name().equals(newUser.getUser_name()));


                            if (!userExists) { // If the user doesn't exist, add to the ArrayList
                                user_details.add(newUser);
                            }

                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e2) {
                            System.out.println("< Error occurred >");
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("< File not found. Try Again! >");
        }
    }

    @Override
    public void Usermenu() {
        load_user();
        while (true) {
            System.out.print("\nAre you a new Customer[y/n] : "); //Asking the user whether new or previous user
            String op = input.next();
            if (op.equalsIgnoreCase("y")) {
                System.out.print('\n' + "Enter your user Name : "); //if new user ask to enter name
                String user_name = input.next();

                // Check if the user with the same username already exists (Reference: https://www.tabnine.com/code/java/methods/java.util.stream.Stream/anyMatch)
                boolean userExists = user_details.stream().anyMatch(existingUser ->
                        existingUser.getUser_name().equals(user_name));

                if (userExists) {
                    System.out.println("\n< Error: User with the same username already exists! >"); //giving the error message
                } else {
                    System.out.print("Enter your password : "); //if a new user then ask to enter the password
                    String password = input.next();
                    User new_user = new User(user_name, password); // creating a new object and save into the file
                    user_save(new_user);
                }

            } else if (op.equalsIgnoreCase("n")) {
                System.out.print('\n' + "Enter your user Name : "); //ask to enter the username
                String user_name = input.next();
                System.out.print("Enter your password : "); //ask the set password
                String password = input.next();

                // Check if there is a user with the same username and password (  Reference : https://www.tabnine.com/code/java/methods/java.util.stream.Stream/anyMatch)
                boolean userExists = user_details.stream().anyMatch(existingUser ->
                        existingUser.getUser_name().equals(user_name) && existingUser.getPassword().equals(password));

                if (userExists) {
                    System.out.println("< Welcome! >"); //continue to the GUI
                    break;
                } else {
                    System.out.println("\n< Incorrect username or password. Please try again! >"); //Show the error message
                }
            }
        }
    }
}