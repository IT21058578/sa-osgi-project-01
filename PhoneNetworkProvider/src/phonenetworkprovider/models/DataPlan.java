package phonenetworkprovider.models;

import phonenetworkprovider.stores.Database;

public class DataPlan implements IDataPlan{
	
	private String id;
	private String serviceProviderId;
	private String name;
	private String description;
	private double price; 
	
	public DataPlan(String name, String description, double price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public DataPlan(String serviceProviderId, String name, String description, double price) {
		this(name, description, price);
		this.id = Database.getInstance().generateUniqueId();
		this.serviceProviderId = serviceProviderId;
	}
	
	@Override
	public String getServiceProviderId() {
		return serviceProviderId;
	}

	@Override
	public void setServiceProviderId(String id) {
		this.serviceProviderId = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String getProviderId() {
		return serviceProviderId;
	}

	@Override
    public IServiceProvider getProvider() {
        return Database.getInstance().getServiceProviders().stream()
                .filter(provider -> provider.getId().equals(this.serviceProviderId))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
	
	@Override
    public void setProvider(String providerId) {
        // Check if id is valid.
        boolean isValid = Database.getInstance().getServiceProviders().stream()
                .anyMatch(provider -> provider.getId() == providerId);
        if (!isValid)
            throw new IllegalStateException("Provider id is invalid");
        else {
            this.serviceProviderId = providerId;
        }
    }
}
