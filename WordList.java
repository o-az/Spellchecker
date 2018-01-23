import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import SeparateChainHashing;

public class WordList {
	private SeparateChainHashing<String, String> hashTable = new SeparateChainHashing<String, String>();
	
	/**
	 * This method returns true if myWord is found in the hash table; otherwise it returns false.
	 */
	public boolean search(String myWord) {
		return hashTable.contains(myWord);
	}
	
	/**
	 * This method inserts myWord into the hash table if it is not already in it.
	 */
	public void insert(String myWord) {
		hashTable.put(myWord, myWord);
	}
	
	/**
	 * This method returns the number of words in the hash table and it should run in constant time.
	 */
	public int numberWords() {
		return hashTable.size();
	}
	
	/**
	 * This method prints out all the words in the hash table in lexicographic order in a nice readable format.
	 * This will be used for testing only.
	 */
	public void sortedPrint() {
		List<String> keys = (List<String>) hashTable.keys();
		Collections.sort(keys);
		for (String s : keys) {
			System.out.println(s);
		}
	}
	
	/**
	 * This method is the same as the above method except that it writes into a file with the given fileName.
	 */
	public void sortedPrint(String fileName) {
		List<String> keys = (List<String>) hashTable.keys();
		Collections.sort(keys);
		try (PrintWriter pw = new PrintWriter(fileName)) {
			for (String s : keys) {
				pw.println(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
