import java.util.Scanner;

public class UserInterface {
    private Scanner scan;
    private Account currentCard;
    private String fileName;
    private String tableName;


    public UserInterface(Scanner scan, String fileName, String tableName) {
        this.scan = scan;

        this.fileName = fileName;
        this.tableName = tableName;
    }

    public void start() {

        DataBase.createNewDatabase(this.fileName);
        DataBase.createNewTable(this.fileName, this.tableName);


        while (true) {
            System.out.println("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            String userInput = scan.nextLine();
            if (userInput.equals("0")) {
                System.exit(0);
            } else if (userInput.equals("1")) {
                create();
            } else if (userInput.equals("2")) {
                if (checkLogin()) {
                    login();
                } else continue;
            }
        }
    }


    public void create() {
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

    public boolean checkLogin() {
        System.out.println("Enter your card number:");
        String userInputCardNumber = scan.nextLine();
        System.out.println("Enter your PIN:");
        String userInputPin = scan.nextLine();


        if (DataBase.checkLogin()) {
            System.out.println("You have successfully logged in!");
            //currentCard = i;
            return true;
        }
        System.out.println("Wrong card number or PIN!");

        return false;
    }


    public void login() {


        while (true) {
            System.out.println("1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit");
            String userInput = scan.nextLine();
            if (userInput.equals("0")) {
                System.exit(0);
            } else if (userInput.equals("1")) {
                getBalance();
            } else if (userInput.equals("2")) {
                start();
            }
        }
    }

    public void getBalance() {
        System.out.println("Balance: " + currentCard.getBalance());
    }

    public boolean checkAccountForDataBase(Account acc) {
        return DataBase.checkAccount("db.s3db", "card", acc.getCardNumber());
    }





}