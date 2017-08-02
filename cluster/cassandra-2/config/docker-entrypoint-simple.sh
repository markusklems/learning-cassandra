#!/bin/bash
set -e

# first arg is `-f` or `--some-option`
if [ "${1:0:1}" = '-' ]; then
        set -- cassandra -f "$@"
fi

# allow the container to be started with `--user`
if [ "$1" = 'cassandra' -a "$(id -u)" = '0' ]; then
        chown -R cassandra /var/lib/cassandra /var/log/cassandra "$CASSANDRA_CONFIG"
        exec gosu cassandra "$BASH_SOURCE" "$@"
fi

exec "$@"