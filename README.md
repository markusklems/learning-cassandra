# Repository structure

## _/cassandra_

- `cd cassandra`

### 1. Build a cassandra server container image

- `docker build -t mycass:latest .`

### 2. launch a single cassandra server

- `docker run --name cassandra-server -d mycass:latest`

### 3. Connect to the cassandra server's bash

- `docker exec -it cassandra-server /bin/bash`

Review the directory structure:
- `ls -la var/lib/cassandra/`
- `ls -la var/log/cassandra/`
- `ls -la etc/cassandra`
- `more etc/cassandra/cassandra.yaml` (hit "space" to view next screen, "q" to exit)

You can view the logs in Kitematic or via `docker logs cassandra-server`

### 4. Make a cassandra server cluster

Launch a second cassandra server instance and join the existing (running) cassandra server:

- `docker run --name cassandra-server-2 -d -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' cassandra-server)" mycass:latest`

## _/app_

`cd app`

### 1. Build app server

- `docker build -t myapp:latest .`

### 2. Run and connect to the app server's bash

- `docker run -it --rm --name app-server --link cassandra-server:latest myapp:latest /bin/bash`

### 3. Run Java code examples

Once logged into the app server bash, you can run the Java code examples like this:

- `java -cp target/mk-0.2-SNAPSHOT-jar-with-dependencies.jar mk.netflix.AstyClient1`

Or run: `example1.sh`

TODO (maybe do this after docker-compose)
Alternatively, you can run the java commands that are shipped in little shell scripts on the bin directory, e.g., like this:

- `docker exec --name app-server --link cassandra-server:latest myapp:latest example1.sh`
