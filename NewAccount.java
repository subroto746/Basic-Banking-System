import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class NewAccount {

    Scanner sc = new Scanner(System.in);
    public void SignUp(){
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            Conn c1 = new Conn();
            long account_number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;



            System.out.println("\n\n\n----Registration Forum-----\n\n");
            System.out.println("Enter your Email");
            String email = bf.readLine();
            System.out.println("Enter password");
            String password = bf.readLine();
            System.out.println("Enter your Name");
            String name = bf.readLine();
            System.out.println("Enter your father's name");
            String father_name = bf.readLine();
            System.out.println("Age");
            int age = sc.nextInt();
            System.out.println("Enter Amount that you wanna deposite");
            double amount = sc.nextDouble();


            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            LocalDateTime now = LocalDateTime.of(date,time);


            System.out.println("insert into accounts values("+"\""+email+"\","+"\""+password+"\","+"\""+name+"\","+"\""+father_name+"\""+","+age+","+amount+","+"\""+now+"\""+");");
            int rs = c1.s.executeUpdate("insert into accounts values("+"\""+email+"\","+"\""+password+"\","+"\""+account_number+"\","+"\""+name+"\","+"\""+father_name+"\""+","+age+","+amount+","+"\""+now+"\""+");");
            if(rs == 1){
                System.out.println("Account Created Successfully");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}

