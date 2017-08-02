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

`cd cassandra`

### 1. Build a cassandra server container image

`docker build -t mycass:latest .`

### 2. launch a single cassandra server

`docker run --rm --name cassandra-server -d mycass:latest`

### 3. Connect to the cassandra server's bash

`docker exec -it cassandra-server /bin/bash`

Review the directory structure:
`ls -la var/lib/cassandra/`
`ls -la var/log/cassandra/`
`ls -la etc/cassandra`
`more etc/cassandra/cassandra.yaml` (hit "space" to view next screen, "q" to exit)

You can view the logs in Kitematic or via `docker logs cassandra-server`

### 4. Make a cassandra server cluster

Launch a second cassandra server instance and join the existing (running) cassandra server:

`docker run --rm --name cassandra-server-2 -d -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' cassandra-server)" mycass:latest`

## _/app_

`cd app`

### 1. Build app server

`docker build -t myapp:latest .`

### 2. Run the app server

Run the app server, link it to the cassandra server, and connect to the app server's bash:

`docker run -it --rm --name app-server --link cassandra-server:latest myapp:latest /bin/bash`

### 3. Run Java code examples

If you are not on the app server yet, connect to the app server's bash:

`docker exec -it app-server /bin/bash`

Once logged into the app server bash, you can run the Java code examples like this:

`java -cp target/mk-0.2-SNAPSHOT-jar-with-dependencies.jar com.netflix.astyanax.examples.AstClient`

Or run: `bash example1.sh`

Alternatively, you can run the java commands without the app server bash like this:

`docker exec app-server example1.sh`

The src directory on your host is mounted at the container-internal src directory path. Whenever you change the source code in the src directory on your host, the change will also be applied to the src in your container. For compiling and assembing a new jar file, you need to run the following command:

`docker exec app-server mvn install`

# Setup with Docker-Compose

Getting started with more complex cassandra exercises using docker-compose.

Launch servers:

`docker-compose up`

To (re)build containers, run `docker-compose up --build`.

View what's going on:

`docker-compose ps`

When you are done with all exercises, you can remove the containers entirely (including data volumes via `--volumes`):

`docker-compose down --volumes`

## Monitoring

Add Grafana datasource:
```
curl 'http://admin:admin@127.0.0.1:3000/api/datasources' -X POST \
-H 'Content-Type: application/json;charset=UTF-8' \
--data-binary '{"name":"graphite","type":"graphite","url":"http://graphite:80",
"access":"proxy","isDefault":true,"basicAuth":true,"basicAuthUser":"guest","basicAuthPassword":"guest"}'
```

Log into the grafana dashboard at http://127.0.0.1:3000/ with user/password admin/admin.

## Performance Testing

Log into a cassandra server:

`docker exec -it cassandra-server /bin/bash`

Load the server with 100.000 write operations:

`root@e5576b0383af:/# cassandra-stress write n=100000`

Run a workload with 100.000 read operations:

`root@e5576b0383af:/# cassandra-stress read n=100000`