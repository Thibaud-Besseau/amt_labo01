# AMT labo01 - BootCamp Project

This project is conducted for the course "AMT-2017, at HEIG-VD.

* Teacher: Olivier Liechti.
* Authors: Thibaud Besseau & Michela Zucca.

## What is this
This application offers to manage a list of people. The People's informations are stored in a database.

* Generate randomly lot of People.
* Manually add a person.
* Edit a person.
* Delete a person.

When the application is started, three persons are already loaded in the database.

All the people saved in the database are displayed in an array. In this array, it is possible to :
* Sort people by gender, last name, first name, birthday, phone or email. 
* Search a person .

## What are the technologies used
  * <a href="https://startbootstrap.com/template-overviews/sb-admin/">SB admin</a> : for the Bootstrap template.
  * <a href="https://randomuser.me/api/?inc=gender,name,dob,email,phone&results=">https://randomuser.me</a> : for the random
 generation of people.
  * Model MVC : used on the server side.
  * JDBC : used to access the DB
  * Docker-compose : used to launch application
  
# How to start the application
Go in the topology folder ../topology-amt./ , and execute the following command: ```docker-compose up --build```.

To access the application, entrer the following URL http://localhost:9090/AMTPeopleManager-1.0-SNAPSHOT/people-list.
