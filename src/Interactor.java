import java.util.Scanner;

public class Interactor {
    private Scanner input;
    private ProbCalculator calc;
    private boolean isUserInputsRank;
    private boolean isUserInputsX;
    Interactor(Scanner input, ProbCalculator calc, boolean isUserInputsX, boolean isUserInputsRank){
        this.input = input;
        this.calc = calc;
        this.isUserInputsX = isUserInputsX;
        this.isUserInputsRank = isUserInputsRank;
    }

    public int getX(){
        int X;
        if (isUserInputsX){
            System.out.println("Input number: ");
            X = input.nextInt();
            while (X<0 || X>1000){
                System.out.println("Please enter a valid number: ");
                X = input.nextInt();
            }
        } else {
            X = (int) (Math.random()*1000)+1;
            System.out.println("X num: "+X);
        }
        return X;
    }

    public int getRank(){
        int rank;
        if (isUserInputsRank){
            System.out.println("Input rank: ");
            rank = input.nextInt();
            if (!calc.isFirstGo()) {
                boolean isAvailable = false;
                int[] availableRanks = calc.getAvailableRanks();
                while (!isAvailable) {
                    for (int availableRank : availableRanks) {
                        if (rank == availableRank) {
                            isAvailable = true;
                            break;
                        }
                    }
                    if (!isAvailable){
                        System.out.println("Available Ranks: ");
                        for (int availableRank : availableRanks) {
                            System.out.print(availableRank + ", ");
                        }
                        System.out.println();
                        System.out.println("Please enter an available Rank: ");
                        rank = input.nextInt();
                    }
                }
            }
        } else {
            rank = calc.findBestRank();
        }
        return rank;
    }
}
