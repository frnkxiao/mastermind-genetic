
import java.util.Random;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.random.RandomGenerator;


public class MMMutationPolicy implements MutationPolicy{
    Random r = new Random();
    @Override
    public Chromosome mutate(Chromosome chrmsm) throws MathIllegalArgumentException {
        
        ((MMChromosome)chrmsm).getEncoding()[r.nextInt(MastermindModel.GUESSCOLORS)] = r.nextInt(MastermindModel.NUMOFCOLORS);
        return chrmsm;
    }
    
}
