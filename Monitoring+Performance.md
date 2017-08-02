# Monitoring

1. Add Grafana datasource:

```
curl 'http://admin:admin@127.0.0.1:3000/api/datasources' -X POST \
-H 'Content-Type: application/json;charset=UTF-8' \
--data-binary '{"name":"graphite","type":"graphite","url":"http://graphite:80",
"access":"proxy","isDefault":true,"basicAuth":true,"basicAuthUser":"guest","basicAuthPassword":"guest"}'
```

2. Log into the grafana dashboard at http://127.0.0.1:3000/ with user/password admin/admin.

3. Optional: You can import the dashboard _cluster/monitoring/Cassandra-Dashboard.json_.

# Performance Testing

Log into a cassandra server:

`docker exec -it cassandra-server /bin/bash`

Load the server with 100.000 write operations:

`root@e5576b0383af:/# cassandra-stress write n=100000`

Run a workload with 100.000 read operations:

`root@e5576b0383af:/# cassandra-stress read n=100000`