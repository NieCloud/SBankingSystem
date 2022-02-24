class Account {
    private String cardNumber;
    private String pinCode;
    private int balance;

    public Account() {
        this.cardNumber = generateRandoms.generateRandomCardNumber();
        this.pinCode = generateRandoms.generateRandomPin();
        this.balance = 0;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public int getBalance() {
        return balance;
    }

    public void returnCardCredentials() {
        System.out.println(this.cardNumber);
        System.out.println(this.pinCode);
    }



}