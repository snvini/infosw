# InfoSW - REST API

A rest api using Java 11 + Spring boot + MongoDB + Docker-Compose for study.

### What it does ?

This api get a Planet information posted by the user, search if this planet appears in any Staw Wars movie through [SWAPI](https://swapi.dev/), get the appeareances count and store the information. 

## Getting Started

You will just need [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/install/) to run this application !

After get those, just run the file 

* "apiWindowsRun.bat" if you are using Windows.
* "apiLinuxRun.sh" if you are using Linux.

Those files will run the docker-compose commands for you and get all de dependecies needed.

If you want to run the commands by yourself, just run the following at the root directory
```
docker-compose pull && docker-compose up -d
```
**Attention, be sure that your machine can run "docker-compose" commands !**


## API Endpoints and Usage

The server will listen at **http://localhost:8081/planets**

| function | HTTP Verb| endpoint | Body | example |
| ------ | ------ | ------ | ------ |  ------ |
| List all planets| GET | / || /planets/ |
| Search by id| GET | /{id} || /planets/5f6003523f518e60c344a3d5 |
| Search by name | GET | /?name={name} || /planets/?name=Tatooine |
| Add Planet | POST | / |Planet Json| {"name"="Tatooine"} |
| Remove Planet | DELETE | /{id} || /planets/5f6003523f518e60c344a3d5 |

### Planet document

Planets have the following attributes:
  - id (auto generated)
  - name (cannot be null)
  - climate 
  - terrain
  - appearances (from SWAPI)

Planet JSON Example:
```
{
  "id": "5f6003523f518e60c344a3d5",
  "name": "TATOOINE",
  "climate": "Dry",
  "terrain": "Desert",
  "appearances": 5
}
```

Body JSON example for request:
```
{
  "name": "TATOOINE",
}
```

## License
[MIT](https://choosealicense.com/licenses/mit/)