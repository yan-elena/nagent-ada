!start.

+!start : true
    <-  .print("started");
        .send(carlos, tell, tellVl(10));
        //.send(carlos, achieve, achieveVl(10));
        .send(carlos, signal, signalVl(10));
        .

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }