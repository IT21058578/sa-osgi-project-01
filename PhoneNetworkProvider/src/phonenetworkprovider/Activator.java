package phonenetworkprovider;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import phonenetworkprovider.stores.Database;

public class Activator implements BundleActivator {

	private ServiceRegistration registration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		try {
			Database db = Database.getInstance();
			System.out.println("Acquired database instance ...");
			registration = bundleContext
					.registerService(Database.class.getName(), db, null);
			System.out.println("==== Started database (Phone Network Provider) ====");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		registration.unregister();
		System.out.println("==== Stopped database ====");
	}

}
