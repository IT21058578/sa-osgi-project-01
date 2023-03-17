package phonenetworkprovider.models;

import phonenetworkprovider.stores.Database;

public class Message implements IMessage{
	
	private String serviceProviderId;
	private String messageID;// dataPackageID;
//	private String dataPackageName;
	private String messageDescription; //dataPackageDescription;
//	private double dataPackagePrice; 
	private String compID;// dataPackageID;
	private String compDescription; //dataPackageDescription;
	private String type;

	
	

	public Message(String messageID, String messageDescription) {
		super();
		this.messageID = messageID;
		this.messageDescription = messageDescription;
	}

	public Message(String serviceProviderId, String messageID, String messageDescription, String compID,
			String compDescription, String type) {
		super();
		this.serviceProviderId = serviceProviderId;
		this.messageID = messageID;
		this.messageDescription = messageDescription;
		this.compID = compID;
		this.compDescription = compDescription;
		this.type = type;
	}
	
	public Message(String serviceProviderId, String compID, String compDescription) {
		super();
		this.serviceProviderId = serviceProviderId;
		this.compID = compID;
		this.compDescription = compDescription;
	}
	
	

	public Message(String serviceProviderId, String messageID, String messageDescription, String type) {
		super();
		this.serviceProviderId = serviceProviderId;
		this.messageID = messageID;
		this.messageDescription = messageDescription;
		this.type = type;
	}
	
	

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return messageID;
	}

	@Override
	public void setMessageID(String msgID) {
		this.messageID = msgID;
		
	}

	@Override
	public String getMessageDescription() {
		// TODO Auto-generated method stub
		return messageDescription;
	}

	@Override
	public void setMessageDescription(String msgdes) {
		this.messageDescription = msgdes;
		
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
	public void setComplaintID(String compID) {
		this.compID = compID;
		
	}

	@Override
	public String getComplaintDescription() {
		// TODO Auto-generated method stub
		return compDescription;
	}

	@Override
	public void setComplaintDescription(String compdes) {
		this.compDescription = compdes;
		
	}


	@Override
	public String gettype() {
		//check weather it's a compliant or a feedback
		return type;
	}


	@Override
	public void setType(String type) {
		this.type = type;
		
	}

}
