# Single-Node Cassandra Setup

`cd cassandra`

## 1. Build a cassandra server container image

`docker build -t mycass:latest .`

## 2. launch a single cassandra server

`docker run --rm --name cassandra-server -d mycass:latest`

## 3. Connect to the cassandra server's bash

`docker exec -it cassandra-server /bin/bash`

Review the directory structure:
`ls -la var/lib/cassandra/`
`ls -la var/log/cassandra/`
`ls -la etc/cassandra`
`more etc/cassandra/cassandra.yaml` (hit "space" to view next screen, "q" to exit)

You can view the logs in Kitematic or via `docker logs cassandra-server`

## 4. Optional: Create a cassandra server cluster

(we will do this with docker-compose, but you can also add cassandra servers to your cluster like this)

Launch a second cassandra server instance and join the existing (running) cassandra server:

`docker run --rm --name cassandra-server-2 -d -e CASSANDRA_SEEDS="$(docker inspect --format='{{ .NetworkSettings.IPAddress }}' cassandra-server)" mycass:latest`