package phonenetworkprovider.models;

public interface IServiceProvider extends IModel {
	
	String getId();
	
	void setId(String Id);
	
	String getName();
	
	void setName(String name);
	
	String getDigits();
	
	void setDigits(String digits);
	
	String getLocation();
	
	void setLocation(String location);
	
	String getNumber();
	
	void setNumber(String number);
}
