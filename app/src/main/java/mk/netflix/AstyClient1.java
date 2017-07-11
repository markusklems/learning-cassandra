package mk.netflix;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.cassandra.thrift.Cassandra;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolType;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

public class AstyClient1 {
	
	public static final String KEYSPACE_NAME = "app_keyspace";
	// provide (via Docker) as environment variable
	public static final String SEED_HOST = System.getenv("CASSANDRA_HOST");
	// Thrift port
	public static final Integer CONN_PORT = 9160;
	
	//private static final Logger logger = LoggerFactory
	//		.getLogger(AstyClient1.class);

	public static void main(String[] args) {
		AstyanaxContext<Keyspace> ctx = 
		           new AstyanaxContext.Builder()
		           .forKeyspace(KEYSPACE_NAME)
		           .withConnectionPoolConfiguration(
		               new ConnectionPoolConfigurationImpl("myCPConfig")
		                   .setSeeds(SEED_HOST)
		                   .setPort(CONN_PORT))
		           .withAstyanaxConfiguration(
		               new AstyanaxConfigurationImpl()
		                   .setConnectionPoolType(ConnectionPoolType.TOKEN_AWARE)
		                   .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE))
		           .buildKeyspace(ThriftFamilyFactory.getInstance());

		Keyspace ks = ctx.getClient();
		ctx.start();
		
		System.out.println("My keyspace: "+ks.getKeyspaceName());
	}
	
}
