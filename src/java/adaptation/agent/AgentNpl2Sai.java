package adaptation.agent;

import jason.asSemantics.Agent;
import jason.asSyntax.Atom;
import jason.asSyntax.Literal;
import npl.NPLInterpreter;
import sai.main.lang.semantics.statusFunction.AgentStatusFunction;
import sai.main.lang.semantics.statusFunction.EventStatusFunction;
import sai.main.lang.semantics.statusFunction.StateStatusFunction;
import sai.norms.npl.npl2sai.Npl2Sai;

public class AgentNpl2Sai extends Npl2Sai {

    private final Agent agent;
    private boolean produceAddBelEvents = false;

    public AgentNpl2Sai(NPLInterpreter nengine, Agent agent) {
        super(nengine);
        //this.nengine.setProduceAddBelEvents(true);
        this.agent = agent;
    }

    //todo: controllare se abbiamo bisogno di questi metodi, sembra no, tutto è gistito in nengine in npl2sai
//    public void setProduceAddBelEvents(boolean b) {
//        this.produceAddBelEvents = b;
//    }
//
//    protected boolean addAgBel(Literal l) {
//        try {
//            return this.produceAddBelEvents ? this.agent.addBel(l) : this.agent.getBB().add(l);
//        } catch (Exception var3) {
//            throw new RuntimeException(var3);
//        }
//    }

    public void addFact(Literal l) {
        this.nengine.addFact(l);
    }

    @Override
    public void addAgentAssignment(String arg0, AgentStatusFunction arg1) {
        super.addAgentAssignment(arg0, arg1);
        addFact(Literal.parseLiteral("sai__is("+arg0.toString()+","+arg1.toString()+")"));
    }

    @Override
    public void addEventAssignment(String arg0, EventStatusFunction arg1, Atom arg2) {
        super.addEventAssignment(arg0, arg1, arg2);
        addFact(Literal.parseLiteral("sai__event("+arg1.toString()+"[sai__agent("+arg2.toString()+")])"));
    }

    @Override
    public void addStateAssignment(String arg0, StateStatusFunction arg1) {
        super.addStateAssignment(arg0, arg1);
        this.addFact(Literal.parseLiteral(arg1.toString()));
    }

    @Override
    public void updateState() {
        super.updateState();
    }
}
