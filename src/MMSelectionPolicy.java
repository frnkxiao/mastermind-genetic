
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author D
 */
public class MMSelectionPolicy implements SelectionPolicy{

    @Override
    public ChromosomePair select(Population pltn) throws MathIllegalArgumentException {
        MMChromosome chromosome = (MMChromosome)pltn.getFittestChromosome();
        
        return new ChromosomePair (chromosome, chromosome);
    }
    
}
