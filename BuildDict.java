import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class BuildDict {
	private Set<String> wordSet = new TreeSet<>();
	
	/**
	 * Main function
	 */
	public static void main(String[] args) {
		new BuildDict().processUser();
	}

	/**
	 * Ask the user for a list of text files to read from.
	 * Read all of them, and build the dictionary of all distinct words,
	 * and write them into the dictionary called myDict.dat
	 */
	private void processUser() {
		List<File> fileList = new ArrayList<>();
		
		try (Scanner kb = new Scanner(System.in)) {
			System.out.println("Enter filenames separated by space character:");
			String line = kb.nextLine();
			try (Scanner lineScan = new Scanner(line)) {
				while (lineScan.hasNext())
					fileList.add(new File("..\\" + lineScan.next()));
			}
		}
		
		for (File file : fileList) {
			addWordsToSet(file);
		}
		
		saveSetToFile("myDict.dat");
		
		printFirstWords(40);
	}
	
	/**
	 * Write whole set of words into the dictionary
	 */
	private void saveSetToFile(String filename) {
		try (PrintWriter pw = new PrintWriter("..\\" + filename)) {
			for (String word : wordSet) {
				pw.println(word);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print 40 words from word set in alphabetic order in a nice readable format
	 */
	private void printFirstWords(int numWords) {
		int count = 0;
		for (String word : wordSet) {
			System.out.printf("%02d. %s\n", ++count, word);
			if (count == numWords)
				break;
		}
	}

	/**
	 * Reads words from file and adds them to set
	 */
	private void addWordsToSet(File file) {
		try (Scanner fileScan = new Scanner(file).useDelimiter("[^\\p{Alpha}]+")) {
			while (fileScan.hasNext()) {
				wordSet.add(fileScan.next().toLowerCase());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
}
