# Exercise: Create a NetworkTopologyStrategy keyspace

Bash into the cassandra-server:

```
docker exec -it cassandra-server-1 /bin/bash

cqlsh 172.16.238.20

cqlsh> CREATE KEYSPACE twotter 
       WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'dc1' : 1 };
cqlsh> DESC KEYSPACE twotter;
```
