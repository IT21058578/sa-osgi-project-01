package phonenetworkprovider.stores;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import phonenetworkprovider.models.IDataPlan;
import phonenetworkprovider.models.IMessage;
import phonenetworkprovider.models.IServiceProvider;
import phonenetworkprovider.models.IUser;

public class Database {  
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
	
	
	//DataPlan Producer Methords
	
	//01.Delete DataPlan
	public boolean deletePackage(String dataPackageID) {
		return dataPlans.removeIf(item -> item.getId() == dataPackageID);
		
//		IDataPlan packageToDelete = dataPlans.stream().filter(dataPackage -> dataPackage.getId() == dataPackageID).findFirst().orElse(null);
//	    if (packageToDelete != null) {
//	    	dataPlans.remove(packageToDelete);
//	    }
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
	
	public void updateDataPackage(IDataPlan dataplan) {
	    if (dataPlans.stream().anyMatch(dataPackage -> dataPackage.getDataPackageName().equals(dataplan.getDataPackageName()) && dataPackage.getId() != dataplan.getId())) {
	        throw new IllegalArgumentException("Data package with that name already exists.");
	    }

	    IDataPlan existingPackage = getDataPackage(dataplan.getId());
	    if (existingPackage != null) {
	    	existingPackage.setDataPackageName(dataplan.getId());
	        existingPackage.setDataPackageName(dataplan.getDataPackageName());
	        existingPackage.setDataPackageDescription(dataplan.getDataPackageDescription());
	        existingPackage.setDataPackagePrice(dataplan.getDataPackagePrice());
	        System.out.println("+----+---------------------+----------------------+----------------+");
	        System.out.println("| ID | Data Package Name   | Data Package Descr.  | Price (LkR) |");
	        System.out.println("+----+---------------------+----------------------+----------------+");
	        System.out.printf("| %-2d | %-19s | %-20s | %-11.2f |\n", existingPackage.getId(), existingPackage.getDataPackageName(), existingPackage.getDataPackageDescription(), existingPackage.getDataPackagePrice());
	        System.out.println("+----+---------------------+----------------------+----------------+");
	    } else {
	        System.err.println("Could not find data package with ID " + dataplan.getId());
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

}
