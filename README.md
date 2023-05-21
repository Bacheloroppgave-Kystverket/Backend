# Backend

# Information

This is part of a bachelor thesis done by the authors in 2023.

A link to the other parts of the project can be found below:

- [VR-Demo](https://github.com/Bacheloroppgave-Kystverket/Unity-VR-Eye-Tracking-Demo)
- [Frontend](https://github.com/Bacheloroppgave-Kystverket/Frontend)
- [Backend](https://github.com/Bacheloroppgave-Kystverket/Backend)

## Productiuon setup
This backend uses docker to launch the backend, but first some files has to be added. 

### Step 1
Go to the src/main/resources folder and locate the application template.txt file. 
In this folder we need to make two different files to configure the testing profile and normal profile.
Make both the application.properties file and application-test.properties file here. 
Copy the template from the .txt file over to both of the new files. 
Configure the test file to be located on a test database on the local computer so that tests are ran correctly when the project is compiled.
Change the database name, username and password to your liking.

### Step 2
Go to the main folder and make a docker-compose.yml and paste the text from docker-compose.txt into that file. 
Change the database name, username and password to match the first application.properties file. 
Also change the host address of the database in the first application.properties to "mysql://database:3306/deployment" where deployment is the name of your database in the compose file.

### Step 3
Deploy the docker profile by using:

#### docker-compose up