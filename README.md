# Fitbit-Grapher User Study

Updated: March 8th 2019

---------------------------------------

Please complete the following tasks over the course of your time participating in this user study.  Complete as many as possible, in whatever order makes the most sense to you (though chronological is probably the easiest).  

## Part 1

### Task 1

For the first task in this study you will be responsible for taking input from a Fitbit file containing **JSONObject**s.  This will require you to parse the Fitbit file using a **JSONParser** object (documentation available [here](https://jar-download.com/artifacts/com.github.cliftonlabs/json-simple/2.1.2/documentation)) or any other parsing method of your choice.  You can store the **jSONObject**s in some type of data structure, most likely a **List**.  

The Fitbit files will be provided for you and will look similar to this: heart_rate-2019-01-17.json, heart_rate-2019-01-19.json, etc.  For this first task choose one of the files provided to parse. 

### Task 2

For the second task you must take the **JSONObject**s that you parsed in the previous task and graph them using AWT/Swing (documentation avaiable [here](https://docs.oracle.com/javase/7/docs/api/java/awt/package-summary.html) and [here](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html)).  Swing is newer and more robust, however, either are acceptable.  

The final product should in some way graph the change in time of the heart rate of the user.  An example is shown below.  Notice that because of the small change in time between each datapoint, each pixel to the right displays a new heart rate, so it need not be to scale. 
![Heart Rate Graph](https://user-images.githubusercontent.com/15836110/54060492-e6081780-41ca-11e9-92b9-3c9ddf985938.PNG)

---------------------------------------

## Part 2

The following tasks will revolves around distributing the local simulation you created in **Part 1**.

### Task 1

In this task you will create a **Socket** connection between a client and a server.  Luckily, Java's **java.net** package makes this relatively easy (documentation available [here](https://docs.oracle.com/javase/7/docs/api/java/net/package-summary.html)).  

To accomplish this, create a class for your client and your server.  The client object is to have access to the JSON file that was parsed in Part 1 Task 1.  For both the server and client create an object that will represent their connection to each other (ie. it will have the **Socket** connection).

### Task 2

Create an object that implements **Runnable** (documentation [here](https://docs.oracle.com/javase/7/docs/api/java/lang/Runnable.html)), so that you can create an accepting thread on the server side.  This thread will listen for a connection request from the client and create the object representing this connection when it receives the request.  This means you should make the server's connection object also implement **Runnable** and spawn a thread for it in the accepting thread.  

### Task 3

Create a thread on the server side that broadcasts the **JSONObject** received by the connection object.  In order to support this, create an object that implements runnable that takes **JSONObject**s off of a **LinkedBlockingQueue** (documentation [here](https://docs.oracle.com/javase/8/docs/api/?java/util/concurrent/LinkedBlockingQueue.html)) and sends them to the client in order.  Note that the server's connection object must now put all incoming **JSONObject**s on this **LinkedBlockingQueue** and both objects/threads must have access to this queue somehow.  




extra:


The third task involves distributing the simple simulation created in tasks 1 and 2.  You must accomplish this using sockets.  Luckily, Java's **java.net** package makes this relatively easy (documentation available [here](https://docs.oracle.com/javase/7/docs/api/java/net/package-summary.html)).  Your goal is to send each **JSONObject** you parsed in task 1 to your server.  The server must then echo the **JSONObject** back to the client.  The client will then graph the heart rate data as in task 2.
