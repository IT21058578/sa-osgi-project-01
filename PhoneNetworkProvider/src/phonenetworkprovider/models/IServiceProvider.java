package phonenetworkprovider.models;

public interface IServiceProvider extends IModel {
	
	IUser getUser();
	
	void setUser(IUser userId);
	
	void setServiceProviderID(String providerId);
	
	String getServiceProviderName();
	
	void setServiceProviderName(String serviceProviderName);
	
	String getServiceProviderLocation();
	
	void setServiceProviderLocation(String serviceProviderLocation);
	
	String getServiceProviderNumber();
	
	void setServiceProviderNumber(String serviceProviderNumber);
}
