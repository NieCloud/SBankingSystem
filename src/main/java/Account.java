class Account {
    private String cardNumber;
    private String pinCode;
    private int balance;

    public Account() {
        this.cardNumber = generateRandoms.generateRandomCardNumber();
        this.pinCode = generateRandoms.generateRandomPin();
        this.balance = 0;
    }

    String getCardNumber() {
        return cardNumber;
    }

    String getPinCode() {
        return pinCode;
    }

    int getBalance() {
        return balance;
    }

    void returnCardCredentials() {
        System.out.println(this.cardNumber);
        System.out.println(this.pinCode);
    }


    void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    void setBalance(int balance) {
        this.balance = balance;
    }



}