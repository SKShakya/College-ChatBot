package oakbot.chat;

import java.time.LocalDateTime;


public class PingableUser {
	private final int roomId;
	private final long userId;
	private final String username;
	private final LocalDateTime lastPost;

	public PingableUser(int roomId, long userId, String username, LocalDateTime lastPost) {
		this.roomId = roomId;
		this.userId = userId;
		this.username = username;
		this.lastPost = lastPost;
	}

	/**
	 * Gets the room that the user is pingable from.
	 * @return the room ID
	 */
	public int getRoomId() {
		return roomId;
	}

	/**
	 * Gets the user's ID.
	 * @return the user's ID
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Gets the username.
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the time that they last posted a message.
	 * @return the time of their last post
	 */
	public LocalDateTime getLastPost() {
		return lastPost;
	}

	@Override
	public String toString() {
		return "PingableUser [roomId=" + roomId + ", userId=" + userId + ", username=" + username + ", lastPost=" + lastPost + "]";
	}
}
