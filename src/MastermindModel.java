import java.util.Vector;
import java.util.Random;

/* File   : MastermindModel.java
* Purpose: Implements the logic (model) for the game of mastermind.
**/
public class MastermindModel {
	//================================================================ constants
		public static final int CONTINUE = 0;		// player can pick 
		public static final int CHECK = 1;			// player need to click check button before further processing
		public static final int LOSE = 2;			// reach max number of guesses
		public static final int WIN = 3;			// player wins
		
	    public static final int GUESSCOLORS = 4;
	    public static final int NUMOFCOLORS = 6;
	    public static final int TIMESTOGUESS = 10;
		
		
		private Vector<Integer> pcolors;  			// array of picked up colors
		private Vector<Integer> evals; 				// evaluation results
		
		private int[] scolors = new int[GUESSCOLORS];
		private int gstatus;
		private int emptySquares;
		private int numPickedColors;
		private int numEvals;
		
		//============================================================== constructor
	    public MastermindModel() {
	    	
	    	pcolors = new Vector<Integer>();
	    	evals = new Vector<Integer>();
	    	
	    	reset();
	    	
	    }
		
		// add color index to array
		public void add(int cind){
			if (emptySquares > 0){
				pcolors.add(cind);
				emptySquares--;
			}			
		}
		
		// check game status
		public int getGameStatus(){			
			return gstatus;
		}
		
		// evaluate the game status
		public void checkGame(){
			
			if (emptySquares > 0)
				return;
			
			int numOfGuessColors = pcolors.size();					// total colors guessed
			int numOfGuess = numOfGuessColors/GUESSCOLORS;			// number of round played
			int numOfCurGuess = numOfGuessColors % GUESSCOLORS;		// number of colors guessed for the last round
			
			// Reach max guess rounds and did not match secret colors; loss
			if (numOfGuess == TIMESTOGUESS && !matchColors()){
				gstatus = LOSE;
				return;
			}
			
			if (numOfGuess > 0 && numOfCurGuess == 0){
				if (matchColors())
					gstatus = WIN;		// matches, win
				else 
					gstatus = CHECK;	// 
				
				return;
			}
			
			gstatus = CONTINUE;			
		}
		
		// match the colors with secret colors
		private boolean matchColors(){
		
			boolean result = true;
			int numExactMatches = 0;
			int numColorMatches = 0;
			
			Vector<Integer> unmatchedGuess= new Vector<Integer>();
			Vector<Integer> unmatchedSecret= new Vector<Integer>();
			
			int num = pcolors.size()-1;
			for (int ii = GUESSCOLORS-1; ii >= 0; ii--){
				if (! pcolors.get(num).equals(scolors[ii])){
					unmatchedGuess.add(pcolors.get(num));
					unmatchedSecret.add(scolors[ii]);
					
					result = false;
				}
				else 
					numExactMatches++;
				num--;
			}
			
			for (int ii = 0; ii < unmatchedGuess.size(); ii++){
				int tmp = unmatchedGuess.get(ii);
				
				for (int jj = 0; jj < unmatchedSecret.size(); jj++){
					if (unmatchedSecret.get(jj).equals(tmp)){
						unmatchedGuess.remove(ii);
						unmatchedSecret.remove(jj);
						ii--;
						
						numColorMatches++;
						break;
					}
				}
			}
			
			addEvals(numExactMatches,numColorMatches);
			
			return result;			
		}
		
		//
		private void addEvals(int exactMatch, int colorMatch){
			evals.add(exactMatch);
			evals.add(colorMatch);
		}
		// Start next round of guess
		public void setNextGuess(){
			emptySquares = GUESSCOLORS;
			gstatus = CONTINUE;
		}
		// undo a color selection
		public void undo(){
			int availSteps = GUESSCOLORS - emptySquares;
			if (availSteps > 0 && availSteps < GUESSCOLORS){
				pcolors.remove(pcolors.size()-1);
				emptySquares++;
			}
		}
		
		// reset game
		public void reset(){			
			gstatus = CONTINUE;
			emptySquares = GUESSCOLORS;
						
			if (pcolors.size()>0)
				pcolors.removeAllElements();
			if (evals.size()>0)
				evals.removeAllElements();
			
			getNewSecretColors();		
			setNextGuess();			
		}
		
		// generate new secret color codes
		private void getNewSecretColors(){
			Random randomGenerator = new Random();
			
			for (int ii = 0; ii < GUESSCOLORS; ii++){
				scolors[ii] = randomGenerator.nextInt(NUMOFCOLORS);
			}
		}
		
		//
		public void setPickedColors(){
			numPickedColors = 0;
			numEvals = 0;
		}
		
		//
		public int getNextColor(){
			int result = -1;
			if (numPickedColors < pcolors.size()){				
				result = pcolors.get(numPickedColors);
				numPickedColors++;
			}			
			return result;
		}
		//
		public int getNextEval(){
			int result  = -1;
			if (numEvals < evals.size()){
				result = evals.get(numEvals);
				numEvals++;
			}
			
			return result;
		}

}
