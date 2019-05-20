package de.gmasil.thymeleaf.minifier;

import java.io.IOException;
import java.io.Writer;
import java.util.regex.Pattern;

import org.thymeleaf.engine.AbstractTemplateHandler;
import org.thymeleaf.model.IComment;
import org.thymeleaf.model.IModelVisitor;
import org.thymeleaf.model.IText;
import org.thymeleaf.util.StringUtils;

public class SimpleMinifierHandler extends AbstractTemplateHandler {
	private static final Pattern TAB_OR_NEW_LINE = Pattern.compile("[\\t\\n]+\\s");

	@Override
	public void handleComment(IComment comment) {
		// do not print comments
	}

	@Override
	public void handleText(IText text) {
		if (!ignorable(text)) {
			String trim = text.getText().trim();
			if (text.getText().length() != trim.length()) {
				super.handleText(new Text(text, trim));
			} else {
				super.handleText(text);
			}
		}
	}

	private boolean ignorable(IText text) {
		return StringUtils.isEmptyOrWhitespace(text.getText()) || TAB_OR_NEW_LINE.matcher(text.getText()).matches();
	}

	public static class Text implements IText {
		private IText itext;
		private String overwriteText;

		public Text(IText itext, String overwriteText) {
			this.itext = itext;
			this.overwriteText = overwriteText;
		}

		@Override
		public boolean hasLocation() {
			return itext.hasLocation();
		}

		@Override
		public String getTemplateName() {
			return itext.getTemplateName();
		}

		@Override
		public int getLine() {
			return itext.getLine();
		}

		@Override
		public int getCol() {
			return itext.getCol();
		}

		@Override
		public void accept(IModelVisitor visitor) {
			visitor.visit(this);
		}

		@Override
		public void write(Writer writer) throws IOException {
			writer.write(overwriteText);
		}

		@Override
		public int length() {
			return overwriteText.length();
		}

		@Override
		public char charAt(int index) {
			return overwriteText.charAt(index);
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return overwriteText.subSequence(start, end);
		}

		@Override
		public String getText() {
			return overwriteText;
		}
	}
}
