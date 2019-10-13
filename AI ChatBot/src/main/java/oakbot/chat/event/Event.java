package oakbot.chat.event;

import java.time.LocalDateTime;

/**
 * Represents a chat room event.
 * @author Michael Angstadt
 */
public abstract class Event {
	protected final long eventId;
	protected final LocalDateTime timestamp;

	protected Event(Builder<?, ?> builder) {
		timestamp = builder.timestamp;
		eventId = builder.eventId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public long getEventId() {
		return eventId;
	}

	public static abstract class Builder<T extends Event, U extends Builder<?, ?>> {
		protected LocalDateTime timestamp;
		protected long eventId;

		@SuppressWarnings("unchecked")
		private final U this_ = (U) this;

		protected Builder() {
			//empty
		}

		protected Builder(Event original) {
			timestamp = original.timestamp;
			eventId = original.eventId;
		}

		
		public U timestamp(LocalDateTime timestamp) {
			this.timestamp = timestamp;
			return this_;
		}

	
		public U eventId(long eventId) {
			this.eventId = eventId;
			return this_;
		}

		public abstract T build();
	}
}
