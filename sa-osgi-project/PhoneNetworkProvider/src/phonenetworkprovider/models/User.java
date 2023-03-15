package phonenetworkprovider.models;

import java.util.ArrayList;

import phonenetworkprovider.stores.Database;

public class User implements IUser {
    private String id;
    private String name;
    private String address;
    private String serviceProviderId;
    private ArrayList<String> dataPlanIds;

    public User(String name, String address, String serviceProviderId) {
        super();
        this.name = name;
        this.address = address;
        this.dataPlanIds = new ArrayList<>();
        this.id = Database.generateUniqueId();
    }

    public User(String name, String address, String serviceProviderId, ArrayList<String> dataPlanIds) {
        this(name, address, serviceProviderId);
        this.dataPlanIds = dataPlanIds;
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
        return Database.getServiceProviders().stream()
                .filter(provider -> provider.getId() == this.serviceProviderId)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public void setProvider(String providerId) {
        // Check if id is valid.
        boolean isValid = Database.getServiceProviders().stream()
                .anyMatch(provider -> provider.getId() == providerId);
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
            dataPlans.add(Database.getDataPlans().stream()
                    .filter(dataPlan -> dataPlan.getId() == id)
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
}
