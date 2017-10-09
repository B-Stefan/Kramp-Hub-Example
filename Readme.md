# Kramp Hub Example
[![Build Status](https://travis-ci.org/B-Stefan/Kramp-Hub-Example.svg?branch=master)](https://travis-ci.org/B-Stefan/Kramp-Hub-Example) [![codebeat badge](https://codebeat.co/badges/0ff451b6-8e53-48c5-b90b-eb573c6db4d1)](https://codebeat.co/projects/github-com-b-stefan-kramp-hub-example-master)

## Getting started 

### Running 

Navigate into the project root folder and execute the following command in your terminal: 

*Docker*
```
$  docker run -p 8080:8080 stefan125/kramphub

```

*Linux*
```
$ ./gradlew bootRun
```

*Windows*
```
 > gradlew.bat bootRun
```
 

 Open your browser on [http://localhost:8080/entries?term=your-search-term](http://localhost:8080/entries?term=your-search-term)
 To monitor the application use the url [localhost:8080/stagemonitor](localhost:8080/stagemonitor)
 
### Testing 

```
$ ./gradlew test
```

## Requirements 

* Java 8


## Project structure 

The project is structured according to the [spring guidelines](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html)
Files for the requirements and architecture are locaed in the [docs](./docs) folder. 

## Release management 

To release a new version set a tag by using the [Semantic Versioning 2.0.0]()http://semver.org/) standard. 
After you pushed your tag to the repository (`git push --tags`) travis creates a new release for you.I 
If appropriate please add more information to the github release.