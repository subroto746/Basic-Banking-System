import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Bank {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        NewAccount newAccount = new NewAccount();
        ExistingAccount existingAccount = new ExistingAccount();

        while(true){
            System.out.println("1.New Customer");
            System.out.println("2.Existing Customer");

            int choice = sc.nextInt();
            switch (choice){
                case 1:
                    newAccount.SignUp();
                    break;
                case 2:


                    System.out.println("Enter your Email");
                    String email = sc.next();
                    System.out.println("Enter your password");
                    String password = sc.next();
                    existingAccount.LogIn(email,password);

                    break;
            }
        }
    }
}
