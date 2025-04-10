package adaptation.agent;

import jason.asSemantics.CircumstanceListener;
import jason.asSemantics.Event;
import jason.asSyntax.Atom;
import jason.asSyntax.Literal;
import jason.asSyntax.Trigger;
import jason.mas2j.AgentParameters;
import jason.mas2j.ClassParameters;
import npl.NormativeAg;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import sai.bridges.jacamo.RuleEngine;
import sai.main.institution.SaiEngine;
import sai.main.lang.parser.sai_constitutiveLexer;
import sai.main.lang.parser.sai_constitutiveListenerImpl;
import sai.main.lang.parser.sai_constitutiveParser;
import util.NPLMonitor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

/**
 * A normative agent that has a NPL normative and SAI constitutive reasoning module integrated in its mind.
 */
public class NormativeAgentSAI extends NormativeAg implements CircumstanceListener {

    private AgentNpl2Sai npl2Sai;
    private RuleEngine saiRuleEngine;
    private SaiEngine saiEngine;

    @Override
    public void initAg() {
        super.initAg();

        this.saiRuleEngine = new RuleEngine();
        this.interpreter.setProduceAddBelEvents(true);
        this.npl2Sai = new AgentNpl2Sai(interpreter, this);
        this.saiEngine = new SaiEngine();
        this.saiRuleEngine.addInstitution(this.saiEngine);

        getTS().getC().addEventListener(this); // for listen the events from the circumstance that the agent perceives

        NPLMonitor gui = new NPLMonitor();
        try {
            gui.add("demo", interpreter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        ClassParameters agC = ((AgentParameters)this.getTS().getSettings().getUserParameters().get("project-parameter")).agClass;
        try {
            if (!agC.getParameters().isEmpty()) {
                Iterator<String> iterator = agC.getParameters().iterator();
                String nplFileName = iterator.next();
                nplFileName = nplFileName.substring(1, nplFileName.length() - 1);
                this.logger.info("*** loading regulative norms from " + nplFileName);

                String saiFileName = iterator.next();
                saiFileName = saiFileName.substring(1, saiFileName.length() - 1);
                this.logger.info("*** loading constitutive norms from " + saiFileName);

                this.loadSAIProgram(saiFileName);


                System.out.println(saiEngine.getProgram().getConstitutiveRules());

                saiEngine.addNormativeEngine(npl2Sai);
                this.npl2Sai.updateState();
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }



    @Override
    public int buf(Collection<Literal> percepts) {
//        int r = super.buf(percepts);

        this.npl2Sai.updateState();

        if (percepts != null) {
            System.out.println("BUF:  " + percepts);
        }
        return super.buf(percepts);
    }

    // Each change in the belief base generates an event
    @Override
    public void eventAdded(Event e) {
        Literal event = Literal.LFalse;
        //System.out.println("eventAdded:  " + e);
        Trigger trigger = e.getTrigger();
        if (trigger.getType().equals(Trigger.TEType.belief) && trigger.getOperator().equals(Trigger.TEOperator.add)) {

            event = trigger.getLiteral(); // a new belief in the agent is added as environmental event in sAI
        }


        if (!event.equals(Literal.LFalse)) {
            saiEngine.addEnvironmentalEvent(event, new Atom(getTS().getAgArch().getAgName()), LocalDateTime.now());
        }

    }

    private void loadSAIProgram(String fileName) throws IOException {
        CharStream input = CharStreams.fromFileName(fileName);
        sai_constitutiveLexer constLexer = new sai_constitutiveLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(constLexer);
        sai_constitutiveParser constParser = new sai_constitutiveParser(tokens);
        ParseTree tree = constParser.constitutive_spec();
        ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        sai_constitutiveListenerImpl constExtractor = new sai_constitutiveListenerImpl(saiEngine.getProgram());
        walker.walk(constExtractor, tree); // initiate walk of tree with listener
    }

}
