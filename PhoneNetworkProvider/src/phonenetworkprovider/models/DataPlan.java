package phonenetworkprovider.models;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import phonenetworkprovider.stores.Database;

public class DataPlan implements IDataPlan{
	
	private String id;
	private String serviceProviderId;
	private String dataPackageID;
	private String dataPackageName;
	private String dataPackageDescription;
	private double dataPackagePrice; 
	

	public DataPlan(String id, String serviceProviderId, String dataPackageID, String dataPackageName,
			String dataPackageDescription, double dataPackagePrice) {
		super();
		this.id = id;
		this.serviceProviderId = serviceProviderId;
		this.dataPackageID = dataPackageID;
		this.dataPackageName = dataPackageName;
		this.dataPackageDescription = dataPackageDescription;
		this.dataPackagePrice = dataPackagePrice;
	}

	public DataPlan(String serviceProviderId,String dataPackageID, String dataPackageName, String dataPackageDescription ,double dataPackagePrice) {
		this.serviceProviderId = serviceProviderId;
		this.dataPackageID = dataPackageID;
		this.dataPackageName = dataPackageName;
		this.dataPackageDescription = dataPackageDescription;
		this.dataPackagePrice = dataPackagePrice;
	}
	
	public DataPlan(String dataPackageName, String dataPackageDescription ,double dataPackagePrice) {

		this.dataPackageName = dataPackageName;
		this.dataPackageDescription = dataPackageDescription;
		this.dataPackagePrice = dataPackagePrice;
	}
	
	public DataPlan(String dataPackageID) {
		this.dataPackageID = dataPackageID;
	}

	@Override
	public String getId() {
		
		return dataPackageID;
	}


//	@Override
//	public int getDataPackageID() {
//		return dataPackageID;
//	}


	@Override
	public void setDataPackageID(String dataPackageID) {
		this.dataPackageID = dataPackageID;
		
	}


	@Override
	public String getDataPackageName() {
		return dataPackageName;
	}


	@Override
	public void setDataPackageName(String dataPackageName) {
		this.dataPackageName = dataPackageName;
		
	}


	@Override
	public String getDataPackageDescription() {
		return dataPackageDescription;
	}


	@Override
	public void setDataPackageDescription(String dataPackageDescription) {
		this.dataPackageDescription = dataPackageDescription;	
	}

	@Override
	public double getDataPackagePrice() {
		return dataPackagePrice;
	}


	@Override
	public void setDataPackagePrice(double dataPackagePrice) {
		this.dataPackagePrice =  dataPackagePrice;
		
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
	
}
