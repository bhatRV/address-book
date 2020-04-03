Project Name : Address Book Simulation

Author : RASHMI VISHNU
#NOTE: if you see name SUNIL somewhere its because I have used my husbands Laptop#

######### SWAGGER URL:####
1.start the application using bootRun
2.browser : http://localhost:8080/swagger-ui.html#/address-book-controller

######### ASSUMPTIONS and SCOPE for ENHANCEMENT :####
1. Extensive test cases to cover more scenarios can be added
2. Validations can be enhanced to include more on the inputs. Basic validations are provided for now. Phone number etc can be further validated
3. User Interface can be provided
4. Default port will be tomcat's default port -8080
5.uses internal cache for storing the data. Can be enhanced to use H2 

#######################HOW TO RUN THE SIMULATION###########################

#To run from Eclipse/IntelliJ

Run as application "AddressBookApplication.java"

#To build the application

./gradlew clean build

###To run the application from command prompt or using gradle:####

./gradlew bootRun

#### Scenarios covered
1. add a new contact to a specified adddress book
2. remove the specified contact from Address book
3. To Retrieve all the contacts from the address book use http://localhost:8080/api/v1/contacts?condition=ALL
4. retrieve UNIQUE FROM MULTIPLE BOOKS http://localhost:8080/api/v1/contacts?condition=UNIQUE
5. retrieve COMMON FROM MULTIPLE BOOKS http://localhost:8080/api/v1/contacts?condition=COMMON

   

