import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ProbCalculator myCalc = new ProbCalculator();

        System.out.println("Choose User Interface:");
        System.out.println("1) Input numbers and Ranks");
        System.out.println("2) Input only numbers, Ranks will be calculated");
        System.out.println("3) Input only Ranks, numbers will be generated");
        System.out.println("4) No user inputs, everything will be generated and calculated");

        int userInput = input.nextInt();
        while(userInput<1 || userInput>4){
            System.out.println("Please enter a valid option.");
            userInput = input.nextInt();
        }

        boolean inputX;
        boolean inputRank;

        switch (userInput) {
            case 1 -> {
                inputX = true;
                inputRank = true;
            }
            case 2 -> {
                inputX = true;
                inputRank = false;
            }
            case 3 -> {
                inputX = false;
                inputRank = true;
            }
            default -> {
                inputX = false;
                inputRank = false;
            }
        }

        Interactor userInt = new Interactor(input, myCalc, inputX, inputRank);

        myCalc.addX(userInt.getX());
        int rank = userInt.getRank();
        System.out.println("Prob of W: "+myCalc.getProbW(rank));
        myCalc.giveRank(rank);
        myCalc.addX(userInt.getX());

        int xLeft = 8;

        while (myCalc.checkIsXValid() && xLeft>0){
            xLeft--;
            rank = userInt.getRank();
            System.out.println("Prob of W: "+myCalc.getProbW(rank));
            myCalc.giveRank(rank);
            displayTable(myCalc.getTable());
            myCalc.addX(userInt.getX());
        }

        if (xLeft>0){
            System.out.println("You Lost :(");
            System.out.println("You Had "+xLeft+" Left");
        } else {
            System.out.println("You Won :)");
        }
    }

    public static void displayTable(int[][] table){
        System.out.print("X numbers: ");
        for (int i=0; i<table[0].length; i++){
            System.out.print(table[0][i]+", ");
        }
        System.out.println();
        System.out.print("Ranks: ");
        for (int i=0; i<table[1].length; i++){
            System.out.print(table[1][i]+", ");
        }
        System.out.println();
    }
}