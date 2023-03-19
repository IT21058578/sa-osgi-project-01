
package phonenetworkprovider.models;

import phonenetworkprovider.stores.Database;

public class ServiceProvider implements IServiceProvider {

	private String Id;
	private String name;
	private String digits;
	private String location;
	private String number;

	public ServiceProvider(String name, String digits, String location, String number) {
		super();
		this.Id = Database.getInstance().generateUniqueId();
		this.name = name;
		this.digits = digits;
		this.location = location;
		this.number = number;
	}
	
	public ServiceProvider(String id, String name, String digits, String location, String number) {
		super();
		this.Id = id;
		this.name = name;
		this.digits = digits;
		this.location = location;
		this.number = number;
	}

	@Override
	public String getId() {
		return Id;
	}

	@Override
	public void setId(String Id) {
		this.Id = Id;
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
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String getDigits() {
		return digits;
	}

	@Override
	public void setDigits(String digits) {
		this.digits = digits;
	}

}
