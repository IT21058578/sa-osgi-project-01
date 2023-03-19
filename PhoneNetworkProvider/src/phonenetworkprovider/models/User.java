package phonenetworkprovider.models;

import java.util.ArrayList;

import phonenetworkprovider.stores.Database;

public class User implements IUser {
    private String id;
    private String name;
    private String mobile;
    private String address;
    private String serviceProviderId;
    private ArrayList<String> dataPlanIds;

    public User(String name, String address, String serviceProviderId, String mobile) {
        super();
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.dataPlanIds = new ArrayList<>();
        this.serviceProviderId = serviceProviderId;
        this.id = Database.getInstance().generateUniqueId();
    }

    public User(String name, String address, String serviceProviderId, String mobile, ArrayList<String> dataPlanIds) {
        this(name, address, serviceProviderId, mobile);
        this.dataPlanIds.addAll(dataPlanIds);
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
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public IServiceProvider getProvider() {
    	System.out.println(this.serviceProviderId);
        return Database.getInstance().getServiceProviders().stream()
                .filter(provider ->{
                	System.out.println(provider.getId());
                	return provider.getId().equals(this.serviceProviderId); })
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public void setProvider(String providerId) {
        // Check if id is valid.
        boolean isValid = Database.getInstance().getServiceProviders().stream()
                .anyMatch(provider -> provider.getId().equals(providerId));
        if (!isValid)
            throw new IllegalStateException("Provider id is invalid");
        else {
            this.serviceProviderId = providerId;
        }
    }

    @Override
    public ArrayList<IDataPlan> getPlans() {
        // Use dataPlanIds to get the dataPlan details for the relevant user
        ArrayList<IDataPlan> dataPlans = new ArrayList<>();
        dataPlanIds.stream().forEach((id) -> {
            dataPlans.add(Database.getInstance().getDataPlans().stream()
                    .filter(dataPlan -> dataPlan.getId().equals(id))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new));
        });
        return dataPlans;
    }

    @Override
    public void addPlan(String planId) {
        dataPlanIds.add(planId);
    }

    @Override
    public void removePlan(String planId) {
        dataPlanIds.removeIf(id -> planId.equals(id));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
	public String getMobile() {
		return mobile;
	}

    @Override
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
    
    @Override
	public String getServiceProviderId() {
		return serviceProviderId;
	}
}
