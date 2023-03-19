package customercareconsumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import phonenetworkprovider.models.IMessage;
import phonenetworkprovider.models.MessageType;
import phonenetworkprovider.stores.Database;

public class Activator implements BundleActivator {

	private ServiceReference<?> reference;

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Starting Customer Care Consumer...");
		reference = bundleContext.getServiceReference(Database.class.getName());
		Database db = (Database) bundleContext.getService(reference);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("=========== Welcome to the Customer Portal =============");
		System.out.println("Use numbers to go to the related menu");
		System.out.println("=========================================================");
		while (true) {

			int decision;

			System.out.println(" 1 -> View All Feedbacks");
			System.out.println(" 2 -> Remove Complaints or Feedback");
			System.out.println(" 3 -> Search Feedbacks or Complaints");
			System.out.println(" 4 -> View All Complaints");
			System.out.println(" 5 -> Exit");
			System.out.println("=========================================================");
			System.out.print("Enter your response: ");
			decision = Integer.parseInt(reader.readLine());

			String id;
			boolean messageDeleted;

			switch (decision) {

			case 1:
				System.out.println("===== All Feedback =====");
				System.out.println();
				System.out.println("---------------------------------------------------------");
				System.out.println("|| FeedbackID ||\tDescription");
				db.getMessages().stream().forEach((msg) -> {
					if (msg.getType().equals(MessageType.Feedback)) {
						System.out.println("----------------------------------------------------");
						System.out.println(msg.getId() + "\t" + msg.getDescription());
						System.out.println("----------------------------------------------------");
					}
				});
				break;
			case 2:
				System.out.println("===== Remove Feedback or Complaint =====");
				System.out.println();
				System.out.println("To Delete message please enter the messageID: ");
				id = reader.readLine();
				System.out.println();
				messageDeleted = db.deleteMessage(id);
				if (messageDeleted)
					System.out.println("Message Deleted Successfully");
				else
					System.out.println("Message Not Found");
				break;

			case 3:
				System.out.println("===== Search Feedback or Complaint =====");
				System.out.println();
				System.out.println("To Search complaints please enter the complaintID: ");
				id = reader.readLine();
				List<IMessage> searchResults = db.searchMessages(id);
				if (!searchResults.isEmpty()) {
					System.out.println("Search results:");
					System.out.format("%-10s | %-10s| %n", "MessageID", "Description");
					for (IMessage result : searchResults) {
						System.out.format("%-10s | %-10s| %n", result.getId(), result.getDescription());
					}
				} else {
					System.out.println("Data package not found.");
				}
				break;
			case 4:

				System.out.println("===== All Complaint =====");
				System.out.println();
				System.out.println("---------------------------------------------------------");
				System.out.println("|| ComplaintkID ||\tDescription");

				db.getMessages().stream().forEach((msg) -> {
					if (msg.getType().equals(MessageType.Complaint)) {
						System.out.println("----------------------------------------------------");
						System.out.println(msg.getId() + "\t" + msg.getDescription());
						System.out.println("----------------------------------------------------");
					}
				});

				break;
			case 5:
				System.out.println("Exited from the Customer Care system");
				return;
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
