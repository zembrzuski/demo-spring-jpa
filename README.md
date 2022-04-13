
APP
---

- Simple RESTFUL service. It is a basic social network 
  with users, where a given user make friends and like movies.
- Explore it using http://localhost:8888/swagger-ui/index.html#/
- Technologies: 
  - Spring, Spring boot, Spring Data JPA
  - Relational databases (h2 / postgres)

RUNNING
-------

- Local env
  - Java 17 required
  - database: h2 http://localhost:8888/h2-console/
  - Command to run: `./gradlew bootRun`

- docker
  - docker and docker compose required
  - database: postgresql http://localhost:8181
  - `$ ./abuild.sh && ./adeploy.sh` 
