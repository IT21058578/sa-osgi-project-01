package phonenetworkprovider.stores;

import java.util.ArrayList;
import java.util.List;
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
    private static ArrayList<IServiceProvider> serviceProviders;
    
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
		return serviceProviders;
	}

	public void addUser(IUser user) {
		users.add(user);
	}
	
	private Database () {
		this.users = new ArrayList<>();
		this.messages = new ArrayList<>();
		this.dataPlans = new ArrayList<>();
		this.serviceProviders = new ArrayList<>();		
		
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
		return dataPlans.removeIf(dataPackage -> dataPackage.getId() == dataPackageID);
	}
	
	//02.Create DataPlan
	public void createDataPackage(IDataPlan dataplan) {
		if (dataPlans.stream().anyMatch(dataPackage -> dataPackage.getId() == dataplan.getId() || dataPackage.getDataPackageName().equals(dataplan.getDataPackageName()))) {
            throw new IllegalArgumentException("Data package with that ID or name already exists.");
        }
		dataPlans.add(dataplan);
		
	}

	//03.Get all DataPlan
	public IDataPlan getDataPackage(String dataPackageName) {
		return dataPlans.stream().filter(dataPackage -> dataPackage.getDataPackageName() == dataPackageName).findFirst().orElse(null);
	}

	//04.Update DataPlan
	public void updateDataPackage(IDataPlan dataplan) {
		if (dataPlans.stream().anyMatch(dataPackage -> dataPackage.getDataPackageName().equals(dataplan.getDataPackageName()) && dataPackage.getId() != dataplan.getId())) {
            throw new IllegalArgumentException("Data package with that name already exists.");
        }
		
		IDataPlan existingPackage = dataPlans.stream().filter(dataPackage -> dataPackage.getId() == dataplan.getId()).findFirst().orElse(null);
        if (existingPackage != null) {
            existingPackage.setDataPackageName(dataplan.getDataPackageName());
            existingPackage.setDataPackageDescription(dataplan.getDataPackageDescription());
            existingPackage.setDataPackagePrice(dataplan.getDataPackagePrice());
        }
	}
	
	//05.Search DataPlan
	public List<IDataPlan> search(String searchTerm) {
		return dataPlans.stream()
                .filter(dataPackage -> dataPackage.getDataPackageName().toLowerCase().contains(searchTerm.toLowerCase()) || dataPackage.getId().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
    

}
