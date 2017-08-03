# Exercise: Add a third Cassandra Node to another Data Center

Uncomment the cassandra3 configs in the _docker-compose.yml_ file and change the _cluster/cassandra-3/config/_ file(s) that are relevant appropriately for the desired datacenter configuration.

Run `docker-compose up` and check with `nodetool` if the cluster is correctly configured.