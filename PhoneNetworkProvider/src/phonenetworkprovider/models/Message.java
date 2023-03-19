package phonenetworkprovider.models;

import phonenetworkprovider.stores.Database;

public class Message implements IMessage{
	private String id;
	private String description;
	private String userId;
	private MessageType type;
	
	public Message(MessageType type, String userId, String description) {
		super();
		this.id = Database.getInstance().generateUniqueId();
		this.description = description;
		this.userId = userId;
		this.type = type;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String getUserId() {
		return userId;
	}
	
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public MessageType getType() {
		return type;
	}
	
	@Override
	public void setType(MessageType type) {
		this.type = type;
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	
}
