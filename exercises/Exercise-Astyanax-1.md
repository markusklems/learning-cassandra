# Exercise: Create Keyspace and Table that fits a given Java app

You have started the cassandra-server with:

`docker run --rm --name cassandra-server -d -e CASSANDRA_START_RPC=true -e CASSANDRA_ENDPOINT_SNITCH=GossipingPropertyFileSnitch mycass:latest`

Bash into the cassandra-server:

`docker exec -it cassandra-server /bin/bash`

Use CQL to create a Keyspace and Table for the sample application given in _app/src/main/java/com/netflix/astyanax/examples/AstClient.java_.

When you are done with this task, you should be able to execute the following without an error:

`cd app`

If not built already: `docker build -t myapp:latest .`

`docker run -it --rm  -v $(pwd)/src:/myapp/src --name app-server --link cassandra-server:latest myapp:latest /bin/bash`

On the app-server:

`mvn install`

`exercise1.sh`

Going back to your cassandra-server bash, you should be able to perform the following query:

```
cqlsh:app2> SELECT * FROM employees2;

 empid | deptid | first_name | last_name
-------+--------+------------+-----------
   222 |    333 |       Eric |   Cartman
```