# Requirements 

This documents represents the requiremnts for this project work. 

## Features  


 | Description  | Status  | Comment |
 |---|---|---|
 | Setup project (Hello world)    | 😎    ||
 | Setup CI     | 😎    ||
 | Setup CD (Docker, Jar release)    | 😎    ||
 | Setup first tests    | 😎    ||
 | HTTP endpoint to list albums and books   | 😎    ||
 | Use Itunes and Google Books API   | 😎  ||
 | Handle partial failure of external resources    | 😎  ||
 | Exposes metrics on response times for upstream services   | 😒    | The stagemonitor web interface dosen't display the recorded metrices... |
 | Expose healthcheck  | 😎  ||
 | be production-ready | 😑  | I'm almost sure that this application could be improved  in this point. For example the size of the fat jar or the used application server. |
 | respond within a minute | 😎  | I cancel all request after 45 seconds and respond with an empty list.|
 | return maximum of 5 books and maximum of 5 album |  😎  | Done |
 | Configure the limit for each api per env. |  😎  | Done |

