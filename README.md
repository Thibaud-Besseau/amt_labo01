# AMT labo01 - BootCamp Project

This project is conducted for the course "AMT-2017, at HEIG-VD.

* Teacher: Olivier Liechti.
* Authors: Ludovic Delafontaine & Michela Zucca.

## What is this
This application offers to manage a list of people. People are stored in a database.

* Generate randomly a lot of People.
* Manually add a person.
* Edit a person.
* Delete a person.

The application is launched with three previously created persons. 

People in the database are presented in a table, the data is presented on several pages. It is possible to :
* Sort people by gender, last name, first name, birthday, phone or email. 
* Search for a person using the filter.

## How is this
  * <a href="https://startbootstrap.com/template-overviews/sb-admin/">SB admin</a> : for the Bootstrap template.
  * <a href="https://randomuser.me/api/?inc=gender,name,dob,email,phone&results=">https://randomuser.me</a> : for the random
 generation of people.
  * Model MVC : used on the server side.
  * JDBC : used to access the DB
  * Docker-compose : used to launch application
  
# How to lanch the application
Go in the topology folder ../topology-amt./ , and execute the following command: ```docker-compose up --build```.

To access the application, entrer the following<a href="http://localhost:9090/AMTPeopleManager-1.0-SNAPSHOT/people-list">URL</a>
