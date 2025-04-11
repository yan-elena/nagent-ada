!start.

+!start : true
    <-  .print("started");
        !manage_clock;
        +b;
        .

/*
+Instance[source(npli)]
    <-  .print("NPL FACT: ", Instance) .
*/

+Constitutive[source(np)]
    <-  .print("SAI FACT: ", Constitutive) .


+!manage_clock : focusing(Clock,clock,_,_,_,_)
    <-  setFrequency(1);
        start;
        .

+!manage_clock
    <-  .print("waiting for sai");
        .wait(focusing(Clock,clock,_,_,_,_));
        !manage_clock
        .

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }

// uncomment the include below to have an agent compliant with its organisation
//{ include("$moise/asl/org-obedient.asl") }
