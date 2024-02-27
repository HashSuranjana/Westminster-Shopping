import javax.swing.*;
import java.awt.*;

public class Main {
    static String user_type;

    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }

        while (true){

            System.out.println("\n\t\tWelcome to WestminsterShoppingManager !");
            System.out.println("-".repeat(50) + "\n");

            System.out.print("Enter 'U' for User or 'M' for Manager : ");
            user_type = WestminsterShoppingManager.input.next();

            if (user_type.equalsIgnoreCase("M")){

                ShoppingManager manager1 = new WestminsterShoppingManager(50);
                manager1.ConsoleMenu(); //runs the ConsoleMenu method
                break;

            }else if(user_type.equalsIgnoreCase("U")){

                WestminsterUserManager westminsterUserManager = new WestminsterUserManager();
                westminsterUserManager.Usermenu();

                Westminster_Frame westminster_frame = new Westminster_Frame();
                westminster_frame.setTitle("Westminster Shopping Centre");
                westminster_frame.getContentPane().setBackground(new Color(245, 106, 216));
                westminster_frame.setSize(1000, 600);
                westminster_frame.setVisible(true);
                westminster_frame.setLocationRelativeTo(null);
                westminster_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                break;

            }else {
                System.out.println("< Please enter relevant letters! >");

            }
        }
    }
}
