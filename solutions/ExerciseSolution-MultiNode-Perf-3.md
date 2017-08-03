# Exercise: Failure-proofing by replicating

On cassandra-server-1 create a new keyspace with replication_factor 2 and load it with some data:

```
cassandra-stress write no-warmup cl=ONE n=1000 -node 172.16.238.20 -schema "replication(factor=2)" keyspace="keyspace2"
```

or

```
cassandra-stress write no-warmup cl=ONE n=1000 -node 172.16.238.20 -schema "replication(strategy=NetworkTopologyStrategy, dc1=2)" keyspace="keyspace3"
```

Then shutdown cassandra-server-2 (`docker stop cassandra-server-2`) and run on cassandra-server-1:

```
cassandra-stress mixed no-warmup cl=ONE n=1000 -node 172.16.238.20 -schema keyspace="keyspace2"
```

or

```
cassandra-stress write no-warmup cl=ONE n=1000 -node 172.16.238.20 -schema keyspace="keyspace3"
```

However, our initial workload on keyspace1 still fails if cassandra-server-2 goes down (no surprise):

```
cassandra-stress mixed no-warmup cl=ONE n=1000 -node 172.16.238.20 -schema keyspace="keyspace1"
```