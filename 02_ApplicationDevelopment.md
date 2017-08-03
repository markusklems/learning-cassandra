# Setup

## Astyanax + Thrift

You need to (re-)launch a cassandra-server container instance with rpc_server enabled.

`docker stop cassandra-server`

`docker run --rm --name cassandra-server -d -e CASSANDRA_START_RPC=true -e CASSANDRA_ENDPOINT_SNITCH=GossipingPropertyFileSnitch mycass:latest`

# Application Development

`cd app`

## Build app server

`docker build -t myapp:latest .`

## Run the app server

Run the app server, link it to the cassandra server, and connect to the app server's bash:

`docker run -it --rm  -e CASSANDRA_HOSTS=cassandra-server -v $(pwd)/src:/myapp/src --name app-server --link cassandra-server:latest myapp:latest /bin/bash`

With the `-v`flag, your local (host) source directory is mounted into the container instance. If you change files on your local source directory, the change will be applied inside the container.

## Run Java examples

If you are not on the app server yet, connect to the app server's bash:

`docker exec -it app-server /bin/bash`

Once logged into the app server bash, you can run the Java code examples like this:

`java -cp target/mk-0.2-SNAPSHOT-jar-with-dependencies.jar com.netflix.astyanax.examples.AstClient`

Or run: `exercise1.sh` (if that does not work: `bash /bin/exercise1.sh`)

When you change the Java source code, you need to re-compile with maven:

`mvn install`

Alternatively, you can run the java commands without the app server bash like this:

`docker exec app-server example1.sh`

The src directory on your host is mounted at the container-internal src directory path. Whenever you change the source code in the src directory on your host, the change will also be applied to the src in your container. For compiling and assembing a new jar file, you need to run the following command:

`docker exec -v src:/myapp/src app-server mvn install`