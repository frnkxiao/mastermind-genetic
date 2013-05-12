
import java.util.Random;
import java.util.Vector;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.StoppingCondition;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */
public class MMGeneticAlgorithm {
    
    GeneticAlgorithm ga;
    ElitisticListPopulation population;
   // int[] colorFitness = new int[MastermindModel.NUMOFCOLORS];
    // int[][] positionalFitness = new int[MastermindModel.GUESSCOLORS][MastermindModel.NUMOFCOLORS];
    int [] guess = new int[MastermindModel.GUESSCOLORS];
    MMChromosome firstChromosome;
    Random r = new Random ();
    
    public void init (){
       /* for (int i = 0; i < MastermindModel.NUMOFCOLORS; i++)
            colorFitness[i]=1;
        
        for (int i = 0; i < MastermindModel.NUMOFCOLORS; i++){
            for (int j = 0; j < MastermindModel.GUESSCOLORS; j++){
              positionalFitness[i][j] = 1;
            }
        }
        */
        population = new ElitisticListPopulation(500,.33);
        
        for (int i = 0; i< MastermindModel.GUESSCOLORS;i++)
            guess[i]=i;
        
        firstChromosome = new MMChromosome (guess);
        population.addChromosome(firstChromosome);
        
        for (int i = 0; i < 100; i++){
            int [] encoding = new int[MastermindModel.GUESSCOLORS];
            for (int j = 0; j< MastermindModel.GUESSCOLORS;j++)
               encoding[j]=r.nextInt(MastermindModel.NUMOFCOLORS);
            population.addChromosome(new MMChromosome(encoding));
        }
        
    }
    
    int [] getFirstGuess(){
        return guess;
    }
    
    int [] getNextGuess(){
       // int [] rGuess = new int[MastermindModel.GUESSCOLORS];
       // System.arraycopy(guess, 0, rGuess, 0, MastermindModel.GUESSCOLORS);
        guess = evolve().getEncoding();
        
        return guess;
    }
    
    public void update(Vector <Integer> pcolors, Vector <Integer> evals ){
        
    }
    
    MMChromosome evolve (){
        //StoppingCondition stopCond = new MMStoppingCondition();
      
        ElitisticListPopulation finalPopulation = (ElitisticListPopulation) ga.evolve(population, new FixedGenerationCount(1000));
        population = finalPopulation;
      
        MMChromosome bestFinal = (MMChromosome)finalPopulation.getFittestChromosome();
        return bestFinal;
      
        
    }
    
    
    
    
    
    
    
    public MMGeneticAlgorithm (){
         init();
         ga = new GeneticAlgorithm(
         new MMCrossoverPolicy(),
                    0.2,
                   new MMMutationPolicy(),
                   0.90,
               new MMSelectionPolicy()
                );

        
        
    }
    
}
