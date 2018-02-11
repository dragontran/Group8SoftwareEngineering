import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import Assembler.Assembler;
import Checksum.Checksum;
import Splitter.Splitter;

public class Driver {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        boolean done = false;

        while (!done){
            System.out.println("Would you like to split or assemble a file (split/assemble) or generate a checksum (checksum)");
            String choice = keyboard.next();
            while (!choice.substring(0,1).equalsIgnoreCase("s") || !choice.substring(0,1).equalsIgnoreCase("a") || !choice.substring(0,1).equalsIgnoreCase("c")) {
                if (choice.substring(0, 1).equalsIgnoreCase("s")){
                    System.out.println("Enter the name of the file to split");
                    String filename = keyboard.next();

                    Splitter.split(filename);
                    break;
                } else if (choice.substring(0,1).equalsIgnoreCase("a")) {
                    System.out.println("Enter the name of the base file (i.e. temp.txt.part0 -> temp.txt)");
                    String filename = keyboard.next();

                    // probably just use the base file name since they won't have the entire file (but wanted a new file for testing idk)
                    System.out.println("Enter the name of the newly created file");
                    String newfile = keyboard.next();

                    System.out.println("Enter the largest subfile part number (i.e. temp.txt.part1754 -> 1754)");
                    int numparts = keyboard.nextInt();

                    Assembler.assemble(filename, newfile, numparts);
                    break;
                }
                else if (choice.substring(0,1).equalsIgnoreCase("c")) {
                    System.out.println("Enter the name of the file for checksum generation");
                    String filename = keyboard.next();

                    try {
                        Checksum checksum = new Checksum(new File(filename));
                        System.out.println("The checksum for " + filename + " is " + checksum.getCheckSum());
                    } catch (IOException e) {
                        System.out.println("File " + filename +" not found");
                    }
                    break;
                } else {
                    System.out.println("Please enter a valid option:\nsplit - to split a file\nassemble - to assemble a file");
                    choice = keyboard.next();
                }
            }
            System.out.println("Would you like to split/assemble/checksum another file? (y/n)");
            choice = keyboard.next();
            if (choice.substring(0,1).equalsIgnoreCase("n")){
                done = true;
            }
        }
        keyboard.close();
    }
}