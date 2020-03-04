import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

import static javafx.application.Platform.exit;

public class ExistingAccount {

    Scanner sc = new Scanner(System.in);

    public void withdraw(String email,String password){
       // ResultSet rs = c1.s.executeQuery("select *from accounts where email = "+"\""+email+"\""+" and password ="+"\""+password+"\""+";");
        System.out.println("Enter the amount you want to withdraw!!!");
        try {


            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("select *from accounts where email = "+"\""+email+"\""+" and password ="+"\""+password+"\""+";");
            if(!rs.next()) {
                System.out.println("No Data found!!!");
            }else {


                double amount = sc.nextDouble();
                String amt = rs.getString(7);
                double available_amount = Double.parseDouble(amt);
                if (available_amount >= amount) {
                     email = rs.getString(1);
                    double remaining_amount = available_amount - amount;
                    // System.out.println("update accounts set current balance = " + "\"" + remaining_amount + "\"" + " where email = " + "\"" + email + "\";");
                    int query = c1.s.executeUpdate("update accounts set currentbalance = " + "\"" + remaining_amount + "\"" + " where email = " + "\"" + email + "\";");
                    if (query == 1) {
                        System.out.println("Transaction Successful!!! ");

                        /***
                         *
                         * Passbook update
                         */

                        ResultSet rss = c1.s.executeQuery("select *from accounts where email = "+"\""+email+"\""+" and password ="+"\""+password+"\""+";");

                        if(rss.next()) {

                            String name = rss.getString(4);
                            String fileName = name.replace(" ", "_");
                            fileName = fileName.concat("_passbook.txt");
                            System.out.println(fileName);
                            File file = new File("/home/subrotho/IdeaProjects/Banking System/passbook/" + fileName);
                            FileWriter bf = new FileWriter(file, true);
                            BufferedWriter f = new BufferedWriter(bf);

                            LocalDate date = LocalDate.now();
                            LocalTime time = LocalTime.now();
                            LocalDateTime now = LocalDateTime.of(date, time);

                            f.write("Withdrawal of amount " + amount + " Now Available balance " + rss.getString(7) + " on " + now + "\n");
                            f.close();

                        }
                        rss.close();
                        LogIn(email,password);

                    }
                } else {
                    System.out.println("Your available balance is less then " + amount);
                    rs.close();
                    LogIn(email,password);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void deposit(String email,String password){
        try{
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("select *from accounts where email = "+"\""+email+"\""+" and password ="+"\""+password+"\""+";");
            if(!rs.next()) {
                System.out.println("No Data found!!!");
            }else {
                System.out.println("Enter the amount you wanna deposit");
                double amt = sc.nextDouble();
                double amount = amt;
                String current_balance = rs.getString(7);
                amt += Double.parseDouble(current_balance);
                int res = c1.s.executeUpdate("update accounts set currentbalance = " + "\"" + amt + "\"" + " where email = " + "\"" + email + "\";");
                if (res == 1) {
                    System.out.println("Transaction Successful!!! ");


                    /**
                     * Passbook Update
                     */
                    ResultSet rss = c1.s.executeQuery("select *from accounts where email = "+"\""+email+"\""+" and password ="+"\""+password+"\""+";");

                    if(rss.next()) {
                        String name = rss.getString(4);
                        String fileName = name.replace(" ", "_");
                        fileName = fileName.concat("_passbook.txt");
                        System.out.println(fileName);
                        File file = new File("/home/subrotho/IdeaProjects/Banking System/passbook/" + fileName);
                        FileWriter bf = new FileWriter(file, true);
                        BufferedWriter f = new BufferedWriter(bf);

                        LocalDate date = LocalDate.now();
                        LocalTime time = LocalTime.now();
                        LocalDateTime now = LocalDateTime.of(date, time);

                        f.write("Deposit of amount " + amount + " Now Available balance " + rss.getString(7) + " on " + now + "\n");
                        f.close();

                    }
                    rs.close();
                    rss.close();
                    LogIn(email,password);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void LogIn(String email,String password){

        try {
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("select *from accounts where email = "+"\""+email+"\""+" and password ="+"\""+password+"\""+";");
            if(!rs.next()){
                System.out.println("No Data found!!!");

            }else {

                System.out.println("\n\n\n----Account Details-----\n\n");
                String ac_no = rs.getString(3);
                System.out.println("Account Number :- " + ac_no);
                String name = rs.getString(4);
                System.out.println("Name :- " + name);
                String fathers_name = rs.getString(5);
                System.out.println("Father's Name :- " + fathers_name);
                String age = rs.getString(6);
                System.out.println("Age :- " + age);
                String curent_balance = rs.getString(7);
                System.out.println("Current Balance :- " + curent_balance);
                String last_trans = rs.getString(8);
                System.out.println("Last Transaction :- " + last_trans);

                System.out.println("\n\n");
                System.out.println("1.Withdraw");
                System.out.println("2.Deposit");
                System.out.println("3.Exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        withdraw(email,password);
                        break;
                    case 2:
                        deposit(email,password);
                        break;
                    case 3:
                        rs.close();
                        exit();

                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
