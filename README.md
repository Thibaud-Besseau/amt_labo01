# AMT labo01 - BootCamp Project

This project is conducted for the course "AMT-2017, at HEIG-VD.

## What is this
This application offers to manage a list of people. People are stored in a database.

* Generate randomly 1 to 1'000'000 person (s).
* Manually add a person
* Edit a person
* Delete a person

### User interface

## How is this
  * <a href="https://startbootstrap.com/template-overviews/sb-admin/">SB admin</a> : for the Bootstrap template.
  * <a href="https://randomuser.me/api/?inc=gender,name,dob,email,phone&results=">https://sandomuser.me</a> : for the random
 generation of people.
  * Model MVC : used on the server side.
  * JDBC : used to access the DB
  * Docker-compose : used to launch application
  
# How to lanch the application
Go in the topology folder ../topology-amt./ , and execute the following command: ```docker-compose up --build```.

To access the application, entrer the following URL: 
* ?  : <a href="http://localhost:9090/AMTPeopleManager-1.0-SNAPSHOT/people-list">click here</a>
* Windows : <a href="http://192.168.99.100:9190/AMTPeopleManager-1.0-SNAPSHOT/people-list">click here</a>



