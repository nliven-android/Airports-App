Airports-App
=====================

A proof-of-concept Android app that does the following:

1.  Makes a REST Request via a Button press.
2.  Converts the JSON response string to Data Transfer Object(s) (DTO)
3.  Stores the DTO data in a local database.
4.  Uses Publish/Subscribe "Event Bus" to allow back-end to notify front-end when things have changed i.e. local Db has been updated so the UI can refresh itself.
5.  Queries the database and puts the LocalDb data into a ListView.
6.  Clicking on the ListView will open a 'Details View' UI.


It uses a couple of popular open-source libraries:
- GreenDAO ORM (for database/persistence functionality) 
   http://greendao-orm.com/
- Retrofit (a type-safe REST client.  Helps abstract away the messy HttpRequest details and handles a response) 
   http://square.github.io/retrofit/
- Otto (A Publish/Subscribe event bus to help with decoupling)
   http://square.github.io/otto/
- GSON (Fast JSON Serialize/Deserializer)
   https://code.google.com/p/google-gson/

NOTE: 
This is intended to run with the "AirportsDaoGenerator" project, so be sure to import both these projects locally.

===========
Change Log
===========
April 4 2014
 - Initial Checkin

May 17 2014 
 - Replaced async-http-client library with Retrofit. 
 - Added ListView UI and DetailsView UI.
 - Added DataSvc pattern.  AirportSvc is first to implement this pattern.
  