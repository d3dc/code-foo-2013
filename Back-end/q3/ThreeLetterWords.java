import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class ThreeLetterWords {

	public static final char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
    public String start;
    public String end;
    
    //Shared between all three letter puzzles
    private static Vector<String> dictionary = new Vector<String>();
    
    private Vector<String> steps = new Vector<String>();
    private Vector<String> marked = new Vector<String>();
	
    public void readDict(String file) {
    	try {
			Scanner fileScanner = new Scanner(new File(file));
			while (fileScanner.hasNextLine())
				dictionary.add(fileScanner.next().toLowerCase());
		} catch (FileNotFoundException e) {
			System.out.println("Could not open dictionary: " + file);
		}
    }
    
    public void reset() {
    	marked.clear();
    }
    
    public void getSteps() {
    	steps = findSteps(start);
    }
    
    //Check if a word can be directly transformed into the end word.
    //Swap each letter with the letter at that position in the end word.
    //Iterates multiple times in case the letters need to be changed in reverse order.
	public Vector<String> findSteps(String word) {
		char[] currWord = word.toCharArray();
		//Shadows global variable
		Vector<String> steps = new Vector<String>();
		steps.add(word);
		
		if (word.compareTo(end) == 0) return steps;
		
		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < word.length(); j++) {
				if (currWord[j] != end.charAt(j)) {
					currWord[j] = end.charAt(j);
					if (dictionary.contains(new String(currWord))) {
						steps.add(new String(currWord));
					} else currWord[j] = word.charAt(j);
				}
			}
		}
		//word can't be directly transformed into end
		if (new String(currWord).compareTo(end) != 0) {
			//Mark the word so its only tried once.
			marked.add(word);
			Vector<String> temp = findWord(new String(currWord));
			if (temp == null) steps = null;
			else steps.addAll(temp);
		}
		return steps;
	}
	
	
	//Tries every letter of the alphabet in every position.
	//If the word created is a valid dictionary word, see if it provides a path to end.
	//A valid path or nothing at all is returned.
	public Vector<String> findWord(String word) {
		//Shadows global variable
		Vector<String> steps = new Vector<String>();
		char[] currWord = word.toCharArray();
		
		for (int i = 0; i < word.length(); i++) {
			if (currWord[i] != end.charAt(i)) {
				for (char c : alphabet) {
					//If currWord[i] = c, its already been tried.
					if (currWord[i] != c) {
						currWord[i] = c;
						if (dictionary.contains(new String(currWord)) && !marked.contains(new String(currWord))) {
							Vector<String> temp = findSteps(new String(currWord));
	
							//If steps is empty or we found a shorter path, set our steps to the new path. The case of word == end has already been checked.
							if (temp != null) {
								if (steps.isEmpty() || temp.size() < steps.size()) steps = temp;
							}
						}
								
						currWord[i] = word.charAt(i);
					}
				}
			}
		}
		
		//word didn't find a path to end after trying every combination
		if (steps.size() == 1 || steps.isEmpty()) { 
			marked.add(word);
			return null;
		} else {
			for (String s : steps) {
				if (marked.contains(s)) marked.remove(s);
			}
		}
		return steps;		
	}	
	
	public void printSteps() {
		if (steps == null) System.out.println("No solution found.");
		else {
			//Iterate through all but the ending word
			for (int i = 0; i < steps.size()-1; i++) 
				System.out.print(steps.get(i) + " -> ");
			//Print the last word and number of moves
			System.out.println(steps.lastElement() + ". " + (steps.size()-1) + " moves");
		}
	}

	public static void main(String[] args) {
        ThreeLetterWords w = new ThreeLetterWords();
		
        w.readDict("three-letter-words.txt");
        
		Scanner sc= new Scanner(System.in);
        
        while(true){
            System.out.println("-------MENU---------");
            System.out.println("1.  Load new dictionary file");
            System.out.println("2.  Set start word");
            System.out.println("3.  Set end word");
            System.out.println("4.  Print Steps");
            System.out.println("0.  Quit  \n");
            System.out.print("  Select an option: ");
            int option = sc.nextInt();
            sc.nextLine();
            switch(option){
            case 0: System.exit(0);
            break;
            case 1: System.out.print("Enter dictionary filename: ");
            	String s = sc.nextLine();
            	w.readDict(s);
            	w.reset();
        	break;
            case 2: System.out.print("Enter start word: ");
            	w.start = sc.nextLine().toLowerCase();
            	w.reset();
        	break;
            case 3: System.out.print("Enter end word: ");
        		w.end = sc.nextLine().toLowerCase();
        		w.reset();
        	break;
            case 4: 
            	if (w.start == null || w.end == null) System.out.println("Set the start and end words first.");
            	else {
            		w.getSteps();
            	   	w.printSteps();
            	}
        	break;
            default: 
            	System.out.println("Invalid Command");
        	break;
            }
            System.out.println();
        }
	}
	
}
