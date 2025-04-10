package adaptation.agent;

import cartago.LINK;
import jason.asSyntax.Literal;
import npl.INorm;
import npl.NormativeAg;
import npl.NormativeProgram;
import npl.parser.ParseException;
import npl.parser.nplp;
import sai.main.institution.INormativeEngine;
import sai.main.institution.SaiEngine;
import sai.norms.npl.npl2sai.NormSai;
import sai.norms.npl.npl2sai.Npl2Sai;
import util.NPLMonitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A normative agent that has a NPL normative and SAI constitutive reasoning module integrated in its mind.
 */
public class NormativeAgentSAI extends NormativeAg {

    private Npl2Sai npl2sai;
    private SaiEngine institution;

    @Override
    public void initAg() {
        super.initAg();
        this.npl2sai = new Npl2Sai(interpreter);

        NPLMonitor gui = new NPLMonitor();
        try {
            gui.add("demo", interpreter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load(String nplProgram) throws Exception {
        NormativeProgram normativeProgram = new NormativeProgram();

        File f = new File(nplProgram);
        try {
            if (f.exists()) {
                new nplp(new FileReader(nplProgram)).program(normativeProgram, null);
            } else {
                new nplp(new StringReader(nplProgram)).program(normativeProgram, null);
            }
        } catch (FileNotFoundException e) {
        } catch (ParseException e) {
            logger.warning("error parsing \n"+nplProgram);
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Converts NPL norms to SAI norms
        /* The following piece of code is introduced in this artifact to convert Npl norms in SAI compliant NPL Nomrs */
        Iterator<INorm> it = normativeProgram.getRoot().getNorms().iterator(); //get the norms to be loaded in the NPL Interpreter
        List<String> toRemove = new ArrayList<String>();
        List<INorm> toAdd = new ArrayList<INorm>();
        int i=0;

        while(it.hasNext()) { // for each norm...
            INorm n = it.next();
            try {
                //create a SAI compliant norm
                NormSai nSai = new NormSai("nSai" + ++i, n.getConsequence(), n.getCondition(), institution.getProgram());
                for(Literal l:n.ifUnfulfilledSanction()) nSai.addUnfulfilledSanction(l);
                for(Literal l:n.ifInactiveSanction()) nSai.addInactiveSanction(l);
                for(Literal l:n.ifFulfilledSanction()) nSai.addFulfilledSanction(l);
                //remove the original norm from the NPL interpreter
                toRemove.add(n.getId());
                //replace the original norm by a SAI compliant one
                toAdd.add(nSai);
            } catch (jason.asSyntax.parser.ParseException e) {
                e.printStackTrace();
            }
        }


        for(String r:toRemove) {
            normativeProgram.getRoot().removeNorm(r);
        }

        for(INorm a:toAdd) {
            try {
                normativeProgram.getRoot().addNorm(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        interpreter.loadNP(normativeProgram.getRoot());

        getNormEngine().updateState();
    }

    public INormativeEngine getNormEngine() {
        return this.npl2sai;
    }

    // Link from SAI to NPL????
    public void setInstitution(SaiEngine institution){
        this.institution = institution;
        institution.addNormativeEngine(this.npl2sai);
    }

}
