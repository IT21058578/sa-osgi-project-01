package phonenetworkprovider.models;

import java.util.ArrayList;

public interface IUser extends IModel {
	String getMobile();
	
	void setMobile(String mobile);
	
	String getName();

	void setName(String name);

	String getAddress();

	void setAddress(String address);

	IServiceProvider getProvider();

	void setProvider(String providerId);

	ArrayList<IDataPlan> getPlans();

	void addPlan(String planId);

	void removePlan(String planId);
	
	String getServiceProviderId();
}
