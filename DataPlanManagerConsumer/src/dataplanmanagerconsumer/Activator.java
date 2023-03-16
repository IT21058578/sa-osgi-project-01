package dataplanmanagerconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import phonenetworkprovider.models.DataPlan;
import phonenetworkprovider.models.IDataPlan;
import phonenetworkprovider.stores.Database;

public class Activator implements BundleActivator {

	private ServiceReference reference;

	public void start(BundleContext context) throws Exception {
		System.out.println("Starting User Portal Consumer...");
		reference = context.getServiceReference(Database.class.getName());
		Database db = (Database) context.getService(reference);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
        
        
       int decision;
        
       db.getInstance().getUsers().stream().forEach((item) -> {System.out.println(item.getName());} );
      
        System.out.println("=========== Welcome to the Data Plan Portal =============");
		System.out.println("Please navigate the menu using the number of the decision");
		System.out.println("=========================================================");
		System.out.println();
		System.out.println();
		
		System.out.println("\t"+"1) Add new data Package to the system");
		System.out.println();
		
		System.out.println("\t"+"2) Get all data Packages in the system");
		System.out.println();
	
		System.out.println("\t"+"3) Update existing data package");
		System.out.println();

		System.out.println("\t"+"4) Delete  data Package to the system");
		System.out.println();
	
		System.out.println("\t"+"5) Search data Packages in the system");
		System.out.println();
		
		System.out.println("\t"+"6) Exit");
		System.out.println();
	
		
		
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
			
			System.out.println("===== Add new data Package =====");
			System.out.println();
			System.out.println("To select the Service Provider refer menu enter the number ");
			
//			System.out.println("Enter data package Service ProviderID:");
//			String ProviderID = reader.readLine();
			System.out.println("Enter data package ID:");
			String ID = reader.readLine();
			System.out.println("Enter data package Name:");
			String name = reader.readLine();
			System.out.println("Enter data package Description:");
			String description = reader.readLine();
			System.out.println("Enter data package Price:");
			String pr = reader.readLine();
			
			//cast to double
			double price = Double.parseDouble(pr);
			
			DataPlan plan = new DataPlan(ID,name,description,price);
			db.createDataPackage(plan);
			System.out.print("New data plan added successfully");
				
		}
		else if(decision == 2){
			
			System.out.println("===== Get all data Packages in the system =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("|| PackageID ||\tPackageName ||\tDescription ||\tPrice ||");
			       
            db.getInstance().getDataPlans().stream().forEach((item) -> {
            
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
            System.out.println(item.getId()+"\t"+item.getDataPackageName()+"\t"+item.getDataPackageName()+"\t"+item.getDataPackagePrice());
   
            } );
			
		}
		else if(decision == 3) {
			
			System.out.println("===== Update existing data package =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("To Update data packages please enter the PackageID ");
			String ID = reader.readLine();
			

			System.out.println("Enter New data package Name:");
			String name = reader.readLine();
			System.out.println("Enter New data package Description:");
			String description = reader.readLine();
			System.out.println("Enter New data package Price:");
			String pr = reader.readLine();
				
			//cast to double
			double price = Double.parseDouble(pr);
			DataPlan plan = new DataPlan(ID,name,description,price);
			db.updateDataPackage(plan);
				
	
		}
		else if(decision == 4) {
			
			System.out.println("===== Delete existing data package =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("To Delete data packages please enter the PackageID ");
			String ID = reader.readLine();
			
//			boolean Instance = db.getInstance().getDataPlans().stream().anyMatch(dataPackage -> dataPackage.getId() == ID);
			
			Database data = (Database) db.getInstance().getDataPackage(ID);
			if(data != null) {
				
				db.deletePackage(ID);
				System.out.print("Data palan Deleted successfully");
			}
			else {
                System.out.println("Data package not found.");
            }
			
		}
		else if(decision == 5) {
			
			System.out.println("===== Serch data package =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("To Search data packages please enter the PackageID or PackageName ");
			String ID = reader.readLine();
			
			List<IDataPlan> searchResults = db.search(ID);
			
			
			if(!searchResults.isEmpty()) {
				
				System.out.println("Search results:");
				
                System.out.format("%-10s | %-10s | %-25s | %-5s | %n", "PackageID", "PackageName", "Description" , "Price");
                for (IDataPlan result : searchResults) {
                    System.out.format("%-10s | %-10s | %-25s | %-5s | %n", result.getId(), result.getDataPackageName(), result.getDataPackageDescription() , result.getDataPackagePrice());
                }
			}
			else {
                System.out.println("Data package not found.");
            }

		}
		else if(decision == 6) {
			running = false;
		}
		
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Good Bye");
		context.ungetService(reference);
	}

}
