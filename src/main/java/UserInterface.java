import javax.xml.crypto.Data;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scan;
    private final String fileName;
    private final String tableName;
    private Account acc;


    public UserInterface(Scanner scan, String fileName, String tableName) {
        this.scan = scan;

        this.fileName = fileName;
        this.tableName = tableName;
    }

    public void start() {

        DataBase.createNewDatabase(this.fileName);
        DataBase.createNewTable(this.fileName, this.tableName);


        while (true) {
            printLoginInterface();

            String userInput = scan.nextLine();
            switch (userInput) {
                case "0":
                    System.exit(0);
                case "1":
                    create();
                    break;
                case "2":
                    if (checkLogin()) {
                        login();
                    }
                    break;
            }
        }
    }


    private void create() {
        Account acc = new Account();
        while(true) {

            // here we check if account with such card number already exists in our system, if yes - we just create a new one
            if (!checkAccountForDataBase(acc)) {
                break;
            } else {
                acc = new Account();
            }
        }
        acc.returnCardCredentials();
        DataBase.insertAccount(this.fileName,this.tableName,acc.getCardNumber(),acc.getPinCode());
    }




    private void login() {
        while (true) {
            printAccountInterface();

            String userInput = scan.nextLine();

            switch (userInput) {
                case "0":
                    System.exit(0);
                case "1":
                    getBalance();
                    break;
                case "2":
                    start();
                    break;
            }
        }
    }

    private void getBalance() {
        System.out.println("Balance: " + acc.getBalance());
    }

    private boolean checkAccountForDataBase(Account acc) {
        return DataBase.checkAccount("db.s3db", "card", acc.getCardNumber());
    }

    private void printLoginInterface() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
    }

    private void printAccountInterface() {
        System.out.println("1. Balance\n" +
                "2. Log out\n" +
                "0. Exit");
    }

    private boolean checkLogin() {
        System.out.println("Enter your card number:");
        String userInputCardNumber = scan.nextLine();
        System.out.println("Enter your PIN:");
        String userInputPin = scan.nextLine();


        if (DataBase.checkAccount(this.fileName, this.tableName, userInputCardNumber, userInputPin)) {

            Account acc = new Account();
            this.acc = acc;
            System.out.println("You have successfully logged in!");
            this.acc.setCardNumber(userInputCardNumber);
            this.acc.setPinCode(userInputPin);
            this.acc.setBalance(DataBase.getBalance(this.fileName, this.tableName, userInputCardNumber, userInputPin));
            return true;
        }
        System.out.println("Wrong card number or PIN!");

        return false;
    }



}