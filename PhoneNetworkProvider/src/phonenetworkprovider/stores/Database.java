package phonenetworkprovider.stores;

import java.util.ArrayList;
import java.util.Random;

import phonenetworkprovider.models.IDataPlan;
import phonenetworkprovider.models.IMessage;
import phonenetworkprovider.models.IServiceProvider;
import phonenetworkprovider.models.IUser;

public class Database {  
	private static ArrayList<IUser> users;
    private static ArrayList<IMessage> messages;
    private static ArrayList<IDataPlan> dataPlans;
    private static ArrayList<IServiceProvider> serviceProviders;
	private static Database instance;
	
	private Database() {}
	
	
	
	public static Database getInstance() {
		if (instance == null) {
			synchronized(Database.class) {
				if (instance == null) {	
					users = new ArrayList<>();
					messages = new ArrayList<>();
					dataPlans = new ArrayList<>();
					serviceProviders = new ArrayList<>();
				}
			}
		}
		return instance;
	}
	
	public ArrayList<IUser> getUser() {
		return users;
	}
	
	
	
	public ArrayList<IUser> getUsers() {
		return users;
	}

	public ArrayList<IMessage> getMessages() {
		return messages;
	}

	public ArrayList<IDataPlan> getDataPlans() {
		return dataPlans;
	}

	public ArrayList<IServiceProvider> getServiceProviders() {
		return serviceProviders;
	}

	public void addUser(IUser user) {
		users.add(user);
	}
	
	// Returns a boolean true is deletion was successful
	public boolean removeUser(String id) {
		return users.removeIf(item -> item.getId() == id);
	}
	
	public boolean removeUser(IUser user) {
		return users.removeIf(item -> item.getId() == user.getId());
	}
	
    public String generateUniqueId() {
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
}
