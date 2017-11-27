package caci;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);		
		System.out.println("Would you like to split or assemble a file (split/assemble)");
		String choice = keyboard.next();
		while (!choice.substring(0,1).equalsIgnoreCase("s") || !choice.substring(0,1).equalsIgnoreCase("a")){
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
			} else {
				System.out.println("Please enter a valid option:\nsplit - to split a file\nassemble - to assemble a file");
				choice = keyboard.next();
			}
		}
		keyboard.close();
	}

}
