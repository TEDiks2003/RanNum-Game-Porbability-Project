public class ProbCalculator {
    class Xnum {
        public int x;
        public int rank;
        Xnum(int x){
            this.x = x;
        }
        public void assignRank(int rank){
            this.rank = rank;
        }
    }
    private Xnum[] xNumArray = new Xnum[10];
    private int nextXIndex = 0;

    public boolean isFirstGo(){
        return nextXIndex==0;
    }

    public void addX(int x){
        xNumArray[nextXIndex] = new Xnum(x);
    }

    public void giveRank(int r){
        xNumArray[nextXIndex].assignRank(r);
        nextXIndex++;
    }

    private void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i ++) {
            int element = arr[i];
            int indexLeft = i - 1;

            while (indexLeft >= 0 && arr[indexLeft] > element) {
                arr[indexLeft + 1] = arr[indexLeft];
                indexLeft --;
            }

            arr[indexLeft + 1] = element;
        }
    }

    private void insertionSort(Xnum[] arr) {
        for (int i = 1; i < nextXIndex; i ++) {
            Xnum element = arr[i];
            int indexLeft = i - 1;

            while (indexLeft >= 0 && arr[indexLeft].rank > element.rank) {
                arr[indexLeft + 1] = arr[indexLeft];
                indexLeft --;
            }

            arr[indexLeft + 1] = element;
        }
    }

    private int factorial(int n) {
        int fact = 1;
        int i = 1;
        while(i <= n) {
            fact *= i;
            i++;
        }
        return fact;
    }

    private int combinations(int n, int r){
        return factorial(n) / (factorial(r) * factorial(n-r));
    }

    private int getC(int[] bArray, int xLeft){
        int C = combinations(xLeft, bArray[0]);
        for (int i=1; i<bArray.length-1; i++){
            xLeft -= bArray[i-1];
            C = C*combinations(xLeft, bArray[i]);
        }
        return C;
    }

    public double getProbW(int testRank){
        xNumArray[nextXIndex].assignRank(testRank);
        int noX = nextXIndex+1;

        int[] rArray = new int[noX];
        for (int i=0; i<noX; i++){
            rArray[i]=xNumArray[i].rank;
        }
        insertionSort(rArray);

        int[] xArray = new int[noX];
        for (int i=0; i<noX; i++){
            xArray[i]=xNumArray[i].x;
        }
        insertionSort(xArray);

        int[] bArray = new int[noX+1];
        bArray[0] = rArray[0]-1;
        for (int i=1; i<noX; i++){
            bArray[i] = rArray[i]-rArray[i-1]-1;
        }
        bArray[noX] = 10-rArray[noX-1];

        int C = getC(bArray, 10-noX);

        double probBelowX1 = (Math.pow(((double) xArray[0])/1000.0, bArray[0]));
        double prob = C*probBelowX1;
        for (int i=1; i<noX; i++){
            int xi = xArray[i-1];
            int xj = xArray[i];
            int bi = bArray[i];
            double probBetween = (Math.pow(((double) (xj-xi+1))/1000.0, bi));
            prob = prob*probBetween;
        }
        double probAboveXn = (Math.pow(((double) (1001-xArray[noX-1]))/1000.0, bArray[noX]));
        prob = prob*probAboveXn;

        return prob;
    }

    public int[] getAvailableRanks(){
        int[] availableRanksArray;
        if (nextXIndex == 0){
            availableRanksArray = new int[10];
            for (int i=0; i<10; i++){
                availableRanksArray[i] = i+1;
            }
        } else {
            insertionSort(xNumArray);
            int rankAbove = 11;
            for (int i=0; i<nextXIndex; i++){
                if (xNumArray[i].x > xNumArray[nextXIndex].x){
                    rankAbove = xNumArray[i].rank;
                    break;
                }
            }
            int rankBelow = 0;
            for (int i=nextXIndex-1; i>=0; i--){
                if (xNumArray[i].x < xNumArray[nextXIndex].x){
                    rankBelow = xNumArray[i].rank;
                    break;
                }
            }

            availableRanksArray = new int[rankAbove-rankBelow-1];

            for (int i=0; i<availableRanksArray.length; i++){
                availableRanksArray[i] = rankBelow+1+i;
            }

        }
        return availableRanksArray;
    }
    public int findBestRank(){

        int[] availableRanksArray = getAvailableRanks();

        System.out.println("Available Ranks: ");
        for (int j : availableRanksArray) {
            System.out.print(j + ", ");
        }
        System.out.println();

        double[] probWArray = new double[availableRanksArray.length];
        int bestRank = 0;
        double bestProb = 0;
        for (int i=0; i<probWArray.length; i++){
            probWArray[i] = getProbW(availableRanksArray[i]);
            if (probWArray[i] > bestProb){
                bestProb = probWArray[i];
                bestRank = availableRanksArray[i];
            }
        }

        System.out.println("Rank Prob: ");
        for(int i=0; i<availableRanksArray.length; i++){
            System.out.print(probWArray[i]+", ");
        }
        System.out.println();
        System.out.println("Best Rank: "+bestRank);

        return bestRank;
    }

    public boolean checkIsXValid(){
        insertionSort(xNumArray);
        int index = -1;
        for (int i=0; i<nextXIndex; i++){
            if (xNumArray[i].x > xNumArray[nextXIndex].x){
                index = i;
                break;
            }
        }
        if (index == -1){
            return xNumArray[nextXIndex - 1].rank != 10;
        }
        int[] bArray = new int[nextXIndex];
        bArray[0] = xNumArray[0].rank-1;
        for (int i=1; i<nextXIndex; i++){
            bArray[i] = xNumArray[i].rank-xNumArray[i-1].rank-1;
        }
        return bArray[index] != 0;
    }

    public int[][] getTable(){
        int[][] table = new int[2][nextXIndex];
        for (int i=0; i<nextXIndex; i++){
            table[0][i] = xNumArray[i].x;
            table[1][i] = xNumArray[i].rank;
        }
        return table;
    }
}
