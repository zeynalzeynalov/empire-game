# Empire game

**Tech stack:**

- Java

- Spring Boot

**Application config:**

\empireGame\src\main\resources\application.properties

 ```
	settings:
		server.port=8080
		empiregame.maxcalllimit=200
```
		
**Application description:**

- To check whether game army generation service status please call:

  `http://localhost:8080/api/army`


- To generate a random army please call: http://localhost:8080/api/army with POST request:
- Body format for POST request:
```		
{
	"armyMenStrong": 10
}
```

- To change limit of non-repetitive random, please update empiregame.maxcalllimit=200 variable at application properties file.

**How to build:**

Please execute command file:
```
	buildProject.cmd
```

- Postman collection file which contains test endpoints calls: 
`EmpireGameRestCallsCollection.postman_collection.json`


- Algorithm used for the random number selection:
  - [Fisher–Yates shuffle](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)



