
package phonenetworkprovider.models;

import phonenetworkprovider.stores.Database;

public class ServiceProvider implements IServiceProvider {
	
	private IUser userId;
	private String serviceProviderId;
	private String serviceProviderName;
	private String serviceProviderLocation;
	private String serviceProviderNumber;
	

	public ServiceProvider(IUser userId, String serviceProviderId, String serviceProviderName,
			String serviceProviderLocation, String serviceProviderNumber) {
		super();
		this.userId = userId;
		this.serviceProviderId = serviceProviderId;
		this.serviceProviderName = serviceProviderName;
		this.serviceProviderLocation = serviceProviderLocation;
		this.serviceProviderNumber = serviceProviderNumber;
	}
	
	public ServiceProvider(String serviceProviderId, String serviceProviderName,
			String serviceProviderLocation, String serviceProviderNumber) {
		this.serviceProviderId = serviceProviderId;
		this.serviceProviderName = serviceProviderName;
		this.serviceProviderLocation = serviceProviderLocation;
		this.serviceProviderNumber = serviceProviderNumber;
	}
	
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return serviceProviderId;
	}

	@Override
	public void setServiceProviderID(String serviceProviderId) {
		// TODO Auto-generated method stub
		this.serviceProviderId = serviceProviderId;
	}

	@Override
	public String getServiceProviderName() {
		// TODO Auto-generated method stub
		return serviceProviderName;
	}

	@Override
	public void setServiceProviderName(String serviceProviderName) {
		// TODO Auto-generated method stub
		this.serviceProviderName = serviceProviderName;
	}

	@Override
	public String getServiceProviderLocation() {
		// TODO Auto-generated method stub
		return serviceProviderLocation;
	}

	@Override
	public void setServiceProviderLocation(String serviceProviderLocation) {
		// TODO Auto-generated method stub
		this.serviceProviderLocation = serviceProviderLocation;
	}

	@Override
	public String getServiceProviderNumber() {
		// TODO Auto-generated method stub
		return serviceProviderNumber;
	}

	@Override
	public void setServiceProviderNumber(String serviceProviderNumber) {
		// TODO Auto-generated method stub
		this.serviceProviderNumber = serviceProviderNumber;
	}

	@Override
	public IUser getUser() {
		// TODO Auto-generated method stub
		return userId;
//        return Database.getUsers().stream()
//                .filter(user -> user.getId() == this.userId)
//                .findFirst()
//                .orElseThrow(IllegalStateException::new);
	}

	public void setUser(IUser userId) {
		// TODO Auto-generated method stub
		this.userId = userId;
//        boolean isValid = Database.getUsers().stream()
//                .anyMatch(user -> user.getId() == userId);
//        if (!isValid)
//            throw new IllegalStateException("User id is invalid");
//        else {
//            this.userId = userId;
//        }
	}

	
}
