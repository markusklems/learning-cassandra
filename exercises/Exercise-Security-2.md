# Exercise: Encryption

Insert some data via cqlsh without server-to-server encryption and read traffic using tcpdump on the other server.

On cassandra-server-1
```
cqlsh:twotter> INSERT INTO users  (id, name, email) VALUES (104, 'jsmith', 'j@smith.com');
```

On cassandra-server-2
```
$ tcpdump -A -i eth0 'tcp port 7000' | grep smith
```

Now the same but with encryption.

On cassandra-server-2
```
$ tcpdump -A -i eth0 'tcp port 7001'
```
