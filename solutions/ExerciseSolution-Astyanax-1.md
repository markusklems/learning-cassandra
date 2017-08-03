# Solution

```
$ cqlsh
cqlsh> CREATE KEYSPACE app2 WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'dc1' : 1 };
cqlsh> DESC KEYSPACE app2;
cqlsh> USE app2;
cqlsh:app2> CREATE TABLE employees2 (
                	empid int PRIMARY KEY,
                	deptid int,
                	first_name text,
                    last_name text
             		) WITH COMPACT STORAGE;
cqlsh:app2> DESC TABLE employees2;
cqlsh:app2> SELECT * FROM employees2;
```