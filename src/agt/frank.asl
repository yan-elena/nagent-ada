!start.

+!start : .my_name(Me)
    <-  .print("started");
        .wait(2000);
        .send(erin, tell, started);
        +assigned(erin, complete(X), Me);
        .print("tell started to erin");
        .

// Adaptation Fact: detect
+detect(Me, What, How)
    <-  .print("ADAPT FACT: detect Who: ", Ag, " What: ", What, " How: ", How);
        !detect(Me, What, How).

// Adaptation Fact: design
+design(Who, What, How)
    <-  .print("ADAPT FACT: Design Who: ", Who, " What: ", What, " How: ", How );
        .

+!detect(Me, What, How) : How
    <-  .print("detected");
        +detected.

+!detect(Me, What, How) : assigned(Ag, _, Me)
    <-  .print("detect...");
        .send(Ag, askOne, How);
        .wait(2000);
        !detect(Me, What, How);
        .


+Instance[source(npli)]
    <-  .print("NPL FACT: ", Instance) .

+Constitutive[source(np)]
    <-  .print("SAI FACT: ", Constitutive) .

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }