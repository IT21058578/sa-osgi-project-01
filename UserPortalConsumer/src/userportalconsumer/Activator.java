package userportalconsumer;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import phonenetworkprovider.stores.Database;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference reference;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Starting User Portal Consumer...");
		reference = bundleContext.getServiceReference(Database.class.getName());
		Database db = (Database) context.getService(reference);
		
		Scanner scanner = new Scanner(System.in);
		int decision;
		
		System.out.println("===== Welcome to the User Portal =====");
		System.out.println("Please navigate the menu using the number of the decision");
		System.out.println("1. Create a new user");
		System.out.println("2. Continue as existing user");
		
		while (true) {
			try {
				decision = scanner.nextInt();
				if (decision > 2 || decision < 1) {
					throw new IllegalStateException(
							String.format("%d is not a decision", decision));
				}
				break;
			} catch (Exception e) {
				System.out.println("Invalid input! Please try again");
			}
		}
		
		if (decision == 1) {
			System.out.println("===== Account Registration =====");
			System.out.print("Please enter your name : ");
			System.out.print("Please enter your address : ");
			System.out.println("Please pick a service provider from the following list, ");
			System.out.println("Our server is down at the moment, please try again later");
			System.out.println("Your phone number is: ");
			System.out.println("All done! You can now log in using the phone number you've received");
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}










