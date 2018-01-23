import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SpellCheck {
	WordList wordList = new WordList();

	/**
	 * Main function
	 */
	public static void main(String[] args) {
		SpellCheck spellCheck = new SpellCheck();
		final String FILENAME = "myDict.dat";
		
		spellCheck.readDict("..\\" + FILENAME);
		
		System.out.println("Type the name of a document she wants spell-checked: ");
		String filename;
		try (Scanner kb = new Scanner(System.in)) {
			filename = kb.nextLine();
		}
		spellCheck.spellChecking("..\\" + filename);
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
				if (wordList.search(word.toLowerCase()))
					pw.print(word + " ");
				else
					pw.print("=" + word + "= ");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
