package oakbot.chat.event;

import oakbot.chat.ChatMessage;

/**
 * Represents an event that is triggered when a message is edited.
 * @author Michael Angstadt
 */
public class MessageEditedEvent extends Event {
	private final ChatMessage message;

	private MessageEditedEvent(Builder builder) {
		super(builder);
		message = builder.message;
	}

	public ChatMessage getMessage() {
		return message;
	}

	public static class Builder extends Event.Builder<MessageEditedEvent, Builder> {
		private ChatMessage message;

	
		public Builder() {
			super();
		}

	
		public Builder(MessageEditedEvent original) {
			super(original);
			message = original.message;
		}

		public Builder message(ChatMessage message) {
			this.message = message;
			this.timestamp = message.getTimestamp();
			return this;
		}

		@Override
		public MessageEditedEvent build() {
			return new MessageEditedEvent(this);
		}
	}
}
