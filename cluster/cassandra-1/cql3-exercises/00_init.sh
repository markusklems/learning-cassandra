#!/bin/bash

# set CQLSH_HOST = <host ip address>
export CQLSH_HOST=`ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1'`

# now you can log in like this: $ cqlsh