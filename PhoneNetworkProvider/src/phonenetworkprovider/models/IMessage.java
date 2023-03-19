package phonenetworkprovider.models;

public interface IMessage extends IModel {
	void setType(MessageType type); 
	
	MessageType getType();
	
	void setDescription(String description);
	
	String getDescription();
	
	void setUserId(String userId);
	
	String getUserId();
}
