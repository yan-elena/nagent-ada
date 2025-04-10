unfulfilled_count(0).

!start.

+!start
    <-  //.print("setup sai");
        //!setup_sai; //todo: check connection
        !manage_clock;
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



//todo: connect norms to institution
+!setup_sai: focusing(ArtSai,inst_test_art,_,_,inst_test,_)
    <-  getSaiEngine(SE)[artifact_id(ArtSai)];
        .print("SAI Engine: ", SE);
        adaptation.agent.actions.set_institution(SE);
        .print("connected: ", ArtSai);
        .

+!setup_sai
    <-  .print("waiting for sai");
        .wait(focusing(ArtSai,inst_test_art,_,_,inst_test,_) & focusing(NplArt,nb1,_,_,_,_));
        !setup_sai.

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