# Project Name : Address Book Simulation

## Author : RASHMI VISHNU
*NOTE: if you see name SUNIL somewhere its because I have used my husbands Laptop*

## SWAGGER URL
1. Start the application using bootRun, **./gradlew bootRun**
1. Swagger UI Link : http://localhost:8080/swagger-ui.html#/address-book-controller

#### ASSUMPTIONS and SCOPE for ENHANCEMENT
1. Extensive test cases to cover more scenarios can be added
1. Validations can be enhanced to include more on the inputs. Basic validations are provided for now.
1. User Interface can be provided
1. Default port will be tomcat's default port -8080
1. Uses internal cache for storing the data. Can be enhanced to use H2 
1. Retrieved data will be sorted by first name always

## HOW TO RUN THE SIMULATION

**_To run from Eclipse/IntelliJ_**

Run as application "AddressBookApplication.java"

**_To build the application_**

./gradlew clean build

**_To run the application from command prompt or using gradle_**

./gradlew bootRun

#### Scenarios covered
1. add a new contact to a specified address book
2. remove the specified contact from Address book
3. To Retrieve all the contacts from the address book use http://localhost:8080/api/v1/contacts?condition=ALL
4. retrieve UNIQUE FROM MULTIPLE BOOKS http://localhost:8080/api/v1/contacts?condition=UNIQUE
5. retrieve COMMON FROM MULTIPLE BOOKS http://localhost:8080/api/v1/contacts?condition=COMMON

   
#### PRE configured Data
**address-book-Earth** To fetch data from this addressBook use this URL _GET http://localhost:8080/api/v1/address-book/address-book-Earth/contacts_
```JSON
{
    "id": "788b58e4-5530-45ff-a3e0-d2e0a0449262",
    "firstName": "Ricky",
    "lastName": "Ponting",
    "phoneNumbers": [
        "03313411111",
        "03313411112"
    ]
},
{
    "id": "1d4b6b11-561e-4c90-a912-8b447ffbb8b0",
    "firstName": "Shane",
    "lastName": "Warne",
    "phoneNumbers": [
        "0331445543"
    ]
},
{
    "id": "3cc9d5f2-e905-4966-9d28-b1243b474092",
    "firstName": "Steve",
    "lastName": "Smith",
    "phoneNumbers": [
        "0331445545"
    ]
}
```
**address-book-Mars** To fetch data from this addressBook use this URL _GET http://localhost:8080/api/v1/address-book/address-book-Mars/contacts_
```JSON
{
   "id": "350b34dd-30a0-4c43-bf7a-8a414779e30c",
   "firstName": "David",
   "lastName": "Warner",
   "phoneNumbers": [
       "0331345543",
       "0331345553"
   ]
},
{
   "id": "492545d4-872d-4801-a7f8-7da23e8437f5",
   "firstName": "Ricky",
   "lastName": "Ponting",
   "phoneNumbers": [
       "03313411111",
       "03313411112"
   ]
}
```
