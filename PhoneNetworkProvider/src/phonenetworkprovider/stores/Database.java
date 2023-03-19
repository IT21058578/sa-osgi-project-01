package phonenetworkprovider.stores;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

import phonenetworkprovider.models.IDataPlan;
import phonenetworkprovider.models.IMessage;
import phonenetworkprovider.models.IServiceProvider;
import phonenetworkprovider.models.IUser;
import phonenetworkprovider.models.MessageType;

public class Database {

	private static final Random random = new Random();
	private static final Set<String> usedPhoneNumbers = new HashSet<>();

	private ArrayList<IUser> users;
	private ArrayList<IMessage> messages;
	private ArrayList<IDataPlan> dataPlans;
	private ArrayList<IServiceProvider> sProviders;

	private static Database INSTANCE;

	private Database() {
		this.users = new ArrayList<>();
		this.messages = new ArrayList<>();
		this.dataPlans = new ArrayList<>();
		this.sProviders = new ArrayList<>();
	}

	public static Database getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Database();
		}
		return INSTANCE;

	}

	public String generateUniqueId() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	public ArrayList<IUser> getUsers() {
		return this.users;
	}

	public ArrayList<IMessage> getMessages() {
		return this.messages;
	}

	public ArrayList<IDataPlan> getDataPlans() {
		return this.dataPlans;
	}

	public ArrayList<IServiceProvider> getServiceProviders() {
		return this.sProviders;
	}

	public void addUser(IUser user) {
		users.add(user);
	}

	// Returns a boolean true is deletion was successful
	public boolean removeUser(String id) {
		return users.removeIf(item -> item.getId().equals(id));
	}

	public boolean removeUser(IUser user) {
		return users.removeIf(item -> item.getId().equals(user.getId()));
	}

	// Generating a phone number
	public String generateUniquePhoneNumber(String digits) {
		String phoneNumber = "";
		do {
			phoneNumber = generatePhoneNumber(digits);
		} while (usedPhoneNumbers.contains(phoneNumber));
		usedPhoneNumbers.add(phoneNumber);
		return phoneNumber;
	}

	private String generatePhoneNumber(String digits) {
		StringBuilder phoneNumber = new StringBuilder();
		phoneNumber.append(digits); // Start with the specified 3 digits
		for (int i = 0; i < 8; i++) {
			phoneNumber.append(random.nextInt(10)); // Append random digit (0-9)
		}
		return phoneNumber.toString();
	}

	// DataPlan Producer Methods

	// 01.Delete DataPlan

	public boolean deleteDataPackage(String id) {
		return dataPlans.removeIf(item -> item.getId().equals(id));
	}

	// 02.Create DataPlan
	public void createDataPackage(IDataPlan dataplan) {
		if (dataPlans.stream().anyMatch(dataPackage -> (dataPackage.getId() == dataplan.getId())
				|| dataPackage.getName().equals(dataplan.getName()))) {
			throw new IllegalArgumentException("Data package with that ID or name already exists.");
		}
		dataPlans.add(dataplan);

	}

	// 03.Get DataPlan by ID
	public IDataPlan getDataPackage(String id) {
		return dataPlans.stream().filter(dataPackage -> dataPackage.getId().equals(id)).findFirst().orElse(null);
	}

	public boolean updateDataPackage(String id, IDataPlan updatedPackage) {
		// Find a package, updates it and returns true. If not; returns false.
		try {
			var packageToUpdate = dataPlans.stream().filter(item -> item.getId().equals(id)).findFirst()
					.orElseThrow(IllegalStateException::new);
			packageToUpdate.setName(updatedPackage.getName());
			packageToUpdate.setDescription(updatedPackage.getDescription());
			packageToUpdate.setPrice(updatedPackage.getPrice());
			System.out.println("Package succesfully updated");
			return true;
		} catch (Exception e) {
			System.err.println("Could not find package with specified id");
			return false;
		}
	}

	// 05.Search DataPlan
	public List<IDataPlan> search(String searchTerm) {
		return dataPlans.stream()
				.filter(dataPackage -> dataPackage.getName().toLowerCase().contains(searchTerm.toLowerCase())
						|| dataPackage.getId().toLowerCase().contains(searchTerm.toLowerCase()))
				.collect(Collectors.toList());
	}

	// Service Provider methods

	// 01.Delete Service Provider
	public boolean removeProvider(String providerId) {
		return sProviders.removeIf(sProvider -> sProvider.getId().equals(providerId));
	}

	// 02.Create Service Provider
	public void createProvider(IServiceProvider serviceProvider) {
		if (sProviders.stream().anyMatch(item -> item.getId().equals(serviceProvider.getId())
				|| item.getName().equals(serviceProvider.getName()))) {
			throw new IllegalArgumentException("Service Provider with that ID or name already exists.");
		}
		sProviders.add(serviceProvider);
	}

	// 03.Get all Service Providers
	public IServiceProvider getSProvider(String providerName) {
		return sProviders.stream().filter(sProvider -> sProvider.getName().equals(providerName)).findFirst()
				.orElse(null);
	}

	// 04.Update Service Provider
	public void updateSProvider(IServiceProvider serProvider) {
		if (sProviders.stream().anyMatch(sProvider -> sProvider.getName().equals(serProvider.getName())
				&& sProvider.getId().equals(serProvider.getId()))) {
			throw new IllegalArgumentException("Service Provider with that name already exists.");
		}

		IServiceProvider existingProvider = sProviders.stream()
				.filter(sProvider -> sProvider.getId().equals(serProvider.getId())).findFirst().orElse(null);
		if (existingProvider != null) {
			existingProvider.setName(serProvider.getName());
			existingProvider.setLocation(serProvider.getLocation());
			existingProvider.setNumber(serProvider.getNumber());
			existingProvider.setDigits(serProvider.getDigits());
		}
	}

	// 05.Search Service Provider
	public List<IServiceProvider> searchProvider(String searchItem) {
		return sProviders.stream()
				.filter(sProvider -> sProvider.getName().toLowerCase().contains(searchItem.toLowerCase())
						|| sProvider.getId().toLowerCase().contains(searchItem.toLowerCase()))
				.collect(Collectors.toList());
	}

	// Customer Care Center

	public void deleteMessagesByUser(String userId) {
		messages.removeIf(message -> message.getUserId().equals(userId));
	}

	public boolean deleteMessage(String id) {
		return messages.removeIf(message -> message.getId().equals(id));
	}

	public void createMessage(IMessage message) {
		messages.add(message);
	}

	public IMessage getMessages(String msg) {
		return messages.stream().filter(message -> message.getDescription().equals(msg)).findFirst().orElse(null);
	}

	public List<IMessage> searchMessagesByType(String searchTerm, MessageType type) {
		return messages.stream()
				.filter(msg -> (msg.getDescription().toLowerCase().contains(searchTerm.toLowerCase())
						|| msg.getId().toLowerCase().contains(searchTerm.toLowerCase())) && msg.getType().equals(type))
				.collect(Collectors.toList());
	}
	
	public List<IMessage> searchMessages(String searchTerm) {
		return messages.stream()
				.filter(msg -> (msg.getDescription().toLowerCase().contains(searchTerm.toLowerCase())
						|| msg.getId().toLowerCase().contains(searchTerm.toLowerCase())))
				.collect(Collectors.toList());
	}
}
