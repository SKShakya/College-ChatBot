package oakbot.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Defines how a chat message should be split up if it exceeds the max message
 * size.
 * @author Michael Angstadt
 */
public enum SplitStrategy {
	/**
	 * Split by word, appending an ellipsis onto the end of each part.
	 */
	WORD {
		@Override
		public List<String> _split(String message, int maxLength) {
			boolean markdownLocations[] = markdownLocations(message);

			String ellipsis = " ...";
			maxLength -= ellipsis.length();

			List<String> posts = new ArrayList<>();
			Iterator<String> it = new WordSplitter(message, maxLength, markdownLocations);
			while (it.hasNext()) {
				String post = it.next();
				posts.add(it.hasNext() ? post + ellipsis : post);
			}
			return posts;
		}

	
		class WordSplitter implements Iterator<String> {
			private final String message;
			private final int maxLength;
			private final boolean[] badSplitLocations;
			private int leftBound = 0;

	
			public WordSplitter(String message, int maxLength, boolean badSplitLocations[]) {
				this.message = message;
				this.maxLength = maxLength;
				this.badSplitLocations = badSplitLocations;
			}

			@Override
			public boolean hasNext() {
				return leftBound < message.length();
			}

			@Override
			public String next() {
				final String post;

				int charactersLeft = message.length() - leftBound;
				if (charactersLeft <= maxLength) {
					post = message.substring(leftBound);
					leftBound = message.length();
					return post;
				}

				int spacePos = message.lastIndexOf(' ', leftBound + maxLength);
				if (spacePos < leftBound) {
					spacePos = -1;
				}

				if (spacePos < 0) {
					post = message.substring(leftBound, leftBound + maxLength);
					leftBound += maxLength;
					return post;
				}

				//find a safe place to split the text so that text contained within markdown is not split up
				while (spacePos >= leftBound && badSplitLocations[spacePos]) {
					spacePos = message.lastIndexOf(' ', spacePos - 1);
				}

				if (spacePos < leftBound) {
					//the markdown section is too long and cannot be split, so just pretend that the markdown is not there
					spacePos = message.lastIndexOf(' ', leftBound + maxLength);
					if (spacePos < leftBound) {
						spacePos = -1;
					}

					if (spacePos < 0) {
						post = message.substring(leftBound, leftBound + maxLength);
						leftBound += maxLength;
						return post;
					}

					post = message.substring(leftBound, spacePos);
					leftBound = spacePos + 1;
					return post;
				}

				post = message.substring(leftBound, spacePos);
				leftBound = spacePos + 1;
				return post;
			}
		}

	
		private boolean[] markdownLocations(String message) {
			boolean inBold = false, inItalic = false, inCode = false, inTag = false, inLink = false;
			boolean[] inMarkdown = new boolean[message.length()];

			for (int i = 0; i < message.length(); i++) {
				char cur = message.charAt(i);
				char next = (i == message.length() - 1) ? 0 : message.charAt(i + 1);

				boolean skipAheadOne = false;
				switch (cur) {
				case '\\':
					skipAheadOne = (inCode && next == '`') || (!inCode && isSpecialChar(next));
					break;
				case '`':
					inCode = !inCode;
					break;
				case '*':
					if (!inCode) {
						if (next == '*') {
							inBold = !inBold;
							skipAheadOne = true;
						} else {
							inItalic = !inItalic;
						}
					}
					break;
				case '[':
					if (!inCode) {
						if (i < message.length() - 4 && message.substring(i + 1, i + 5).equals("tag:")) {
							inTag = true;
						}
						inLink = true;
					}
					break;
				case ']':
					if (inLink) {
						if (next != '(') {
							//it's not a link, just some brackets!
							inLink = false;
						}
					}
					if (inTag) {
						inTag = false;
					}
					break;
				case ')':
					//assumes there are no parens in the URL or title string
					inLink = false;
					break;
				}

				if (skipAheadOne) {
					inMarkdown[i] = inMarkdown[i + 1] = true;
					i++;
				} else {
					inMarkdown[i] = (inBold || inItalic || inCode || inLink || inTag);
				}
			}
			return inMarkdown;
		}

		//note: I don't escape () or _ in DescriptionNodeVisitor, so I'm not going to treat these characters as escapable
		private final String specialChars = "`*[]";

		private boolean isSpecialChar(char c) {
			return specialChars.indexOf(c) >= 0;
		}
	},

	/**
	 * Split by newline.
	 */
	NEWLINE {
		@Override
		public List<String> _split(String message, int maxLength) {
			List<String> posts = new ArrayList<>();
			Iterator<String> it = new NewlineSplitter(message, maxLength);
			while (it.hasNext()) {
				posts.add(it.next());
			}
			return posts;
		}

		class NewlineSplitter implements Iterator<String> {
			private final String message;
			private final int maxLength;
			private int leftBound = 0;

			public NewlineSplitter(String message, int maxLength) {
				this.message = message;
				this.maxLength = maxLength;
			}

			@Override
			public boolean hasNext() {
				return leftBound < message.length();
			}

			@Override
			public String next() {
				final String post;

				int charactersLeft = message.length() - leftBound;
				if (charactersLeft <= maxLength) {
					post = message.substring(leftBound);
					leftBound = message.length();
					return post;
				}

				int newlinePos = message.lastIndexOf('\n', leftBound + maxLength);
				if (newlinePos < leftBound) {
					newlinePos = -1;
				}

				if (newlinePos < 0) {
					post = message.substring(leftBound, leftBound + maxLength);
					leftBound += maxLength;
					return post;
				}

				post = message.substring(leftBound, newlinePos);
				leftBound = newlinePos + 1;
				return post;
			}
		}
	},

	/**
	 * Just truncate the message.
	 */
	NONE {
		@Override
		protected List<String> _split(String message, int maxLength) {
			return Arrays.asList(message.substring(0, maxLength));
		}
	};

	
	public List<String> split(String message, int maxLength) {
		if (maxLength < 1 || message.length() <= maxLength) {
			return Arrays.asList(message);
		}
		return _split(message, maxLength);
	}

	protected abstract List<String> _split(String message, int maxLength);
}
