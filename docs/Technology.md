# Technology & Architecture 

This document explains shortly the resons for this certain technology stack 

## Technology
 | Technology  | Comment | 
 |---|---|
 | Spring  |  Easy to use framework to create web applications with Java. Fast development and Java EE compatibility | 
 | Spring-Boot  |  Small application server, good for a small project. If wewant to scale up the service we should consider using another application server | 
 | Travis  |  CI / CD platform to build and test the project on each commit. | 
 | Functional programming  |  Programming principle to reduce the complexity of code especially in  a multi-threading env. Also support scalability because you reduce your shared state inside your application and handle more operations stateless | 
 | stagemonitor  |  Simple tool to monitor your state. For a big application should we change the configuration at least. The monitoring software should run on a dedicated machine and also separated from the application| 
 
 
 ## Architecture 
 
 The architecture of is pretty straight forward: 
 
 ![class-diagram](./class-diagram.svg)
 
 
## Known Issues / Problems

During the development I got some problems that are not so easy to solve. 
This are perfect points to discuss further with the team. 

* Naming: kramphub.example.bookmusic is not nice
* Testing: We need to add more test esspecally for async testing the api. I got some problems with the mocking of the response because I used Java 8 features. 
* Monitoring: Research a better solution. With more features and a dedicated runtime just for the monitoring system. 
* Build-process: Creating a big jar is not a proper solution. Extend the docker support would be better in my opinion. 

 