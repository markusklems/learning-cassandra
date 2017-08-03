# Exercise: Authentication & Authorization

```
cqlsh> CREATE KEYSPACE myspace WITH 
replication = {'class': 
'NetworkTopologyStrategy', 'dc1':2};

cqlsh> CREATE TABLE myspace.users(
id int PRIMARY KEY,
name text,
email text
);

cqlsh> INSERT INTO myspace.users 
(id, name, email) VALUES
(101, 'john', 'john@xyz.de');

cqlsh> CREATE USER heinz WITH
PASSWORD 'linuxhotel' NOSUPERUSER;

cqlsh> GRANT SELECT ON TABLE 
myspace.users TO heinz;

cqlsh> LIST ALL PERMISSIONS OF heinz;

username | resource              | permission
---------+-----------------------+-----------
heinz    | <table myspace.users> |     SELECT

cqlsh> exit;
$ cqlsh -u heinz -p linuxhotel

cqlsh> DROP KEYSPACE myspace;
Bad Request: User heinz has no DROP permission on <keyspace myspace> or any of its parents
cqlsh> USE myspace;
cqlsh:myspace> SELECT * FROM users;
id   | email       | name
-----+-------------+------
101  | john@xyz.de | john


cqlsh> exit;

$ cqlsh

cqlsh> REVOKE SELECT ON myspace.users 
FROM heinz;

cqlsh> LIST ALL PERMISSIONS OF heinz;
cqlsh> exit;

$ cqlsh -u heinz -p linuxhotel

cqlsh> SELECT * FROM myspace.users;
Bad Request: User heinz has no SELECT permission on <table myspace.users> or any of its parents


```
