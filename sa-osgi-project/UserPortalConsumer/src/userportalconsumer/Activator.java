package userportalconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import phonenetworkprovider.models.User;
import phonenetworkprovider.stores.Database;

public class Activator implements BundleActivator {

	
	@SuppressWarnings("rawtypes")
	private ServiceReference reference;

	

	public void start(BundleContext context) throws Exception {
		try {

		System.out.println("Starting User Portal Consumer...");
		reference = context.getServiceReference(Database.class.getName());
		@SuppressWarnings("unchecked")
		Database db = (Database) context.getService(reference);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
		
		int decision ;
		
		System.out.println("===== Welcome to the User Portal =====");
		System.out.println("Please navigate the menu using the number of the decision");
		System.out.println("1. Create a new user");
		System.out.println("2. Continue as existing user");
		
		while (true) {
			try {
				decision = Integer.parseInt(reader.readLine());
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
			String name = reader.readLine();
			System.out.print("Please enter your address : ");
			String address = reader.readLine();
			User data = new User(name,address, "");
			db.addUser(data);
			System.out.print("New user account created successfully");
			
//			System.out.println("Please pick a service provider from the following list, ");
//			System.out.println("Our server is down at the moment, please try again later");
//			System.out.println("Your phone number is: ");
//			System.out.println("All done! You can now log in using the phone number you've received");
		}}
		catch(Exception e){
			e.printStackTrace();
			
		}
	}

	public void stop(BundleContext context) throws Exception {
		context.ungetService(reference);
	}

}





