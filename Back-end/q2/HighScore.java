import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;


public class HighScore {
	Vector<playerScore> table = new Vector<playerScore>();
	
	public class playerScore implements Comparable<playerScore> {
		public String name;
		public float score;
		
		//Highscore tables are usually descending order.
		//Switching this flips the order.
		public int compareTo(playerScore other) {
			return (int) (other.score-score);
		}
	}
	
	/*
	 * args: filename - the location of a highscore file with proper format
	 */
	
	public static void main(String[] args) {
		//NEW HIGHSCORE!!!
		HighScore h = new HighScore();
		
		if (args.length > 0) h.readScoreFile(args[0]);
		else h.readScoreFile("highscores.txt");
		
		h.sortScores();
		h.printScores();
	}
	
	public void sortScores() {
		quickSort(0, table.size()-1);
	}
	
	public void quickSort(int left, int right) {
		if (left < right) {
			//Not worst-case not best case
			int pivot = (left+right)/2;
			pivot = partition(left,right,pivot);
			quickSort(left,pivot-1);
			quickSort(pivot+1,right);
		}
	}
	
	public int partition(int left, int right, int pivot) {
		playerScore pivotValue = table.get(pivot);

		swap(pivot, right);
		
		int j = left;
		for (int i = left; i < right; i++) {
			if (table.get(i).compareTo(pivotValue) < 0) {
				swap(i,j);
				j++;
			}
		}
		swap(j,right);
		return j;
	}
	
	public void swap(int i, int j) {
		playerScore one = table.get(i);
		playerScore two = table.get(j);
		//this isn't an array
		table.remove(one);
		table.add(j,one);
		table.remove(two);
		table.add(i,two);
	}
	
	public void printScores() {
		for (playerScore p: table) {
			System.out.println(p.name + " - " + p.score);
		}
	}
	
	//Reads a highscore file into the highscore table.
	//A valid file has the format:
	//    Each score is on its own line with a name and a score separated by a space.
	public boolean readScoreFile(String filename) {
		try {
			Scanner fileScanner = new Scanner(new File(filename));
			
			while (fileScanner.hasNextLine()) {
				playerScore temp = new playerScore();
				
				temp.score = fileScanner.nextFloat();
				temp.name = fileScanner.next();
				
				table.add(temp);
			}
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}

}
