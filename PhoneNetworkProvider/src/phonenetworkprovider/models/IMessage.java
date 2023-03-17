package phonenetworkprovider.models;

public interface IMessage extends IModel {
	
	IServiceProvider getProvider();
	
	void setProvider(String providerId);
	
	String gettype();
	
	void setType(String type);
	
	void setMessageID(String msgID);
	
	String getMessageDescription();
	
	void setMessageDescription(String msgdes);
	
	void setComplaintID(String compID);
	
	String getComplaintDescription();
	
	void setComplaintDescription(String msgdes);
	
	

}
