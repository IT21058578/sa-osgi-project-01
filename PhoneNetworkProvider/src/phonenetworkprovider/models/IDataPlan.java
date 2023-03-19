package phonenetworkprovider.models;

public interface IDataPlan extends IModel {
	
	IServiceProvider getProvider();
	
	void setProvider(String providerId);

	String getName();
	
	void setName(String dataPackageName);
	
	String getDescription();
	
	void setDescription(String dataPackageDescription);
	
	double getPrice();
	
	void setPrice(double dataPackagePrice);

	String getServiceProviderId();
	
	void setServiceProviderId(String id);

	String getProviderId();
}
