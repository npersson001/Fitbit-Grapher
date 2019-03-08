# Fitbit-Grapher User Study

Updated: March 8th 2019

---------------------------------------

Please complete the following tasks over the course of your time participating in this user study.  Complete as many as possible, in whatever order makes the most sense to you.  

## Task 1

For the first task in this study you will be responsible for taking input from a Fitbit file containing **JSONObject**s.  This will require you to parse the Fitbit file using a **JSONParser** object (documentation available [here](https://jar-download.com/artifacts/com.github.cliftonlabs/json-simple/2.1.2/documentation)) or any other parsing method of your choice.  You can store the **jSONObject**s in some type of data structure, most likely a **List**.  

The Fitbit files will be provided for you and will look similar to this: heart_rate-2019-01-17.json, heart_rate-2019-01-19.json, etc.  For this first task choose one of the files provided to parse. 

---------------------------------------

## Task 2

For the second task you must take the **JSONObject**s that you parsed in the previous task and graph them using AWT/Swing (documentation avaiable [here](https://docs.oracle.com/javase/7/docs/api/java/awt/package-summary.html) and [here](https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html)).  Swing is newer and more robust, however, either are acceptable.  

The final product should in some way graph the change in time of the heart rate of the user.  An example is shown below.  Notice that because of the small change in time between each datapoint, each pixel to the right displays a new heart rate, so it need not be to scalse. 
![Heart Rate Example Chart](https://drive.google.com/open?id=10jxyL_KpUcI-YpXJmQma8qjgKgEE4oi6)
