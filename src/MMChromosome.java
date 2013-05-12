
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.apache.commons.math3.genetics.Chromosome;


public class MMChromosome extends Chromosome{
    
    int [] encoding;
    
    public MMChromosome (int [] encoding){
        this.encoding = encoding;
    }
    
    int [] getEncoding (){
        return encoding;
    }
    
   double evaluate (List <Integer> scolors) {
        
        
        System.out.println(encoding.length + " " + scolors.size());
       
	int numExactMatches = 0;
	int numColorMatches = 0;
	
	Vector<Integer> unmatchedGuess= new Vector<Integer>();
	Vector<Integer> unmatchedSecret= new Vector<Integer>();
			
	
	for (int i = 0; i< MastermindModel.GUESSCOLORS;i++){
            if ( encoding[i] !=(scolors.get(i))){
		unmatchedGuess.add(encoding[i]);
		unmatchedSecret.add(scolors.get(i));
	
            }
            else 
                numExactMatches++;
            
        }
			
	for (int i = 0; i < unmatchedGuess.size(); i++){
            int tmp = unmatchedGuess.get(i);
            for (int j = 0; j < unmatchedSecret.size(); j++){
		if (unmatchedSecret.get(j).equals(tmp)){
                    unmatchedGuess.remove(i);
                    unmatchedSecret.remove(j);
                    i--;
                    numColorMatches++;
                    break;
		}
            }
	}
        return V(numExactMatches, numColorMatches);
        
    }
    
    private double V (int pos, int col){
        return (1.1*pos + col)/4.4;
    }
    
    @Override
    public double fitness() {
        int sum=0;
        //Integer [] pcolorsArr = new Integer[MastermindModel.GUESSCOLORS];
        
        int numGuesses = MastermindModel.pcolors.size() / MastermindModel.GUESSCOLORS;
        for (int i = 0; i<numGuesses; i++){
            sum +=V(MastermindModel.evals.get(2*i), MastermindModel.evals.get(2*i+1))* evaluate(MastermindModel.pcolors.subList(4*i,i+4));
        }
            
       return sum  / numGuesses;
        
    }
    
}
