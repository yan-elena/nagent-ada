!start.

+!start
    <-  .print("started").

+activated(O)
    <-  .print("activated obligation: ", O).

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