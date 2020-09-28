# Postgresql & PgAdmin powered by compose

## Requirements:
* docker >= 17.12.0+
* docker-compose

## Install Docker: https://docs.docker.com/engine/install/

## Quick Start
* Run this command `docker-compose -f postgresql.yml up`

## Environments
This Compose file contains the following environment variables:

* `POSTGRES_USER` the default value is **postgres**
* `POSTGRES_PASSWORD` the default value is **changeme**
* `PGADMIN_PORT` the default value is **5050**
* `PGADMIN_DEFAULT_EMAIL` the default value is **pgadmin4@pgadmin.org**
* `PGADMIN_DEFAULT_PASSWORD` the default value is **admin**

## Access to postgres: 
* `localhost:5432`
* If you are using windows, localhost may not work, use the ip provided by the folowing comand 'docker-machine ip' instead
* **Username:** postgres (as a default)
* **Password:** changeme (as a default)

## Access to PgAdmin: 
* **URL:** `http://localhost:5050`
* If you are using windows, localhost may not work, use the ip provided by the folowing comand 'docker-machine ip' instead

* **Username:** pgadmin4@pgadmin.org (as a default)
* **Password:** admin (as a default)

## Add a new server in PgAdmin:
* **Host name/address** `postgres`
* **Port** `5432`
* **Username** as `POSTGRES_USER`, by default: `postgres`
* **Password** as `POSTGRES_PASSWORD`, by default `changeme`
