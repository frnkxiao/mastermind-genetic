
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.apache.commons.math3.genetics.Chromosome;


public class MMChromosome extends Chromosome{
    
    int [] encoding;
    
    public MMChromosome (int [] encoding){
        this.encoding = encoding;
       // System.out.println(this);
    }
    
    int [] getEncoding (){
        return encoding;
    }
    
   double evaluate (List <Integer> scolors) {
        
        
       // System.out.println(encoding.length + " " + scolors.size());
       
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
        double r =  (1.1*pos + col)/4.4;
        
        return r;
    }
    
    @Override
    public double fitness() {
        double fitness;
        double sum=0;
        //Integer [] pcolorsArr = new Integer[MastermindModel.GUESSCOLORS];
        
        int numGuesses = MastermindModel.pcolors.size() / MastermindModel.GUESSCOLORS;
        //System.out.println("Number of guesses = "+ numGuesses + " "+  MastermindModel.pcolors.size());
        for (int i = 0; i<numGuesses; i++){
            sum +=V(MastermindModel.evals.get(2*i), MastermindModel.evals.get(2*i+1))* evaluate(MastermindModel.pcolors.subList(4*i,4*i+4));
        }
       
       fitness = sum*1.00 / numGuesses;
       System.out.println(this + " fitness = " + sum + " / " + numGuesses + " = " + fitness);
       return fitness;
        
    }
    
    public String toString (){
        String s = "";
        for (int i=0; i<encoding.length; i++)
            s = s+encoding[i];
        return s;
    }
    
}
