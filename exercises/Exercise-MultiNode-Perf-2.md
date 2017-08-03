# Exercise: Another failure strikes

Stop cassandra-server-2:

`docker stop cassandra-server-2`

Write some data with Consistency Level ONE on cassandra-server-1 using cassandra-stress.

`docker exec -it cassandra-server-1 /bin/bash`

`cassandra-stress write no-warmup cl=ONE n=10 -node 172.16.238.20`

Does it work? Why/why not?
