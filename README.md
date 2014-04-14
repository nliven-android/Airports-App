Airports-App
=====================

A simple Android app that does the following:

1.  Makes a REST Request
2.  Parses the JSON response.
3.  Stores the data in a database.
4.  Queries the database and dumps output to the LOGCAT console.

It uses a couple of popular open-source libraries:
- GreenDAO ORM (for database/persistence functionality) 
   http://greendao-orm.com/
- Android-Async-Http (for abstracting away Http requests and handling a response) 
   http://loopj.com/android-async-http/
- Otto (A Publish/Subscribe event bus to help with decoupling)
   http://square.github.io/otto/
- GSON (Fast JSON Serialize/Deserializer)
   https://code.google.com/p/google-gson/

NOTE: 
This is intended to run with the "AirportsDaoGenerator" project, so be sure to import both these projects locally.