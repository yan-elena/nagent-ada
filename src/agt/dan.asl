!start.

+!start : true
    <-  .print("started");
        .wait(3000);
        .print("send carlos tellVl(10)");
        .send(carlos, tell, tellVl(10));
        .wait(3000);
        .print("send carlos signalFine(10)");
        .send(carlos, signal, signalFine(10));
        //.wait(3000);
        //.print("send carlos achieveVl(10)");
        //.send(carlos, achieve, achieveVl(10));
        .

{ include("$jacamo/templates/common-cartago.asl") }
{ include("$jacamo/templates/common-moise.asl") }