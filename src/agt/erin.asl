!start.

+!start
    <-  .print("started");
        .

+answer(A)
    <-  .print("answer ", A).

+Instance[source(npli)]
    <-  .print("NPL FACT: ", Instance) .

+Constitutive[source(np)]
    <-  .print("SAI FACT: ", Constitutive) .

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }