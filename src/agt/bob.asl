unfulfilled_count(0).

!start.

+!start
    <-  !manage_clock;
        .print("started").


+active(O)
    <-  .print("active obligation: ", O)
        .

+fulfilled(O)
    <-  .print("fulfilled obligation: ", O).

+unfulfilled(O) : state_sf(detect(unfulfilled_count)) & unfulfilled_count(C)
    <-  +unfulfilled_count(C + 1);
        .print("unfulfilled obligation: ", O, " count:", C+1);
        .

+What[source(self)] : state_sf(detect(Test))[artifact_id(ArtSai)] & focusing(ArtSai,inst_test_art,_,_,inst_test,_)
    <-  .print("What: ", What, " test: ", Test).

//adaptation
+unfulfilled_count(X) : state_sf(detect(unfulfilled_count))
    <-  .print("DETECTED: ", X).

+!unfulfilled_count
    <-  .print("Achieve unfulfilled count??").

+state_sf(detect(X))
    <-  !X.

// status functions from SAI
+state_sf(SF)
    <-  .print("status function: ", SF).


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