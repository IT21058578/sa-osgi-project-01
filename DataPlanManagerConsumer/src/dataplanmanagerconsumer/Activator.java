package dataplanmanagerconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
		System.out.println("==============================================================");
		System.out.println("==============Starting Data Plan Portal Consumer==============");
		System.out.println("==============================================================");
		System.out.println();
		reference = context.getServiceReference(Database.class.getName());
		Database db = (Database) context.getService(reference);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			
			
			int decision;
	        
	        System.out.println("=========== Welcome to the Data Plan Portal =============");
			System.out.println("Please navigate the menu using the number of the decision");
			System.out.println("=========================================================");
			System.out.println("|                                                       |");
			System.out.println("|                                                       |");
			System.out.println("|"+"-|-"+"1) Add new data Package to the system"+"               |");
			System.out.println("|                                                       |");
			System.out.println("|"+"-|-"+"2) Get all data Packages in the system"+"              |");
			System.out.println("|                                                       |");
			System.out.println("|"+"-|-"+"3) Update existing data package"+"                     |");
			System.out.println("|                                                       |");
			System.out.println("|"+"-|-"+"4) Delete  data Package to the system"+"               |");
			System.out.println("|                                                       |");
			System.out.println("|"+"-|-"+"5) Search data Packages in the system"+"               |");
			System.out.println("|                                                       |");
			System.out.println("|"+"-|-"+"6) Exit"+"                                             |");
			System.out.println("|                                                       |");
			System.out.println("=========================================================");
	       
			decision = Integer.parseInt(reader.readLine());
			
		
		switch (decision) {
		case 1:
			
			System.out.println("===== Add new data Package =====");
			System.out.println();
			System.out.println("To select the Service Provider refer menu enter the number ");
			System.out.println("==========================================================");
			System.out.println();
			System.out.println("+-----+---------------------+");
	        System.out.println("| SID |   Provider Name     |");
	        System.out.println("+-----+---------------------+");     
	        db.getInstance().getServiceProviders().stream().forEach((provider) -> {         
	        System.out.printf("| %-2s | %-19s |\n",provider.getId(),provider.getName());
	        System.out.println("+-----+---------------------+"); 
	        } );
	        System.out.println();
			System.out.print("1->"+"Enter data package Service ProviderID:");
			String ProviderID = reader.readLine();
			System.out.print("2->"+"Enter data package ID:");
			String ID = reader.readLine();
			System.out.print("3->"+"Enter data package Name:");
			String name = reader.readLine();
			System.out.print("4->"+"Enter data package Description:");
			String description = reader.readLine();
			System.out.print("5->"+"Enter data package Price:");
			String pr = reader.readLine();
			
			//cast to double
			double price = Double.parseDouble(pr);
			
			DataPlan plan = new DataPlan(ProviderID,ID,name,description,price);
			db.createDataPackage(plan);
			System.out.println("=========================================================");
			System.out.println("New data plan added successfully");
			System.out.println("=========================================================");
			System.out.println();
			System.out.println("Press Any key to Go back to Home Screen");
			reader.readLine();
			System.out.println("---------------------------------------");
			System.out.println();
			
			break;	
		
		case 2:
			
			System.out.println("===== Get all data Packages in the system =====");
			ArrayList<IDataPlan> data =  db.getDataPlans();
			if( data != null) {
				System.out.println("+-----+-----+---------------------+----------------------+----------------+");
		        System.out.println("| SID | PID | Data Package Name   | Data Package Descr.  | Price (LkR) |");
		        System.out.println("+-----+-----+---------------------+----------------------+----------------+");     
	            db.getInstance().getDataPlans().stream().forEach((item) -> {         
	            System.out.printf("| %-2s | %-2s | %-19s | %-20s | %-11.2s |\n",item.getProvider(),item.getId(),item.getDataPackageName(),item.getDataPackageName(),item.getDataPackagePrice());
	            System.out.println("+-----+-----+---------------------+----------------------+----------------+"); 
	            } );	
	            System.out.println();
				System.out.println("Press Any key to Go back to Home Screen");
				reader.readLine();
				System.out.println("---------------------------------------");
				System.out.println();
			}	
			else {
				System.out.println("======================");
                System.out.println("==No data available===");
                System.out.println("======================");
                System.out.println();
    			System.out.println("Press Any key to Go back to Home Screen");
    			reader.readLine();
    			System.out.println("---------------------------------------");
    			System.out.println();
			}
			break;
		
		case 3:
			
			System.out.println("===== Update existing data package =====");
			System.out.println("---------------------------------------------------------");
			System.out.println();
			System.out.print("To Update data packages please enter the Package ID :");
			String ID1 = reader.readLine();
			System.out.println("---------------------------------------------------------");
			System.out.print("Enter New data package Name:");
			String name1 = reader.readLine();
			System.out.print("Enter New data package Description:");
			String description1 = reader.readLine();
			System.out.print("Enter New data package Price:");
			String pr1 = reader.readLine();
				
			//cast to double
			double price1 = Double.parseDouble(pr1);
			DataPlan plan1 = new DataPlan(name1,description1,price1);
			db.updateDataPackage(plan1);
			System.out.println();
			System.out.println("Press Any key to Go back to Home Screen");
			reader.readLine();
			System.out.println("---------------------------------------");
			System.out.println();
			break;	
	
		case 4:
			
			System.out.println("===== Delete existing data package =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("To Delete data packages please enter the PackageID ");
			String ID2 = reader.readLine().toLowerCase();
			System.out.println();
						
			List<IDataPlan> data1 = db.deleteDataPackage(ID2);
			if(data1 != null) {
				System.out.println("==============================");
				System.out.println("Data palan Deleted successfully");
				System.out.println("==============================");
				System.out.println();
				System.out.println("Press Any key to Go back to Home Screen");
				reader.readLine();
				System.out.println("---------------------------------------");
				System.out.println();
			}
			else {
				System.out.println("======================");
                System.out.println("Data package not found");
                System.out.println("======================");
                System.out.println();
    			System.out.println("Press Any key to Go back to Home Screen");
    			reader.readLine();
    			System.out.println("---------------------------------------");
    			System.out.println();
            }
			break;
		

		case 5:
			
			System.out.println("===== Serch data package =====");
			System.out.println("---------------------------------------------------------");
			System.out.println("To Search data packages please enter the PackageID or PackageName :");
			String ID3 = reader.readLine();
			System.out.println();
			
			List<IDataPlan> searchResults = db.search(ID3);
			
			
			if(!searchResults.isEmpty()) {
				
					System.out.println("========= Search results =========");
					System.out.println("+-----+-----+---------------------+----------------------+----------------+");
			        System.out.println("| SID | PID | Data Package Name   | Data Package Descr.  | Price (LkR) |");
			        System.out.println("+-----+-----+---------------------+----------------------+----------------+"); 
                for (IDataPlan result : searchResults) {
                	System.out.printf("| %-2s | %-2s | %-19s | %-20s | %-11.2s |\n",result.getProvider(), result.getId(), result.getDataPackageName(), result.getDataPackageDescription() , result.getDataPackagePrice());
                	System.out.println("+-----+-----+---------------------+----------------------+----------------+");
                    
                }
                System.out.println();
    			System.out.println("Press Any key to Go back to Home Screen");
    			reader.readLine();
    			System.out.println("---------------------------------------");
    			System.out.println();
			}
			else {
				System.out.println("======================");
                System.out.println("Data package not found");
                System.out.println("======================");
                System.out.println();
    			System.out.println("Press Any key to Go back to Home Screen");
    			reader.readLine();
    			System.out.println("---------------------------------------");
    			System.out.println();
            }
			break;

		case 6:
			System.out.println("Exiting program...");
            return;
            
        default:
        	System.out.println("Invalid input. Please try again.");
            break;
		}}
        
		
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("==========");
		System.out.println("Good Bye");
		System.out.println("==========");
		context.ungetService(reference);
	}

}
