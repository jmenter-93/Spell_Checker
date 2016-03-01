import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
* Read in the dictionary file and handle checks for words and near misses.
* Near misses include deletions, insertions, substitutions, transpositions, and splits.
* @author Jordan Menter
* @version 26 November 2015
*/
public class SpellDictionary extends HashSet<String> {
	
	/** Default constructor */
	public SpellDictionary() {
		super(100000);
	}
	
	/** Construct the dictionary with buildDictionary method */
	public SpellDictionary(String filename) {
		this();
		buildDictionary(filename);
	}
	
	/**
	 * buildDictionary: build and return the dictionary
	 * @param String filename, your dictionary
	 * @return The dictionary, stored in a HashSet<String>
	 */
	public void buildDictionary(String filename){
		
		// Read in the file
		String fname = filename;
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(fname));
			String line;
			while ((line = file.readLine()) != null) {
				add(line);
			}
		} catch (IOException e) {
			System.err.println("Problem reading file " + fname);
			System.exit(-1);
		}
                System.out.println("Built dictionary");
	}
	
	/**
	 * Deletions: Compile any near-miss deletions
	 * @param StringBuilder word to test
	 * @param HashSet<String> set, the dictionary
	 * @return suggestions from deletions
	 */
	public static ArrayList<String> deletions(StringBuilder word, HashSet<String> set) {
		
		// ArrayList of suggestions
    	ArrayList<String> suggestions = new ArrayList<String>();
		
		// Deletions: Sequentially delete a character and test if word is contained in dictionary
		for(int i = 0; i < word.length() - 1; i++) {
			System.out.println("deletion: word length is " + word.length());
				
			// Modified copy of the word
			StringBuilder copy = new StringBuilder(word);
			StringBuilder modify = copy.deleteCharAt(i);
			System.out.println("modified word is " + modify);
				
			// If deletion is contained in dictionary and not already contained in suggestions
			if((set.contains(modify.toString())) && (!suggestions.contains(modify.toString()))) {
				System.out.println("modified word is " + modify.toString());
				// Add deletion to list of suggestions
				suggestions.add(modify.toString());
			}
		}
		return suggestions;
	}

	/**
	 * Insertions: compile any near-miss insertions
	 * @param StringBuilder word to test
	 * @param HashSet<String> set, the dictionary
	 * @return suggestions from insertions
	 */
    public static ArrayList<String> insertions(StringBuilder word, HashSet<String> set) { 
    	
    	// ArrayList of suggestions
    	ArrayList<String> suggestions = new ArrayList<String>();
    	
    	// Alphabet for insertions and substitutions
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
		char[] letters = alphabet.toCharArray();
		
    	for(int i = 0; i <= word.length(); i++) {
    		for(int j = 0; j < letters.length; j++) {
    			System.out.println("insertion: word length is " + word.length());
					
    			// Modified copy of the word
    			StringBuilder copy = new StringBuilder(word);
    			StringBuilder modify = copy.insert(i, letters[j]);
    			System.out.println("modified word is " + modify);
					
    			// If insertion is contained in dictionary and not already in suggestions
    			if((set.contains(modify.toString())) && (!suggestions.contains(modify.toString()))) {
    				System.out.println("modified word is " + modify.toString());
                    // Add insertion to list of suggestions
    				suggestions.add(modify.toString());
    			}
    		}
    	}
    	return suggestions;
    }
    
    /**
     * Substitutions: compile any near-miss substitutions
     * @param StringBuilder word to test
     * @param HashSet<String> set, the dictionary
     * @return suggestions from substitutions
     */
	public static ArrayList<String> substitutions(StringBuilder word, HashSet<String> set) {
		
		// ArrayList of suggestions
    	ArrayList<String> suggestions = new ArrayList<String>();
    	
    	// Alphabet for insertions and substitutions
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
		char[] letters = alphabet.toCharArray();
		
		for(int i = 0; i < word.length(); i++) {
			for(int j = 0; j < letters.length; j++) {
				System.out.println("substitution: word length is " + word.length());
					
				// Modified copy of the word
				StringBuilder copy = new StringBuilder(word);
				StringBuilder deleted = copy.deleteCharAt(i);
				StringBuilder modify = deleted.insert(i, letters[j]);
				System.out.println("modified word is " + modify);
					
				// If substitution is contained in dictionary and not already in suggestions
				if((set.contains(modify.toString())) && (!suggestions.contains(modify.toString()))) {
					// Add substitution to list of suggestions
					suggestions.add(modify.toString());
				}	
			}
		}
		return suggestions;
	}
	
	/**
	 * Transpositions: compile any near-miss transpositions (adjacent characters swapped)
	 * @param StringBuilder word to test
	 * @param HashSet<String> set, the dictionary
	 * @return suggestions from transpositions
	 */
	public static ArrayList<String> transpositions(StringBuilder word, HashSet<String> set) {
		
		// ArrayList of suggestions
    	ArrayList<String> suggestions = new ArrayList<String>();
		
		for(int i = 0; i < word.length() - 1; i++) {
			System.out.println("transposition: word length is " + word.length());
				
			// Modified copy of the word
			// StringBuilder copy = new StringBuilder(word);
			StringBuilder modify = new StringBuilder(word); // test this
			char one = modify.charAt(i);
			char two = modify.charAt(i+1);
			modify.setCharAt(i, two);
			modify.setCharAt(i+1, one);
			System.out.println("modified word is " + modify);
				
			// If transposition is contained in dictionary and not already in suggestions
			if((set.contains(modify.toString())) && (!suggestions.contains(modify.toString()))) {
				// add transposition to list of suggestions
				suggestions.add(word.toString());
			}
		}
		return suggestions;
	}
	
	/**
	 * Splits: compile any near-miss splits (word is split into two correctly-spelled words)
	 * @param StringBuilder word to test
	 * @param HashSet<String> set, the dictionary
	 * @return suggestions from splits
	 */
	public static ArrayList<String> splits(StringBuilder word, HashSet<String> set) {
		
		// ArrayList of suggestions
    	ArrayList<String> suggestions = new ArrayList<String>();
		
    	for(int i = 0; i < word.length() - 1; i++) {
    		System.out.println("split: word length is " + word.length());
				
    		// Modify the word
    		StringBuilder copy = new StringBuilder(word);
    		StringBuilder modify = new StringBuilder(copy);
    		String left = modify.substring(0, i);
    		String right = modify.substring(i, modify.length());
    		System.out.println("modified word is " + left + " " + right);
				
    		// If both splits are contained in dictionary
    		if((set.contains(left)) && (set.contains(right))) {
    			// and are not already in suggestions
    			if(!suggestions.contains(left + " " + right)) {
    				// add splits to list of suggestions
    				suggestions.add(left + " " + right);
    			}
    		}
    	}
    	return suggestions;
	}
	
	/**
	 * getSuggestions: aggregate all suggestions from all near-misses
	 * @param StringBuilder word to test
	 * @param HashSet<String> set, the dictionary
	 * @return HashSet of suggestions with no duplicates
	 */
	public static HashSet<String> getSuggestions(StringBuilder word, HashSet<String> set) {
		
		// Append all suggestions together
		ArrayList<String> allSuggestions = new ArrayList<String>();
		
		ArrayList<String> allDeletes = deletions(word, set);
		allSuggestions.addAll(allDeletes);
		ArrayList<String> allInserts = insertions(word, set);
		allSuggestions.addAll(allInserts);
		ArrayList<String> allSubs = substitutions(word, set);
		allSuggestions.addAll(allSubs);
		ArrayList<String> allTranspose = transpositions(word, set);
		allSuggestions.addAll(allTranspose);
		ArrayList<String> allSplits = splits(word, set);
		allSuggestions.addAll(allSplits);
		
		// Store in HashSet<String> to remove duplicates
		HashSet<String> suggestions = new HashSet<String>(allSuggestions);
		return suggestions;
	}
}
