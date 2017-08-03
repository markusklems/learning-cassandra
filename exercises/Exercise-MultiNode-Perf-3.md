# Exercise: Failure-proofing by replicating

Re-run the scenario from before with cassandra-stress using a replicated keyspace, so that the workload will run even if cassandra-server-2 is down.

Re-start cassandra-server-2 first, because schema changes need to be propagated to all nodes:

`docker start cassandra-server-2`
