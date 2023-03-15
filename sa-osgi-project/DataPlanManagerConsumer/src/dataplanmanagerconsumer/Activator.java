package dataplanmanagerconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import phonenetworkprovider.models.User;
import phonenetworkprovider.stores.Database;

public class Activator implements BundleActivator {

	private ServiceReference reference;

	public void start(BundleContext context) throws Exception {
		
	try {
	
		System.out.println("Starting User Portal Consumer...");
		reference = context.getServiceReference(Database.class.getName());
		Database db = (Database) context.getService(reference);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
        
        int decision;
        
//        db.getInstance().getUsers().stream().forEach((item) -> {System.out.println(item.getName());} );
        
        System.out.println("=========== Welcome to the Data Plan Portal =============");
		System.out.println("Please navigate the menu using the number of the decision");
		System.out.println("=========================================================");
		
//		db.getInstance().getDataPlans().stream().forEach((item) -> {System.out.println(item.getProvider());} );
		System.out.println("1) Add new data Package to the system");
		System.out.println("------------------------------------");
		System.out.println("2) Get all data Packages in the system");
		System.out.println("------------------------------------");
		System.out.println("3) Update existing data package");
		System.out.println("-------------------------------");
		System.out.println("4) Delete  data Package to the system");
		System.out.println("--------------------------------------");;
		System.out.println("5) Search data Packages in the system");
		System.out.println("------------------------------------");
		
		
		while (true) {
			try {
				decision = Integer.parseInt(reader.readLine());
				if (decision > 5 || decision < 1) {
					throw new IllegalStateException(
							String.format("%d is not a decision", decision));
				}
				break;
			} catch (Exception e) {
				System.out.println("Invalid input! Please try again");
			}
		}
		
		if (decision == 1) {
			
			System.out.println("===== Add new data Package =====");
			System.out.println("");
			System.out.println("To select the Service Provider refer menu and enter the number of the decision");
			
			
		}}
		
	catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Good Bye");
		context.ungetService(reference);
	}

}
