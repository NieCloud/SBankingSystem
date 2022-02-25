import java.util.Random;

class generateRandoms {

    public static String generateRandomCardNumber() {



        Random random = new Random();
        StringBuilder tempCard = new StringBuilder();

        // 400000 is a bank number for simplicity
        tempCard.append("400000");
        for (int i = 0; i < 10; i++) {
            int temporary = random.nextInt(10);
            tempCard.append(temporary);
        }

        // Creating checksum
        String[] splintedString = tempCard.toString().split("");

        return checkForLuhnAlgorithm(splintedString);
    }

    public static String generateRandomPin() {
        Random random = new Random();
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int temporary = random.nextInt(10);
            pin.append(temporary);
        }

        return pin.toString();
    }

    private static String checkForLuhnAlgorithm(String[] splitString) {

        //setting integers instead of strings into array
        int size = splitString.length;
        int [] arr = new int [size];
        int [] checkedArray = new int [size];

        for(int i=0; i<size; i++) {
            arr[i] = Integer.parseInt(splitString[i]);
            checkedArray[i] = arr[i];
        }

        //1 and 2 Steps - dropping the last digit (size - 1) and multiply odd digits by 2:
        for (int i = 0; i < size - 1; i += 2) {
            arr[i] *= 2;
        }

        //3 Step - subtract 9 for numbers over 9
        for (int i = 0; i < size - 1; i++) {
            if (arr[i] > 9 ) {
                arr[i] -= 9;
            }
        }

        //4 Step - adding all numbers except the last one

        int sumOfTheNumbers = 0;
        for (int i = 0; i < size - 1; i++) {
            sumOfTheNumbers += arr[i];
        }

        //5 Step Implementing last digit

        if (sumOfTheNumbers % 10 != 0) {
            checkedArray[size-1] = 10 - (sumOfTheNumbers % 10);
        } else {
            checkedArray[size-1] = 0;
        }


        //6 Step - making String out of Array and returning it

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < size; i++) {
            str.append(checkedArray[i]);

        }

        return str.toString();

    }


}