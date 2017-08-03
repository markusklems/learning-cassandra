# Exercise: Twotter

Bash into the cassandra-server:

`docker exec -it cassandra-server /bin/bash`

Create a keyspace:

```
cqlsh> CREATE KEYSPACE twotter WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
```

Next, start the app-server:

`docker run -it --rm  -e CASSANDRA_HOSTS=cassandra-server -v $(pwd)/src:/myapp/src --name app-server --link cassandra-server:latest myapp:latest /bin/bash`

Run `exercise2.sh` (aka `java -cp target/mk-0.2-SNAPSHOT-jar-with-dependencies.jar mk.twotter.TwotterClient`).

On the Twotter command line, enter:

```
> create-userline
```

Now, on the cassandra-server, create an index on the userline table for user_name.

