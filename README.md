# LeoVegasChanllege

# Getting Started

## import project
this project uses Gradle to handle dependencies, Spring boot and Spring Data JPA, the database is h2(no password required for macOS, the data will persist across restarts and it is stored in a local repository) in-memory DB, and written in IntelliJ. To import it, import build.gradle file in IntelliJ and run the application

###Scalability
this can be solved by installing this microservice on several hosts and using PostgreSQL server(could be distributed).
###Concurrency
Using data transfer object to handle concurrent transaction and account
##

#DataObject class declare two classes:

* Account for each player
* Wallet for each transaction

* Both with Builder design pattern to construct the objects step by step, also override hashcode and equals method to make sure the transaction safe for the different currency

* Account and Wallet objects are linked by a many-to-one relationship

* the Wallet object has an entity "purpose", if it is "withdraw" the account will be a negative number, in the future, it could be modified to a boolean value to save computation power

#DTO classes:
They are two DTO(data transfer object) classes for the data objects to reduce remote calls for API, returning a DTO to summarising everything. Also with two mapper classes to transfer DTO to the data object, vice versa and also for data objects to a list of DTO objects

#Repository
Two repositories that extend the CRUD repository to store objects. The Wallet repo has three methods to query the repo, the get balance method is a sum of all the wallet's accounts based on the given id

#Services
There are two services based on the PlayerService interface, which implement the method for the data operation for example, save, update, return object, query repo.


#Controller


For the controller we have:

* 4 GET

GET http://localhost:8080/v1/players
get all player accounts

GET http://localhost:8080/v1/players/{{id}}
get the player by id

GET http://localhost:8080/v1/transactions
get all the transactions

GET http://localhost:8080/v1/transactions/{{id}}
get all the transactions by account id



* 2 POST

POST http://localhost:8080/v1/players
create player account

POST http://localhost:8080/v1/{{id}}/transactions

create a transaction for the player based on his id

* 1 PUT

PUT http://localhost:8080/v1/players/{{id}}
update player account

##transaction JSON example for postman
{
"id": 1,
"accountId": 2,
"amount": 223,
"purpose": "withdraw",
"transactionDate": "2017-12-27",
"transactionReference": 123
}

##player JSON
{
"id": 25,
"playerName": "jiacheng qi",
"sex": "M",
"dateCreated": "2017-12-27",
"balance": 12.3
}

# test
the test service test folder need to run independently without other test running since the application has the @Transactional notation 
