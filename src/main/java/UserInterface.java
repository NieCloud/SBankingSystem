
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
                    addIncome();
                    break;
                case "3":
                    transferMoney();
                    break;
                case "4":
                    deleteAccount();
                    start();
                    break;
                case "5":
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
                "2. Add Income\n" +
                "3. Do Transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
    }

    private boolean checkLogin() {
        System.out.println("Enter your card number:");
        String userInputCardNumber = scan.nextLine();
        System.out.println("Enter your PIN:");
        String userInputPin = scan.nextLine();


        if (DataBase.checkAccount(this.fileName, this.tableName, userInputCardNumber, userInputPin)) {

            this.acc = new Account();
            System.out.println("You have successfully logged in!");
            this.acc.setCardNumber(userInputCardNumber);
            this.acc.setPinCode(userInputPin);
            this.acc.setBalance(DataBase.getBalance(this.fileName, this.tableName, userInputCardNumber, userInputPin));
            return true;
        }
        System.out.println("Wrong card number or PIN!");

        return false;
    }

    private void addIncome() {
        System.out.println("Enter income: ");
        int userInput = Integer.parseInt(scan.nextLine());

        if (userInput >= 0) {
            DataBase.addIncome(this.fileName, this.tableName, this.acc.getCardNumber(), userInput);
            this.acc.setBalance(this.acc.getBalance() + userInput);
            System.out.println("Income was added!\n");
        }
    }

    private void transferMoney() {
        System.out.println("Transfer\n" +
                "Enter card number:");
        String userInput = scan.nextLine();

        String[] checkLuhn = userInput.toString().split("");

        if (userInput.charAt(15) != generateRandoms.checkForLuhnAlgorithm(checkLuhn).charAt((15)) || checkLuhn.length != 16) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else if (!DataBase.checkAccount(this.fileName, this.tableName, userInput)) {
            System.out.println("Such a card does not exist.");
        } else {
            System.out.println("Enter how much money you want to transfer:");
            int userMoneyToTransfer = Integer.parseInt(scan.nextLine());

            if (this.acc.getBalance() < userMoneyToTransfer) {
                System.out.println("Not enough money!");
            } else {
                DataBase.addIncome(this.fileName, this.tableName, userInput, userMoneyToTransfer);
                System.out.println("Success!");
            }

        }
    }

    private void deleteAccount() {
        DataBase.deleteAccount(this.fileName, this.tableName, this.acc.getCardNumber());
        System.out.println("The account has been closed!");
    }

}