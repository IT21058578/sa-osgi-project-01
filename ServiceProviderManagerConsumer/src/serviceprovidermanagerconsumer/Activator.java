package serviceprovidermanagerconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import phonenetworkprovider.models.ServiceProvider;
import phonenetworkprovider.models.IServiceProvider;
import phonenetworkprovider.stores.Database;

public class Activator implements BundleActivator {

	private ServiceReference reference;
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Starting Service Provider Consumer...");
		reference = context.getServiceReference(Database.class.getName());
		Database db = (Database) context.getService(reference);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
        
        int decision;
        
        db.getInstance().getUsers().stream().forEach((item) -> {System.out.println(item.getName());} );
       
        System.out.println("=========== Welcome to the Service Provider Portal =============");
 		System.out.println("Please navigate the menu using the number of the decision");
 		System.out.println("=========================================================");
 		System.out.println();
 		System.out.println();
 		
//		db.getInstance().getDataPlans().stream().forEach((item) -> {System.out.println(item.getProvider());} );
		System.out.println("1) Add new Service Provider to the system");
		
		System.out.println("2) Get all the Service Providers in the system");
	
		System.out.println("3) Update existing Service Provider");

		System.out.println("4) Delete Service Provider from the system");
	
		System.out.println("5) Search Service Providers in the system");
		
		System.out.println("6) Exit");
	
		
		
		while (true) {
			try {
				decision = Integer.parseInt(reader.readLine());
				if (decision > 6 || decision < 1) {
					throw new IllegalStateException(
							String.format("%d is not a decision", decision));
				}
				break;
			} catch (Exception e) {
				System.out.println("Invalid input! Please try again");
			}
		}
		
		if (decision == 1) {
			
			System.out.println("===== Add new Service Provider =====");
			System.out.println();
//			System.out.println("To select the Service Provider refer menu enter the number ");
			
//			db.getInstance().getDataPlans().stream().forEach((item) -> {System.out.println(item.getProvider());} );
			
//			System.out.println("Enter data package Service ProviderID:");
//			String ProviderID = reader.readLine();
			System.out.println("Enter Service Provider ID:");
			String ID = reader.readLine();
			System.out.println("Enter Service Provider Name:");
			String name = reader.readLine();
			System.out.println("Enter Service Provider Location:");
			String location = reader.readLine();
			System.out.println("Enter Service Provider Number:");
			String number = reader.readLine();
			
			
			ServiceProvider provider = new ServiceProvider(ID,name,location,number);
			db.createServiceProvider(provider);
			System.out.print("New service provider added successfully");
				
		}
		else if(decision == 2){
			
			System.out.println("===== Get all service providers in the system =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("|| ProviderID ||\tProviderName ||\tLocation ||\tContact ||");
			       
            db.getInstance().getServiceProviders().stream().forEach((item) -> {
            
//            System.out.print("-------------------------------");
//            System.out.print("PackageID :");
//            System.out.println(item.getId());
//            System.out.print("PackageName :");
//            System.out.println(item.getDataPackageName());
//            System.out.print("Description :");
//            System.out.println(item.getDataPackageDescription());
//            System.out.print("Price :");
//            System.out.println(item.getDataPackagePrice());
//            System.out.print("-------------------------------");
            
            System.out.println("---------------------------------------------------------");
            System.out.println(item.getId()+"\t"+item.getServiceProviderName()+"\t"+item.getServiceProviderLocation()+"\t"+item.getServiceProviderNumber());
   
            } );
			
		}
		else if(decision == 3) {
			
			System.out.println("===== Update existing service provider =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("To Update service providers, please enter the PackageID ");
			String ID = reader.readLine();
			
			boolean Instance = db.getInstance().getServiceProviders().stream().anyMatch(serviceProvider -> serviceProvider.getId() == ID);
			
			if(Instance != false) {
				System.out.println("Enter service provider Name:");
				String name = reader.readLine();
				System.out.println("Enter service provider Location:");
				String loaction = reader.readLine();
				System.out.println("Enter service provider Number:");
				String number = reader.readLine();
				
				ServiceProvider provider = new ServiceProvider(ID,name,loaction,number);
				db.updateServiceProvider(provider);
				System.out.print("Service provider updated successfully");
			}
			else {
                System.out.println("Service provider not found.");
            }
	
		}
		else if(decision == 4) {
			
			System.out.println("===== Delete existing service provider =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("To Delete service providers please enter the ProviderID ");
			String ID = reader.readLine();
			
			boolean Instance = db.getInstance().getServiceProviders().stream().anyMatch(serviceProvider -> serviceProvider.getId() == ID);
			
			if(Instance != false) {
				
				db.removeServiceProvider(ID);
				System.out.print("Service provider Deleted successfully");
			}
			else {
                System.out.println("Service Provider not found.");
            }
			
		}
		else if(decision == 5) {
			
			System.out.println("===== Serch Service Provider =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("To Search Service Providers, please enter the ProviderID or ProviderName ");
			String ID = reader.readLine();
			
			List<IServiceProvider> searchResults = db.searchProvider(ID);
			
			
			if(!searchResults.isEmpty()) {
				
				System.out.println("Search results:");
				
                System.out.format("%-10s | %-10s | %-20s | %-10s | %n", "ProviderID", "ProviderName", "Location" , "Contact");
                for (IServiceProvider result : searchResults) {
                    System.out.format("%-10s | %-20s | %-40s%n", result.getId(), result.getServiceProviderName(), result.getServiceProviderLocation() , result.getServiceProviderNumber());
                }
			}
			else {
                System.out.println("Service provider not found.");
            }

		}
		else if(decision == 6) {
			running = false;
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Good Bye");
		context.ungetService(reference);
	}

}
