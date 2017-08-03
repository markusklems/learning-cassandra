# Security

## Authentication

On all cassandra servers, set in the _cassandra.yaml_:

`authenticator: PasswordAuthenticator`

Then, restart all cassandra servers.

Connect to one cassandra server:

`docker exec -it cassandra-server-1 /bin/bash`

On cassandra-server-1:

```
cqlsh -u cassandra -p cassandra
cqlsh> ALTER KEYSPACE system_auth WITH replication =
  {'class' : 'NetworkTopologyStrategy',
  'dc1' : 2, 'dc2' : 1};

$ cqlsh -u cassandra -p cassandra
cqlsh> LIST USERS;
cqlsh> CREATE USER otto WITH PASSWORD 'test1234567' NOSUPERUSER;
cqlsh> ALTER USER otto WITH PASSWORD  'test1234567' SUPERUSER;
cqlsh> DROP USER otto;
```

For easier access to cqlsh, you can enter your credentials in a file ~/.cqlshrc and chmod 600 or similar to keep its content secret:

```
echo "[authentication]
username = otto
password = test1234567" > /root/.cassandra/cqlshrc
```

## Authorization

On all cassandra servers, set in the _cassandra.yaml_:

`authorizer: CassandraAuthorizer`

Then, restart all cassandra servers.

Connect to one cassandra server:

`docker exec -it cassandra-server-1 /bin/bash`

On cassandra-server-1:

```
cqlsh

cqlsh> GRANT ALL ON ALL KEYSPACES TO cassandra;
cqlsh> GRANT ALL ON ALL KEYSPACES TO otto;
cqlsh> LIST ALL PERMISSIONS;
```


## In-transit encryption (SSL)

Steps 1 and 2 have already been performed. The files are in the _cluster/cassandra-{x}/security_ directories.

1. Generate the private and public key pair for each server of the cluster.

```
~$ keytool -genkey -alias cassandra-server-1 -keyalg RSA -validity 7 -keystore .keystore
~$ keytool -genkey -alias cassandra-server-2 -keyalg RSA -validity 7 -keystore .keystore
~$ keytool -genkey -alias cassandra-server-3 -keyalg RSA -validity 7 -keystore .keystore
```

(default password: cassandra)

2. Export the public part of the certificate to a separate file.

```
~$ keytool -export -alias cassandra-server-1 -file cassandra1.cer -keystore .keystore
~$ keytool -export -alias cassandra-server-2 -file cassandra2.cer -keystore .keystore
~$ keytool -export -alias cassandra-server-3 -file cassandra3.cer -keystore .keystore
```

3. Copy all certificates to all other servers.

```
docker cp cluster/cassandra-1/security/cassandra1.cer cassandra-server-2:/cassandra1.cer
docker cp cluster/cassandra-1/security/cassandra1.cer cassandra-server-3:/cassandra1.cer
```

```
docker cp cluster/cassandra-2/security/cassandra2.cer cassandra-server-1:/cassandra2.cer
docker cp cluster/cassandra-2/security/cassandra2.cer cassandra-server-3:/cassandra2.cer
```

```
docker cp cluster/cassandra-3/security/cassandra3.cer cassandra-server-1:/cassandra3.cer
docker cp cluster/cassandra-3/security/cassandra3.cer cassandra-server-2:/cassandra3.cer
```

4. On each cassandra server:

On cassandra-server-1:
```
keytool -import -v -trustcacerts -alias cassandra2 -file cassandra2.cer -keystore .truststore
keytool -import -v -trustcacerts -alias cassandra3 -file cassandra3.cer -keystore .truststore
```

On cassandra-server-2:
```
keytool -import -v -trustcacerts -alias cassandra1 -file cassandra1.cer -keystore .truststore
keytool -import -v -trustcacerts -alias cassandra3 -file cassandra3.cer -keystore .truststore
```

On cassandra-server-3:
```
keytool -import -v -trustcacerts -alias cassandra1 -file cassandra1.cer -keystore .truststore
keytool -import -v -trustcacerts -alias cassandra2 -file cassandra2.cer -keystore .truststore
```

4. Copy the keystores to the respective servers and adjust permissions:

```
docker cp cluster/cassandra-1/security/.keystore cassandra-server-1:/.keystore
docker cp cluster/cassandra-2/security/.keystore cassandra-server-2:/.keystore
docker cp cluster/cassandra-3/security/.keystore cassandra-server-3:/.keystore
```

On each cassandra-server:

```
chown cassandra:cassandra /.keystore
chmod 400 /.keystore
```