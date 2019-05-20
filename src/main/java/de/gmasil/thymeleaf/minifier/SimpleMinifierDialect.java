package de.gmasil.thymeleaf.minifier;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IPostProcessorDialect;
import org.thymeleaf.engine.ITemplateHandler;
import org.thymeleaf.postprocessor.IPostProcessor;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
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