package com.cdfive.springboot.common.predicate;

import org.springframework.boot.logging.LoggerConfiguration;

import java.util.function.Predicate;

/**
 * Arthas
 * ognl -c xxx '#predicate=new com.cdfive.springboot.common.predicate.LoggerConfigurationPackageNamePredicate(),@com.cdfive.springboot.util.SpringContextUtil@getBean(@org.springframework.boot.logging.LoggingSystem@class).getLoggerConfigurations().stream().filter(#predicate).collect(@java.util.stream.Collectors@toList())'
 *
 * @author cdfive
 */
public class LoggerConfigurationPackageNamePredicate implements Predicate<LoggerConfiguration> {

    @Override
    public boolean test(LoggerConfiguration loggerConfiguration) {
        return loggerConfiguration.getName().startsWith("com.cdfive");
    }
}
