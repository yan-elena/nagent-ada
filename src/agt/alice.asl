!start.

+!start
    <-  .print("started").


+!deliberate
    <-  .print("deliberate adaptation...");
        ID = "n2";
        O = obligation(bob,n2,apply_fine(A,X*E),false);
        C = extra(E);
        !execute(ID, O, C).

+!execute(ID, O, C)
    <-  .print("execute adaptation...");
        adaptation.agent.actions.add_norm(ID, O, C);
        .print("Added new norm: ", ID, " obligation: ", O, " consequence: ", C);

        .wait(3000);
        .print("test extra fact");
        +extra(20);
        .

+unfulfilled(O) : detect
    <-  .print("detected unfulfilled  ",O);
        .wait(2000);
        !deliberate;
        .

+active(O)
    <-  .print("active obligation: ", O);
        +detect.

+fulfilled(O)
    <-  .print("fulfilled obligation: ", O).

+unfulfilled(O)
    <-  .print("unfulfilled obligation: ", O).



//connect norms to institution
+!setup_sai: focusing(ArtSai,inst_test_art,_,_,inst_test,_) & focusing(NplArt,nb1,_,_,_,_)
    <-  getSaiEngine(SE)[artifact_id(ArtSai)];
        setInstitution(SE)[artifact_id(NplArt)];
        .print("connected: ", ArtSai, NplArt);
        .

+!setup_sai
    <-  .wait(focusing(ArtSai,inst_test_art,_,_,inst_test,_) & focusing(NplArt,nb1,_,_,_,_));
        !setup_sai.

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }