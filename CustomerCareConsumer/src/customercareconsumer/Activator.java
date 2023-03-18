package customercareconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import phonenetworkprovider.models.IMessage;
import phonenetworkprovider.models.Message;
import phonenetworkprovider.stores.Database;

public class Activator implements BundleActivator {

//	private static BundleContext context;
	private ServiceReference reference;


	public void start(BundleContext bundleContext) throws Exception {
		
		System.out.println("Starting Customer Care Consumer...");
		
//        boolean running = true;
        
        System.out.println("=========== Welcome to the Customer Portal =============");
		System.out.println("Use numbers to go to the related menu");
		System.out.println("=========================================================");
		reference = bundleContext.getServiceReference(Database.class.getName());
		@SuppressWarnings("unchecked")
		Database db = (Database) bundleContext.getService(reference);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			 
	       int decision;
			
			System.out.println(" 1 -> View All Feedbacks");
			System.out.println(" 2 -> Remove Feedbacks");
			System.out.println(" 3 -> Search Feedbacks");
			System.out.println(" 4 -> View All Complaints");
			System.out.println(" 5 -> Remove Complaints");
			System.out.println(" 6 -> Search Complaints");
			System.out.println(" 7 -> Exit");
			System.out.println("=========================================================");
			System.out.print("Enter your response: ");
			decision = Integer.parseInt(reader.readLine());
	switch (decision) {

		 
		case 1: 
			
			System.out.println("===== All Feedback =====");
			System.out.println();
			System.out.println("---------------------------------------------------------");
			System.out.println("|| FeedbackID ||\tDescription");
			
			db.getInstance().getMessages().stream().forEach((msg)->{
				
				System.out.println("----------------------------------------------------");
	            System.out.println(msg.getId()+"\t"+msg.getMessageDescription());
	            System.out.println("----------------------------------------------------");

			});
			
			break;
			
		case 2:
			
			System.out.println("===== Remove Feedback =====");
			System.out.println();
			System.out.println("To Delete message please enter the messageID: ");
			String ID = reader.readLine();
			System.out.println();
			
			List<IMessage> msg = db.deleteMessage(ID);
			
			if(msg != null) {
				System.out.println("Message Deleted Successfully");
			}
			else {
				System.out.println("Message Not Found");
			}
		
			
		break;
	
		case 3:
	
	System.out.println("===== Search Feedback =====");
	System.out.println();
	
	System.out.println("To Search complaints please enter the complaintID: ");
	String id1 = reader.readLine();
	
	List<IMessage> searchResults = db.searchMessages(id1);
	
	
	if(!searchResults.isEmpty()) {
		
		System.out.println("Search results:");
		
        System.out.format("%-10s | %-10s| %n", "MessageID", "Description");
        for (IMessage result : searchResults) {
            System.out.format("%-10s | %-10s| %n", result.getId(), result.getMessageDescription());
        }
	}
	else {
        System.out.println("Data package not found.");
    }
	
	break;
		
	case 4:
	
	System.out.println("===== All Complaint =====");
	System.out.println();
	System.out.println("---------------------------------------------------------");
	System.out.println("|| ComplaintkID ||\tDescription");
	
	db.getInstance().getMessages().stream().forEach((msg1)->{
		
		System.out.println("----------------------------------------------------");
        System.out.println("\t\t"+msg1.getId()+"\t\t\t\t"+msg1.getComplaintDescription());

	});
	
	break;
	
	case 5:
			
			System.out.println("===== Remove Complaint =====");
			System.out.println();
			
			System.out.println("To Delete message please enter the complaintID ");
			String ID2 = reader.readLine();
			boolean Instance = db.getInstance().getMessages().stream().anyMatch(msg2 -> msg2.getId() == ID2);
			
			List<IMessage> msg4 = db.deleteComplaint(ID2);
			
			if(msg4 != null) {
				System.out.println("Message Deleted Successfully");
			}
			else {
				System.out.println("Message Not Found");
			}
		
			
		break;
		
		
	case 6:
			
			System.out.println("===== Search Complaint =====");
			System.out.println();
			System.out.println("To Search complaints please enter the ComplainttID: ");
			String id4 = reader.readLine();
			
			List<IMessage> searchResults1 = db.searchComlpaint(id4);
			
			
			if(!searchResults1.isEmpty()) {
				
				System.out.println("Search results:");
				
		        System.out.format("%-10s | %-10s | %n", "ComplaintID", "Description" );
		        for (IMessage result : searchResults1) {
		            System.out.format("%-10s | %-20s | %n", result.getId(), result.getComplaintDescription());
		        }
			}
			else {
		        System.out.println("Data package not found.");
		    }
			
			
	break;
	
	case 7:
		System.out.println("Exited from the Customer Care system");
		return ;
	
	default:
		
		System.out.println("Error");
		break;
}
		
	}
	}
	
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("==== Exiting From Customer Care Admin ====");
		bundleContext.ungetService(reference);
	}
}	
