package adaptation.agent;

import jason.architecture.AgArch;
import jason.asSyntax.Literal;

import java.util.Collection;

public class NormativeAgentSAIArch extends AgArch {

    @Override
    public Collection<Literal> perceive() {
        Collection<Literal> res = super.perceive();

        if (res != null) {
            System.out.println("*** PERCEIVE: " + res);
        }

        return res;
    }
}
