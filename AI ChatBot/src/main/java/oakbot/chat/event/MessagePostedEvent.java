package oakbot.chat.event;

import oakbot.chat.ChatMessage;

/**
 * Represents an event that is triggered when a new message is posted.
 * @author Michael Angstadt
 */
public class MessagePostedEvent extends Event {
	private final ChatMessage message;

	private MessagePostedEvent(Builder builder) {
		super(builder);
		message = builder.message;
	}

	public ChatMessage getMessage() {
		return message;
	}

	public static class Builder extends Event.Builder<MessagePostedEvent, Builder> {
		private ChatMessage message;

		/**
		 * Creates an empty builder.
		 */
		public Builder() {
			super();
		}

		
		public Builder(MessagePostedEvent original) {
			super(original);
			message = original.message;
		}

	
		public Builder message(ChatMessage message) {
			this.message = message;
			this.timestamp = message.getTimestamp();
			return this;
		}

		@Override
		public MessagePostedEvent build() {
			return new MessagePostedEvent(this);
		}
	}
}
