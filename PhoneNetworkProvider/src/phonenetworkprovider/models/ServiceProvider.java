
package phonenetworkprovider.models;

import phonenetworkprovider.stores.Database;

public class ServiceProvider implements IServiceProvider {
	
	private String Id;
	private String name;
	private String digits;
	private String location;
	private String number;
	

	public ServiceProvider(String Id, String name, String digits,
			String location, String number) {
		super();
		this.Id = Id;
		this.name = name;
		this.digits = digits;
		this.location = location;
		this.number = number;
	}
	
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return Id;
	}

	@Override
	public void setId(String Id) {
		// TODO Auto-generated method stub
		this.Id = Id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	public String getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public void setLocation(String location) {
		// TODO Auto-generated method stub
		this.location = location;
	}

	@Override
	public String getNumber() {
		// TODO Auto-generated method stub
		return number;
	}

	@Override
	public void setNumber(String number) {
		// TODO Auto-generated method stub
		this.number = number;
	}

	@Override
	public String getDigits() {
		// TODO Auto-generated method stub
		return digits;
	}

	@Override
	public void setDigits(String digits) {
		// TODO Auto-generated method stub
		this.digits = digits;
	}


	
}
