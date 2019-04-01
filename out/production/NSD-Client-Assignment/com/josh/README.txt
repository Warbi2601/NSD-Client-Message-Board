Week 13 Exercise 4: Message board server V4 (serialisation of requests)

FILES:

* ErrorResponse.java          representation of error responses
* json-simple-1.1.1.jar       JSON library
* LoginRequest.java           representation of request to login
* MessageBoardClientV4.java   client
* MessageBoardServerV4.java   server
* Message.java                representation of messages
* MessageListResponse.java    representation of responses to read request
* PostRequest.java            representation of request to post a message
* QuitRequest.java            representation of request to quit (client-s
* ReadRequest.java            representatoin of request to read messages
* Request.java                abstract super class of all responses
* README.txt                  this file
* Response.java               abstract super class of all responses
* SuccessResponse.java        representation of responses acknowledging success

COMPILE:
javac -cp json-simple-1.1.1.jar;. *.java

START SERVER on port 12345:
java -cp json-simple-1.1.1.jar;. MessageBoardServerV4 12345

START CLIENT on localhost:
java -cp json-simple-1.1.1.jar;. MessageBoardClientV4 localhost 12345
