import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
/**
 * Read the user input or input file and run the spell-checking interface
 * @author Jordan Menter
 * @version 26 November 2015
 */
public class SpellChecker {
	
	/**
	 * Make input lower-case and remove symbols, punctuation, numbers, etc.
	 * @param input
	 * @return a string with lower-case letters only
	 */
	public static String removeSym(String input) {
		String lower = input.toLowerCase();
    	String lettersOnly = lower.replaceAll("[^A-Za-z]", "");
		return lettersOnly;
	}
	
	/**
	 * Check spelling and get near-misses for standard file input.
	 * @param inputs, the words contained in the file
	 * @param dict, the dictionary
	 */
	public static void getFileSuggestions(ArrayList<String> inputs, HashSet<String> dict) {
		// Remove any duplicate input words using a Set structure
        HashSet<String> set = new HashSet<String>(inputs);

        // Check if word is spelled correctly, or find spelling suggestions
        for(String token : set) {
            StringBuilder word = new StringBuilder(token);
            System.out.println("word is " + word);
			
            // Only print to command-line if word is spelled incorrectly, or if no suggestions are found.
            if(!dict.contains(word.toString())) {
                HashSet<String> suggestions = SpellDictionary.getSuggestions(word, dict);
                System.out.print("'" + word + "'" + " is spelled incorrectly. Suggestions: ");
                if(!suggestions.isEmpty()) {
                    for(String out : suggestions) {
                        System.out.print(" '" + out + "' ");
                    }
                } else {
                    System.out.print("There are no suggestions available.");
                }
            }
        }
	}
	
	/**
	 * Check spelling and get near-misses for user input.
	 * @param inputs, the words entered by the user
	 * @param dict, the dictionary
	 */
	public static void getUserSuggestions(ArrayList<String> inputs, HashSet<String> dict) {
		// Remove any duplicate input words using a Set structure
        HashSet<String> set = new HashSet<String>(inputs);

        // Check if word is spelled correctly, or find spelling suggestions
        for(String token : set) {
            StringBuilder word = new StringBuilder(token);
			
            // Print to command-line if word is spelled correctly or incorrectly, or there are no suggestions.
            if(dict.contains(word.toString())) {
                System.out.println("'" + word + "'" +  " is spelled correctly.");
            } else {
                HashSet<String> suggestions = SpellDictionary.getSuggestions(word, dict);
                System.out.print("'" + word + "'"  + " is spelled incorrectly. Suggestions: ");
                if(!suggestions.isEmpty()) {
                    for(String out : suggestions) {
                        System.out.print(" '" + out + "' ");
                    }
                } else {
                    System.out.print("There are no suggestions available.");
                }
            }
        }
	}
		
	
    public static void main(String[] args) {
        /** Construct the dictionary */
        SpellDictionary dict = new SpellDictionary("/usr/share/dict/words");
	
        ArrayList<String> inputs = new ArrayList<String>();

        /** Standard file input */
        if(args.length == 0) {
            Scanner sc = new Scanner(System.in);
            // Make all words lower-case and remove symbols, punctuation, etc.
            while(sc.hasNext()) {
                String next = sc.next();
                String lettersOnly = removeSym(next);
        		inputs.add(lettersOnly);
            }
            sc.close();
            
            // Check if each word is correctly spelled and generate suggestions for misspellings 
            getFileSuggestions(inputs, dict);
            
        } else {
            /** User input */
            for(String input : args) {
            	String lettersOnly = removeSym(input);
                inputs.add(lettersOnly);
            }
            
            // Check if each word is correctly spelled and generate suggestions for misspellings
            getUserSuggestions(inputs, dict);
        }
    }
}
