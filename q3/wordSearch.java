import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class wordSearch {
	private char[][] puzzle;
	private Vector<String> words;
	
	//These are identical to puzzle.length and puzzle[].length, once the puzzle is initialized.
	private int numrows = 0;
	private int numcols = 0;
	
	public wordSearch() {
		puzzle = null;
		words = null;
	}
	
	public wordSearch(int n, int m) {
		numrows = n;
		numcols = n;
		puzzle = new char[n][m];
		words = new Vector<String>();
	}
	
	public wordSearch(String filename) {
		readFile(filename);
	}
	
	public char at(int n, int m) {
		return puzzle[n][m];
	}
	
	public void set(int n, int m, char c) {
		puzzle[n][m] = c;
	}
	
	public void addWord(String word) {
		words.add(word);
	}
	
	/*
	 * Destroy the old puzzle and read a new one from a file
	 * Returns true on successful read.	
	 */
	public boolean readFile(String filename) {
		File f = new File(filename);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			
			Vector<String[]> rows = new Vector<String[]>();
			
			String line = null;
			
			while (!(line=reader.readLine()).isEmpty() && line != null) {
				
				String[] chars = line.split("\t");
				
				//If a row is longer than the others, the file is improperly formatted
				if (numcols == 0) numcols = chars.length;
				else if (chars.length != numcols) { reader.close(); return false; }
				
				numrows++;
				rows.add(chars);
			}
			
			//Reached end of file prematurely
			if (line == null) { reader.close(); return false; }
			
			puzzle = new char[numrows][numcols];
			
			int row = 0;
			for (String[] s : rows) {
				for (int col = 0; col < s.length; col++) {
					puzzle[row][col] = s[col].charAt(0);
				}
				row++;
			}
			
			//Read word list here
			words = new Vector<String>();
			
			//Consume the "Words to find:" line
			reader.readLine();
			
			while ((line=reader.readLine()) != null) {
				words.add(line);
			}
			
			reader.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void searchPuzzle(String word) {
	    int row = 0;
	    int col = 0;
	    //remove all letter cases, spaces, and punctuation for search
	    String searchWord = word.toLowerCase().replaceAll("\\W","");
	    
		while (row < numrows && col < numcols) {
	    	if (puzzle[row][col] == searchWord.charAt(0)) {
	    		if (searchHoriz(searchWord,row,col,1)) {
	    			System.out.println(word + " is at [" + row + ',' + col + "] going across");
	    			return;
	    		} else if (searchHoriz(searchWord,row,col,-1)) {
	    			System.out.println(word + " is at [" + row + ',' + col + "] going backwards across");
	    			return;
	    		} else if (searchVert(searchWord,row,col,1)) {
	    			System.out.println(word + " is at [" + row + ',' + col + "] going down");
	    			return;
	    		} else if (searchVert(searchWord,row,col,-1)) {
	    			System.out.println(word + " is at [" + row + ',' + col + "] going up");
	    			return;
	    		} else if (searchDiagonal(searchWord,row,col,1,1)) {
	    			System.out.println(word + " is at [" + row + ',' + col + "] going diagonally down");
	    			return;
	    		} else if (searchDiagonal(searchWord,row,col,-1,1)) {
	    			System.out.println(word + " is at [" + row + ',' + col + "] going diagonally up");
	    			return;
	    		} else if (searchDiagonal(searchWord,row,col,1,-1)) {
	    			System.out.println(word + " is at [" + row + ',' + col + "] going backwards diagonally down");
	    			return;
	    		} else if (searchDiagonal(searchWord,row,col,-1,-1)) {
	    			System.out.println(word + " is at [" + row + ',' + col + "] going backwards diagonally up");
	    			return;
	    		}
	    	}
	    	col++;
	    	if (col == numcols) { col = 0; row++; }
	    }
		System.out.println(word + " was not found in puzzle.");
	}
	
	//Checks if a word with its first letter at [r,c] goes vertically.
	//Direction determines whether the row is incremented or decremented.
	//The first letter is already located, offset = 1.
	public boolean searchVert(String word, int r, int c, int direction) {
		return searchVert(word,r,c,direction,1);
	}
	public boolean searchVert(String word, int r, int c, int dir, int offset) {
		if (offset == word.length()) return true;
		
		int newrow = r+offset*dir;
		if (newrow == numrows || newrow < 0) return false;
		
		if (puzzle[newrow][c] == word.charAt(offset)) return searchVert(word,r,c,dir,offset+1);
		else return false;
	}

	//Checks if a word with its first letter at [r,c] goes horizontally.
	//Direction determines whether the column is incremented or decremented.
	//The first letter is already located, offset = 1.
	public boolean searchHoriz(String word, int r, int c, int direction) {
		return searchHoriz(word,r,c,direction,1);
	}
	public boolean searchHoriz(String word, int r, int c, int dir, int offset) {		
		if (offset == word.length()) return true;
		
		int newcol = c+offset*dir; 
		if (newcol == numcols || newcol < 0) return false;
		
		if (puzzle[r][newcol] == word.charAt(offset)) return searchHoriz(word,r,c,dir,offset+1);
		else return false;
	}
	
	//Checks if a word with its first letter at [r,c] goes diagonal.
	//Direction determines whether the row is incremented or decremented.
	//The first letter is already located, offset = 1.
	public boolean searchDiagonal(String word, int r, int c, int rowDir, int colDir) {
		return searchDiagonal(word,r,c,rowDir,colDir,1);
	}
	public boolean searchDiagonal(String word, int r, int c, int rowDir, int colDir, int offset) {		
		if (offset == word.length()) return true;
		
		int newcol = c+offset*colDir;
		int newrow = r+offset*rowDir;
		if (newcol == numcols || newrow == numrows || newrow < 0 || newcol < 0) return false;
		
		if (puzzle[newrow][newcol] == word.charAt(offset)) return searchDiagonal(word,r,c,rowDir,colDir,offset+1);
		else return false;
	}
	
	public void printPuzzle() {
		for (int row = 0; row < numrows; row++) {
			for (int col = 0; col < numcols; col++) {
				System.out.print(puzzle[row][col] + "\t");
			}
			System.out.println();
		}
	}
	
	public void printWords() {
		for (String s : words) {
			System.out.println(s);
		}
	}
	
	public void printAnswers() {
		for (String w : words) {
			searchPuzzle(w);
		}
	}
}
