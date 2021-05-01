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

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.IPostProcessorDialect;
import org.thymeleaf.engine.ITemplateHandler;
import org.thymeleaf.postprocessor.IPostProcessor;
import org.thymeleaf.templatemode.TemplateMode;

public class SimpleMinifierDialect implements IPostProcessorDialect {
    @Override
    public String getName() {
        return "thymeleaf-minifier";
    }

    @Override
    public int getDialectPostProcessorPrecedence() {
        return 1000;
    }

    @Override
    public Set<IPostProcessor> getPostProcessors() {
        Set<IPostProcessor> set = new HashSet<>(1);
        set.add(new IPostProcessor() {
            @Override
            public TemplateMode getTemplateMode() {
                return TemplateMode.HTML;
            }

            @Override
            public int getPrecedence() {
                return 1000;
            }

            @Override
            public Class<? extends ITemplateHandler> getHandlerClass() {
                return SimpleMinifierHandler.class;
            }
        });
        return set;
    }
}
