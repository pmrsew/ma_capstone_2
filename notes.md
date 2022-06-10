## Start Here
1. Set up Postgres database using database/tenmo.sql
2. User registration and authentication functionality is implemented
   * After successful authentication, an instance of 
   AuthenticatedUser is stored in the currentUser variable of App
   * User's authorization token (meaning JWT) can be accessed from
   App as currentUser.getToken()
   * When use cases refer to an "authenticated user", this means a
   request that includes the token as a header
   * Reference other information about the current user by using
   the User object retrieved from currentUser.getUser()
   * ***This means use cases 1 and 2 don't need to be addressed by us***
## Now to break down what we know about required use cases
3. Use Case 3 has a method predefined in App (viewCurrentBalance())
4. Use Case 4 has a method predefined in App (sendBucks())
   * This is going to be a series of displaying information and
   receiving input from the user
   * Multiple input checks to be included
5. Use Case 5 has a method predefined in App (viewTransferHistory())
6. Use Case 6 doesn't have a predefined method because it isn't accessed
in the main menu. The sample screens show it appearing as an option after
viewing transfers

***We are gonna hold on the optional use cases for now, focusing on them 
will make the picture to big.***
## What needs done?
Looking at the database schema, tenmo_user table is already implemented 
for both client and server

account table has balance information

transfer table has all transfers, linked to user via account id
### Client Side

`tenmo-client/.../model/[Table Name].java`  
provides model for data use in Service that will need to be made

`tenmo-client/.../services/[Service Name].java`  
Methods that interact with API in the Service made will be used  
in App.java (our use cases)
### Server side

`tenmo-server/.../model/[Table Name].java`  
provides model for data use in dao and controller

`tenmo-server/.../dao/[Table Name]Dao.java`  
provides methods to access database to be used in controller

`tenmo-server/.../controller/[Controller Name].java`  
the api, used by the service in the client
##How to go about it?
* Start with making all the model skeletons
* Move onto DAO skeleton, this will help us establish the main functions needed
for accessing database information (use cases as reference)
* From there, Controller skeleton to establish URL path names
* With the URL path names done, Service skeleton can be created

Start filling in skeletons and App.java methods, can be systematic or random.

## Server Side

-User Ids of from and to users, amount of te bucks
-receiver's account balance is increased
-sender's account balance is decreased



