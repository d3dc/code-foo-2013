public class WordSearchSolver {

	/**
	 * WordSearchSolver file - Open a correctly formatted wordsearch file and print out each word and its location
	 * 
	 * A correct file has the format:
	 * 	
	 *  The first part of the file contains a matrix of N rows and M columns. 
	 *  Letters in the same row are separated by a tab (\t). Each row is separated by a newline.
	 *  
	 *  The list of words to find is below the matrix, delineated by a blank line followed by "Words to find:".
	 */
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: WordSearchSolver file");
		} else {
			wordSearch ws = new wordSearch(args[0]);
			ws.printAnswers();
		}
	}

}
