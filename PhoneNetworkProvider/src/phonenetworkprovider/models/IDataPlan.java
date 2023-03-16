package phonenetworkprovider.models;

public interface IDataPlan extends IModel {

//	int getDataPackageID();
	
//	IServiceProvider getProvider();
//	
//	void setProvider(String providerId);
	
	void setDataPackageID(String dataPackageID);
	
	String getDataPackageName();
	
	void setDataPackageName(String dataPackageName);
	
	String getDataPackageDescription();
	
	void setDataPackageDescription(String dataPackageDescription);
	
	double getDataPackagePrice();
	
	void setDataPackagePrice(double dataPackagePrice);

	
}
