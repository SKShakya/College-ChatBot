package oakbot.chat;

import java.time.LocalDateTime;

public class UserInfo {
	private final int userId, roomId;
	private final String username;
	private final String profilePicture;
	private final int reputation;
	private final boolean moderator, owner;
	private final LocalDateTime lastSeen, lastPost;

	private UserInfo(Builder builder) {
		userId = builder.userId;
		roomId = builder.roomId;
		username = builder.username;
		profilePicture = builder.profilePicture;
		reputation = builder.reputation;
		moderator = builder.moderator;
		owner = builder.owner;
		lastSeen = builder.lastSeen;
		lastPost = builder.lastPost;
	}


	public int getUserId() {
		return userId;
	}

	public int getRoomId() {
		return roomId;
	}

	
	public String getUsername() {
		return username;
	}


	public String getProfilePicture() {
		return profilePicture;
	}

	public int getReputation() {
		return reputation;
	}


	public boolean isModerator() {
		return moderator;
	}


	public boolean isOwner() {
		return owner;
	}

	c LocalDateTime getLastSeen() {
		return lastSeen;
	}

	public LocalDateTime getLastPost() {
		return lastPost;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", username=" + username + ", profilePicture=" + profilePicture + ", reputation=" + reputation + ", moderator=" + moderator + ", owner=" + owner + ", lastSeen=" + lastSeen + ", lastPost=" + lastPost + "]";
	}

	public static class Builder {
		private int userId, roomId;
		private String username;
		private String profilePicture;
		private int reputation;
		private boolean moderator, owner;
		private LocalDateTime lastSeen, lastPost;

		public Builder userId(int userId) {
			this.userId = userId;
			return this;
		}

		public Builder roomId(int roomId) {
			this.roomId = roomId;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder reputation(int reputation) {
			this.reputation = reputation;
			return this;
		}

		public Builder profilePicture(String profilePicture) {
			this.profilePicture = profilePicture;
			return this;
		}

		public Builder moderator(boolean moderator) {
			this.moderator = moderator;
			return this;
		}

		public Builder owner(boolean owner) {
			this.owner = owner;
			return this;
		}

		public Builder lastSeen(LocalDateTime lastSeen) {
			this.lastSeen = lastSeen;
			return this;
		}

		public Builder lastPost(LocalDateTime lastPost) {
			this.lastPost = lastPost;
			return this;
		}

		public UserInfo build() {
			return new UserInfo(this);
		}
	}
}
