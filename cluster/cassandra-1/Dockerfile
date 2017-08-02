FROM cassandra:3.11

RUN apt-get update && apt-get install -y net-tools && apt-get -y install vim

ADD cql3-exercises /cql3-exercises

# JMX is running on 0.0.0.0, therefore we need to change the port.
COPY config/cassandra-env.sh /etc/cassandra/cassandra-env.sh

# With docker-compose we don't want to sed the cassandra.yaml
# Instead we mount the cassandra.yaml from local config dir.
# Therefore, we overwrite the entrypoint script from the cassandra
# base docker container.
ENV CASSANDRA_CONFIG /etc/cassandra
COPY config/cassandra.yaml /etc/cassandra/cassandra.yaml
COPY config/docker-entrypoint-simple.sh /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.sh \
    && chown -R cassandra:cassandra "$CASSANDRA_CONFIG" \
	&& chmod 777 "$CASSANDRA_CONFIG"