# bankaccount
Bank Account Sample Test

This test uses spring boot to implement the REST endpoints for the given test
It bundles all the dependencies using maven.

#Steps to Run 1
1. Clone the repo from https://github.com/stansL/bankaccount.git or from the shared bitbucket link
2. mvn clean install the application
3. Run the built bank-1.0-SNAPSHOT.jar jar file, this spawns the necessary bootstrapping to setup the server running on http://localhost:8080/api/...
4. To test the end points, open up postmaster or any other rest client and access the following endpoints
      balance(GET): http://localhost:8080/api/111111/balance
      deposit(POST): http://localhost:8080/api/111111/deposit       URL Params : amount=1000 or any value
      withdrawal(POST) : http://localhost:8080/api/111111/withdraw       URL Params : amount=1000 or any value
      
NB: 111111 is the account id for the default/universal account used in this test

Steps to run 2
1. Clone the project into an IDE (Eclipse/Intellij etc)
2. Run the EntryPoint BankApplication.java..this spawns the necessary bootstrapping to setup the server running on http://localhost:8080/api/...
3. To test the end points, open up postmaster or any other rest client and access the following endpoints
      balance(GET): http://localhost:8080/api/111111/balance
      deposit(POST): http://localhost:8080/api/111111/deposit       URL Params : amount=1000 or any value
      withdrawal(POST) : http://localhost:8080/api/111111/withdraw       URL Params : amount=1000 or any value
      
NB: 111111 is the account id for the default/universal account used in this test


