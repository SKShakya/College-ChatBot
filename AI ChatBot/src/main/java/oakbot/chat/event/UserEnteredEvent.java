package oakbot.chat.event;

/**
 * Represents an event that is triggered when a user enters a room.
 * @author Michael Angstadt
 */
public class UserEnteredEvent extends Event {
	private final int userId;
	private final String username;
	private final int roomId;
	private final String roomName;

	private UserEnteredEvent(Builder builder) {
		super(builder);
		userId = builder.userId;
		username = builder.username;
		roomId = builder.roomId;
		roomName = builder.roomName;
	}

	/**
	 * Gets the user ID of the user
	 * @return the user ID
	 */
	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	
	public int getRoomId() {
		return roomId;
	}

	
	public String getRoomName() {
		return roomName;
	}

	public static class Builder extends Event.Builder<UserEnteredEvent, Builder> {
		private int userId;
		private String username;
		private int roomId;
		private String roomName;

		public Builder() {
			super();
		}

		public Builder(UserEnteredEvent original) {
			super(original);
			userId = original.userId;
			username = original.username;
			roomId = original.roomId;
			roomName = original.roomName;
		}

		public Builder userId(int userId) {
			this.userId = userId;
			return this;
		}

	
		public Builder username(String username) {
			this.username = username;
			return this;
		}

	
		public Builder roomId(int roomId) {
			this.roomId = roomId;
			return this;
		}

		public Builder roomName(String roomName) {
			this.roomName = roomName;
			return this;
		}

		@Override
		public UserEnteredEvent build() {
			return new UserEnteredEvent(this);
		}
	}
}
