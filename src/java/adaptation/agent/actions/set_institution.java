package adaptation.agent.actions;

import adaptation.agent.ANormativeAgent;
import adaptation.agent.ANormativeAgentSAI;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import sai.main.institution.SaiEngine;

/**
 * An internal action for add a new norm in the normative engine of the agent.
 */
public class set_institution extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        ANormativeAgentSAI ag = (ANormativeAgentSAI) ts.getAg();
        System.out.println(un);
        System.out.println(args[0]);
        SaiEngine institution = (SaiEngine) args[0];
        ag.getLogger().info("[Action] Set institution: " + institution);
        ag.setInstitution(institution);
        return true;
    }
}
