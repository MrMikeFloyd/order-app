# JHipster Order App test application

This is a microservice test application built with [JHipster](https://www.jhipster.tech). It uses a pre-generated JDL application template built using the [JDL online editor](https://start.jhipster.tech). I used information from [Jörg Riegel's blog post on the codecentric blog](https://blog.codecentric.de/en/2020/05/kick-start-your-microservice-project-with-jhipster/) to get started.

## Application components

The application consists of three primary services:

* storeService: Serves product information
* orderService: Allows the ordering of products
* webStore: Web frontend for the application

Other than that, there's a little more going on:

* Kafka is used for cross-service communications
* Eureka is used for service discovery

For this example, docker is used as a deployment target.

## How to build/run

In order to run this example, you will need to have `docker` as well as `docker compose` installed on your machine.

Run as follows:

```
cd docker-compose
docker-compose up -d
```

Wait for a bit, then navigate your browser to `http://localhost:8080`. You might need to add a host entry on your system to be able to navigate to your local keycloak instance for login:

```
echo "127.0.0.1	keycloak" >> /etc/hosts
```

To build everything from scratch using the JDL:

```
cd jdl
jhipster import-jdl order-app-jhipster-jdl.jh
```

To build the required docker containers, follow the instructions at the end of the import command.
