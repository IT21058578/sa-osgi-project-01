package phonenetworkprovider.stores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import phonenetworkprovider.models.IDataPlan;
import phonenetworkprovider.models.IMessage;
import phonenetworkprovider.models.IServiceProvider;
import phonenetworkprovider.models.IUser;

public class Database {  
	
    private static final Random random = new Random();
    private static final Set<String> usedPhoneNumbers = new HashSet<>();
    
	private static ArrayList<IUser> users;
    private static ArrayList<IMessage> messages;
    private static ArrayList<IDataPlan> dataPlans;
    private static ArrayList<IServiceProvider> sProviders;
    
    private static Database INSTANCE;
    

	public static ArrayList<IUser> getUsers() {
		return users;
	}

	public static ArrayList<IMessage> getMessages() {
		return messages;
	}

	public static ArrayList<IDataPlan> getDataPlans() {
		return dataPlans;
	}

	public static ArrayList<IServiceProvider> getServiceProviders() {
		return sProviders;
	}

	public void addUser(IUser user) {
		users.add(user);
	}
	
	private Database () {
		this.users = new ArrayList<>();
		this.messages = new ArrayList<>();
		this.dataPlans = new ArrayList<>();
		this.sProviders = new ArrayList<>();		
		
	}
	
	public static Database getInstance() {
		
		if(INSTANCE == null) {
			INSTANCE = new Database();
		}
		return INSTANCE;
		
	}


	// Returns a boolean true is deletion was successful
	public boolean removeUser(String id) {
		return users.removeIf(item -> item.getId() == id);
	}
	
	public boolean removeUser(IUser user) {
		return users.removeIf(item -> item.getId() == user.getId());
	}
	

	public static String generateUniqueId() {
		int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
	}
	
	
	//phone number generation 
	//it's not fully implemented to get the digits from the consumer provider yet
	
    public static String generateUniquePhoneNumber(String digits) {
        String phoneNumber = "";
        do {
            phoneNumber = generatePhoneNumber(digits);
        } while (usedPhoneNumbers.contains(phoneNumber));
        usedPhoneNumbers.add(phoneNumber);
        return phoneNumber;
    }
	
    private static String generatePhoneNumber(String digits) {
        StringBuilder phoneNumber = new StringBuilder();
        phoneNumber.append(digits); // Start with the specified 3 digits
        for (int i = 0; i < 8; i++) {
            phoneNumber.append(random.nextInt(10)); // Append random digit (0-9)
        }
        return phoneNumber.toString();
    }
	
	
	//DataPlan Producer Methords
	
	//01.Delete DataPlan
	
	public List<IDataPlan> deleteDataPackage(String dataPackageID) {
	    List<IDataPlan> removedDataPackages = new ArrayList<>();
	    dataPlans.removeIf(item -> {
	        if (item.getId().equals(dataPackageID)) {
	            removedDataPackages.add(item);
	            return true;
	        }
	        return false;
	    });
	    return removedDataPackages;
	}

	
	
	//02.Create DataPlan
	public void createDataPackage(IDataPlan dataplan) {
		if (dataPlans.stream().anyMatch(dataPackage -> dataPackage.getId() == dataplan.getId() || dataPackage.getDataPackageName().equals(dataplan.getDataPackageName()))) {
            throw new IllegalArgumentException("Data package with that ID or name already exists.");
        }
		dataPlans.add(dataplan);
		
	}

	//03.Get DataPlan by ID
	public IDataPlan getDataPackage(String dataPackageID) {
		return dataPlans.stream().filter(dataPackage -> dataPackage.getId() == dataPackageID).findFirst().orElse(null);
	}

	//04.Update DataPlan
//	public void updateDataPackage(IDataPlan dataplan) {
//		if (dataPlans.stream().anyMatch(dataPackage -> dataPackage.getDataPackageName().equals(dataplan.getDataPackageName()) && dataPackage.getId() != dataplan.getId())) {
//            throw new IllegalArgumentException("Data package with that name already exists.");
//        }
//		
//		IDataPlan existingPackage = dataPlans.stream().filter(dataPackage -> dataPackage.getId() == dataplan.getId()).findFirst().orElse(null);
//        if (existingPackage != null) {
//            existingPackage.setDataPackageName(dataplan.getDataPackageName());
//            existingPackage.setDataPackageDescription(dataplan.getDataPackageDescription());
//            existingPackage.setDataPackagePrice(dataplan.getDataPackagePrice());
//        }
//	}
	
//	public void updateDataPackage(IDataPlan dataplan) {
//	    if (dataPlans.stream().anyMatch(dataPackage -> dataPackage.getDataPackageName().equals(dataplan.getDataPackageName()) && dataPackage.getId() != dataplan.getId())) {
//	        throw new IllegalArgumentException("Data package with that name already exists.");
//	    }
//
//	    IDataPlan existingPackage = (IDataPlan) search(dataplan.getId());
//	    if (existingPackage != null) {
//	    	existingPackage.setDataPackageName(dataplan.getId());
//	        existingPackage.setDataPackageName(dataplan.getDataPackageName());
//	        existingPackage.setDataPackageDescription(dataplan.getDataPackageDescription());
//	        existingPackage.setDataPackagePrice(dataplan.getDataPackagePrice());
//	        System.out.println("+----+---------------------+----------------------+----------------+");
//	        System.out.println("| ID | Data Package Name   | Data Package Descr.  | Price (LkR)    |");
//	        System.out.println("+----+---------------------+----------------------+----------------+");
//	        System.out.printf("| %-2s | %-19s | %-20s | %-11.2s |\n", existingPackage.getId(), existingPackage.getDataPackageName(), existingPackage.getDataPackageDescription(), existingPackage.getDataPackagePrice());
//	        System.out.println("+----+---------------------+----------------------+----------------+");
//	    } else {
//	        System.err.println("Could not find data package with ID " + dataplan.getId());
//	    }
//	}
	
	public List<IDataPlan> updateDataPackage(IDataPlan updatedPackage) {
	    if (dataPlans.stream().anyMatch(dataPackage -> dataPackage.getDataPackageName().equals(updatedPackage.getDataPackageName()) && dataPackage.getId() != updatedPackage.getId())) {
	        throw new IllegalArgumentException("Data package with that name already exists.");
	    }

	    Optional<IDataPlan> matchingPackage = dataPlans.stream().filter(dataPackage -> dataPackage.getId() == updatedPackage.getId()).findFirst();
	    if (matchingPackage.isPresent()) {
	        IDataPlan packageToUpdate = matchingPackage.get();
//	        packageToUpdate.setDataPackageName(updatedPackage.getProvider());
//	        packageToUpdate.setDataPackageName(updatedPackage.getId());
	        packageToUpdate.setDataPackageName(updatedPackage.getDataPackageName());
	        packageToUpdate.setDataPackageDescription(updatedPackage.getDataPackageDescription());
	        packageToUpdate.setDataPackagePrice(updatedPackage.getDataPackagePrice());

	        System.out.println("+-----+-----+---------------------+----------------------+----------------+");
	        System.out.println("| SID | PID | Data Package Name   | Data Package Descr.  | Price (LkR) |");
	        System.out.println("+-----+-----+---------------------+----------------------+----------------+"); 
	        System.out.printf("| %-2s | %-2s | %-19s | %-20s | %-11.2s |\\n",packageToUpdate.getProvider(), packageToUpdate.getId(), packageToUpdate.getDataPackageName(), packageToUpdate.getDataPackageDescription(), packageToUpdate.getDataPackagePrice());
	        System.out.println("+-----+-----+---------------------+----------------------+----------------+"); 

	        return dataPlans;
	    } else {
	        System.err.println("Could not find data package with ID " + updatedPackage.getId());
	        return new ArrayList<IDataPlan>();
	    }
	}


	
	//05.Search DataPlan
	public List<IDataPlan> search(String searchTerm) {
		return dataPlans.stream()
                .filter(dataPackage -> dataPackage.getDataPackageName().toLowerCase().contains(searchTerm.toLowerCase()) || dataPackage.getId().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
    
	
    //Service Provider methods
    
	//01.Delete Service Provider
	public boolean removeSProvider(String providerId) {
		return sProviders.removeIf(sProvider -> sProvider.getId() == providerId);
	}
	
	//02.Create Service Provider
	public void createSProvider(IServiceProvider serProvider) {
		if (sProviders.stream().anyMatch(sProvider -> sProvider.getId() == sProvider.getId() || sProvider.getName().equals(sProvider.getName()))) {
            throw new IllegalArgumentException("Service Provider with that ID or name already exists.");
        }
		sProviders.add(serProvider);
		
	}

	//03.Get all Service Providers
	public IServiceProvider getSProvider(String providerName) {
		return sProviders.stream().filter(sProvider -> sProvider.getName() == providerName).findFirst().orElse(null);
	}

	//04.Update Service Provider
	public void updateSProvider(IServiceProvider serProvider) {
		if (sProviders.stream().anyMatch(sProvider -> sProvider.getName().equals(sProvider.getName()) && sProvider.getId() != sProvider.getId())) {
            throw new IllegalArgumentException("Service Provider with that name already exists.");
        }
		
		IServiceProvider existingProvider = sProviders.stream().filter(sProvider -> sProvider.getId() == sProvider.getId()).findFirst().orElse(null);
        if (existingProvider != null) {
        	existingProvider.setName(serProvider.getName());
        	existingProvider.setLocation(serProvider.getLocation());
        	existingProvider.setNumber(serProvider.getNumber());
        	existingProvider.setDigits(serProvider.getDigits());
        }
	}
	
	//05.Search Service Provider
	public List<IServiceProvider> searchProvider(String searchItem) {
		return sProviders.stream()
                .filter(sProvider -> sProvider.getName().toLowerCase().contains(searchItem.toLowerCase()) || sProvider.getId().toLowerCase().contains(searchItem.toLowerCase()))
                .collect(Collectors.toList());
    }

	
	// Customer Care Center
	
	
		//01.Delete Message
		public List<IMessage> deleteMessage(String messageID) {
		    List<IMessage> removeMessage = new ArrayList<>();
		    messages.removeIf(msg -> {
		        if (msg.getId().equals(messageID)) {
		        	removeMessage.add(msg);
		            return true;
		        }
		        return false;
		    });
		    return removeMessage;
		}
		
		//02.Delete Complaint
		public List<IMessage> deleteComplaint(String compID) {
		    List<IMessage> removeComplaint = new ArrayList<>();
		    messages.removeIf(msg -> {
		        if (msg.getId().equals(compID)) {
		        	removeComplaint.add(msg);
		            return true;
		        }
		        return false;
		    });
		    return removeComplaint;
		}
			
			
		//03.Create Message
			public void createMessage(IMessage message) {
				if (messages.stream().anyMatch(messagedes -> messagedes.getId() == message.getId() || messagedes.getMessageDescription().equals(message.getMessageDescription()))) {
		            throw new IllegalArgumentException("Feedback is already given by the User");
		        }
				messages.add(message);
				
			}
		
		//04.Create Complaint
			public void createComplaint(IMessage complaint) {
				if (messages.stream().anyMatch(messagedes -> messagedes.getId() == complaint.getId() || messagedes.getComplaintDescription().equals(complaint.getComplaintDescription()))) {
				            throw new IllegalArgumentException("Complaint is already given by the User");
				        }
						messages.add(complaint);
						
					}	
		//05.Get all Messages
			public IMessage getMessages(String msg) {
				return messages.stream().filter(message -> message.getMessageDescription() == msg).findFirst().orElse(null);
			}
			
		//06.Get all Messages
					public IMessage getComplaint(String comp) {
						return messages.stream().filter(complaint -> complaint.getComplaintDescription() == comp).findFirst().orElse(null);
					}

		//07.Search Feedbacks
			public List<IMessage> searchMessages(String searchTerm) {
				return messages.stream()
		                .filter(msg -> msg.getMessageDescription().toLowerCase().contains(searchTerm.toLowerCase()) || msg.getId().toLowerCase().contains(searchTerm.toLowerCase()))
		                .collect(Collectors.toList());
		    }
			
		//08.Search DataPlan
					public List<IMessage> searchComlpaint(String searchTerm) {
						return messages.stream()
				                .filter(msg -> msg.getComplaintDescription().toLowerCase().contains(searchTerm.toLowerCase()) || msg.getId().toLowerCase().contains(searchTerm.toLowerCase()))
				                .collect(Collectors.toList());
		    }
}
