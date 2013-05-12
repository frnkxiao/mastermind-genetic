
import java.util.Arrays;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.lang3.ArrayUtils;


/**
 *
 * @author D
 */
public class MMCrossoverPolicy implements CrossoverPolicy {

    @Override
    public ChromosomePair crossover(Chromosome chrmsm, Chromosome chrmsm1) throws MathIllegalArgumentException {
        MMChromosome chromosome1 = (MMChromosome)chrmsm;
        MMChromosome chromosome2 = (MMChromosome)chrmsm1;
        
        int [] c1Arr = chromosome1.getEncoding();
        int [] c2Arr = chromosome2.getEncoding();
        
        int [] c1ArrA = Arrays.copyOfRange(c1Arr, 0, c1Arr.length/2);
        int [] c1ArrB = Arrays.copyOfRange(c1Arr, c1Arr.length/2, c1Arr.length);
        
        int [] c2ArrA = Arrays.copyOfRange(c2Arr, 0, c2Arr.length/2);
        int [] c2ArrB = Arrays.copyOfRange(c2Arr, c2Arr.length/2, c2Arr.length);
        
        return new ChromosomePair (
            new MMChromosome (ArrayUtils.addAll(c1ArrA,c2ArrB)),
            new MMChromosome (ArrayUtils.addAll(c2ArrA,c1ArrB)));
    }
    
}
