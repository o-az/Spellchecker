import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class SpellCheckVersion2 {
	private static final String DICT_NAME = "myDict.dat";
	WordList wordList = new WordList();
	Scanner kb = new Scanner(System.in);
	
	/**
	 * Main function
	 */
	public static void main(String[] args) {
		SpellCheckVersion2 spellCheck2 = new SpellCheckVersion2();
		spellCheck2.readDict("..\\" + DICT_NAME);
		
		System.out.println("Type the name of a document she wants spell-checked: ");
		String filename;
		filename = spellCheck2.kb.nextLine();
		spellCheck2.spellChecking("..\\" + filename);
	}

	/**
	 * Read words from file one-by-one, and check which words appear in its dictionary,
	 * and will produce an .out file that flags all words with wrong spellings
	 * by surrounding them with special character (=). 
	 */
	private void spellChecking(String filename) {
		try (Scanner fileScan = new Scanner(new File(filename)); PrintWriter pw = new PrintWriter(filename + ".out")) {
			while (fileScan.hasNext()) {
				String word = fileScan.next();
				if (wordList.search(word.toLowerCase())) {
					pw.print(word + " ");
				} else {
					System.out.println("Misspelled word: " + word);
					String suggestedWord = chooseSuggestedWord(replacementWord(word));
					if (suggestedWord == null)
						suggestedWord = word;
					pw.print(suggestedWord + " ");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Document was spellchecked successfully.");
		wordList.sortedPrint(DICT_NAME);
		System.out.println("Dictionary was updated.");
	}

	/**
	 * Prompt user for choose one of suggested words
	 */
	private String chooseSuggestedWord(String[] replacementWord) {
		int choice = -1;

		do {
			System.out.print("List of suggestions: ");
			int i;
			for (i = 0; i < replacementWord.length; i++) {
				System.out.print("[" + i + "] " + replacementWord[i] + " ");
			}
			System.out.print("[" + i++ + "] No change ");
			System.out.print("[" + i + "] Enter your own choice: ");
			choice = Integer.parseInt(kb.nextLine());
		} while (choice < 0 || choice > (replacementWord.length - 1) + 2);
		
		if (choice == (replacementWord.length - 1) + 1) {
			return null;
		} else if (choice == (replacementWord.length - 1) + 2) {
			System.out.print("Enter your own choice: ");
			String resultWord = kb.next().toLowerCase();
			wordList.insert(resultWord);
			System.out.println("'" + resultWord + "' was inserted into dictionary.");
			kb.nextLine();
			return resultWord;
		}
		
		return replacementWord[choice];
	}

	/**
	 * Return all replacement words in the dictionary for w
	 */
	private String[] replacementWord(String word) {
		String[] replacementWords = new String[5];
		int counter = 0;
		
		for (int i = 0; i < word.length(); i++) {
			int alphabetSize = 'z' - 'a' + 1;
			for (int j = 0; j < alphabetSize; j++) {
				String suggestion = replaceCharAt(word, i, (char) ('a' + j));
				if (wordList.search(suggestion)) {
					if (counter >= 5)
						break;
					replacementWords[counter++] = suggestion;
				}
			}
		}
		return Arrays.copyOf(replacementWords, counter);
	}

	/**
	 * Replaces index-th character with new character
	 */
	private String replaceCharAt(String word, int index, char newChar) {
		String resultString = "";
		
		if (index != 0)
			resultString += word.substring(0, index);
		resultString += newChar + word.substring(index + 1);
		
		return resultString;
	}
	
	/**
	 * Read dictionary file and insert words into wordList object
	 */
	private void readDict(String filename) {
		try (Scanner fileScan = new Scanner(new File(filename))) {
			while (fileScan.hasNext())
				wordList.insert(fileScan.next());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
}
