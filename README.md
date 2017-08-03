# Setup

Install Docker on your laptop (recommended: install the Docker Toolbox which includes useful tools like Docker Compose and Kitematic)
    - https://docs.docker.com/toolbox/overview/

I am using the following Docker versions on Mac OSX:

```
$ docker --version
Docker version 17.06.1-ce-rc1, build 77b4dce

$ docker-compose --version
docker-compose version 1.14.0, build c7bdf9e
```

# Repository structure

## _/cassandra_

Single-node Cassandra exercises.

## _/app_

Cassandra app development exercises.

## _/cluster_

Multi-node setup exercises with monitoring, performance, availability exercises.

### Setup with Docker-Compose

For the more complex setup in the multi-node exercises, we use docker-compose.

Launch servers:

`docker-compose up`

To (re)build containers, run `docker-compose up --build`.

View what's going on:

`docker-compose ps`

When you are done with all exercises, you can remove the containers entirely (including data volumes via `--volumes`):

`docker-compose down --volumes`