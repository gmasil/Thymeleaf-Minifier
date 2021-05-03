/**
 * Thymeleaf Minifier
 * Copyright © 2021 gmasil.de
 *
 * This file is part of Thymeleaf Minifier.
 *
 * Thymeleaf Minifier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Thymeleaf Minifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thymeleaf Minifier.  If not, see <https://www.gnu.org/licenses/>.
 */
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
            super.handleText(minifyText(text));
        }
    }

    private IText minifyText(IText iText) {
        String[] lines = iText.getText().replace("\r", "").split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line.trim());
        }
        return new Text(iText, sb.toString());
    }

    private boolean ignorable(IText text) {
        return StringUtils.isEmptyOrWhitespace(text.getText()) || TAB_OR_NEW_LINE.matcher(text.getText()).matches();
    }

    public static class Text implements IText {
        private IText iText;
        private String overwriteText;

        public Text(IText itext, String overwriteText) {
            this.iText = itext;
            this.overwriteText = overwriteText;
        }

        @Override
        public boolean hasLocation() {
            return iText.hasLocation();
        }

        @Override
        public String getTemplateName() {
            return iText.getTemplateName();
        }

        @Override
        public int getLine() {
            return iText.getLine();
        }

        @Override
        public int getCol() {
            return iText.getCol();
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
