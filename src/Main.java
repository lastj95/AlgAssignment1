import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * Program Authors: Matthew Bertello, James Last, Adeel Sultan
 */
public class Main {

    /*
     * Lets the user pick between the two algorithms encompassing this assignment.
     */
    public static void main(String[] args) {
        int alg = 0;

        // Asks user for input corresponding to one of the two problems.
        while (alg == 0) {
            System.out.print("Welcome to Assignment 1.\nEnter 1 for the stock problem, enter 2 for the tree problem, " +
                             "or enter 3 for both: ");
            Scanner userIn = new Scanner(System.in);
            String input = userIn.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                alg = Integer.parseInt(input);
            }
            else {
                System.out.println("Error: Valid input not given.");
            }
        }

        switch(alg) {
            case 1:
                stockPriceProblemHelper();
                break;
            case 2:
                majorityTreeHelper();
                break;
            default:
                stockPriceProblemHelper();
                System.out.println();
                majorityTreeHelper();
                break;
        }

    }


    /*
     * Generates the arrays of stock prices before calling the recursive algorithm to determine the max profit.
     */
    public static void stockPriceProblemHelper() {
        // Generates the lists of stock prices.
        for(int p=482; p<488; p++){
            Random r = new Random(p);
            int size = 10000000;
            int[] vals = new int[size];
            for(int i=0; i<size; i++){
                vals[i] = r.nextInt(Integer.MAX_VALUE);
            }
            // Calls the recursive method to print out the max profit for this stock price list.
            System.out.println("Max profit for list " + p + ": " + stockPriceProblem(vals));
        }
    }

    /*
     * Recursively gets the max profit from buying and selling once each in the array.
     */
    public static int stockPriceProblem(int[] vals) {
        return GetMaxHelper(vals, 0, vals.length - 1);
    }

    public static int GetMaxHelper(int[] arr, int start, int end) {
        if (start >= end) {
            return Integer.MIN_VALUE;
        }
        else if (end - start == 1) {
            return arr[end] - arr[start];
        }

        int mid = start + ((end - start) / 2);
        int leftMax = GetMaxHelper(arr, start, mid);
        int rightMax = GetMaxHelper(arr, mid + 1, end);

        int leftCrossMin = arr[start];
        int rightCrossMax = arr[mid + 1];

        for(int i = start + 1; i <= mid; i++) {
            if(arr[i] < leftCrossMin) {
                leftCrossMin = arr[i];
            }
        }

        for(int i = mid + 2; i <= end; i++) {
            if(arr[i] > rightCrossMax) {
                rightCrossMax = arr[i];
            }
        }

        int crossMax = rightCrossMax - leftCrossMin;

        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }

    /*
     * Generates the list from a file called input4.txt from the InputFiles subdirectory. Then calls on
     * recursive method to get the majority-occurring element in the list.
     */
    public static void majorityTreeHelper() {
        // Gets input from input4.txt.
        ArrayList<String> treeList = new ArrayList<>();

        try {
            File inputFile = new File("./InputFiles/input4.txt");
            Scanner fileIn = new Scanner(inputFile);

            // Goes line by line adding each "tree vote" into the list.
            while (fileIn.hasNextLine()) {
                treeList.add(fileIn.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Out of " + treeList.size() + " votes, the majority-favored tree is: ");
        System.out.println(majorityTree(treeList));
    }

    /*
     * Recursively determines what element in the list occurs most.
     */
    public static String majorityTree(ArrayList<String> treeList) {
        String majority = findMajorityHelper(treeList, 0, treeList.size() - 1);
        return majority != null ? majority : "No favorite tree :(";
    }

    public static String findMajorityHelper(ArrayList<String> trees, int start, int end) {
        if(start >= end) {
            return trees.get(start);
        }
        int mid = start + ((end - start) / 2);
        String leftMajority = findMajorityHelper(trees, start, mid);
        String rightMajority = findMajorityHelper(trees, mid + 1, end);
        if(java.util.Objects.equals(leftMajority, rightMajority)) {
            return leftMajority;
        }
        int leftCount = 0;
        int rightCount = 0;

        for(int i = start; i <= end; i++) {
            if(trees.get(i).equals(leftMajority)) {
                leftCount++;
            } else if(trees.get(i).equals(rightMajority)) {
                rightCount++;
            }
        }

        int majorityCount = (end - start) / 2 + 1;

        if(leftCount > majorityCount) {
            return leftMajority;
        } else if(rightCount > majorityCount) {
            return rightMajority;
        } else {
            return null;
        }
    }
}