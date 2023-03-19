package phonenetworkprovider;

import org.osgi.framework.*;
import phonenetworkprovider.stores.Database;


public class Activator implements BundleActivator {

	private ServiceRegistration<?> registration;

	@Override
	public void start(BundleContext context) throws Exception {
		try {
			Database db =  Database.getInstance();
			System.out.println("Acquired database instance ...");
			registration = context
					.registerService(Database.class.getName(), db, null);
			System.out.println("==== Started database (Phone Network Provider) ====");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
		System.out.println("==== Stopped database ====");
	}

}

