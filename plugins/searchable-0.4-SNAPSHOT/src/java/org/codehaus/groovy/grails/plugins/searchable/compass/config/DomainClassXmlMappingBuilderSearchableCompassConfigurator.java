/*
* Copyright 2007 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.codehaus.groovy.grails.plugins.searchable.compass.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.codehaus.groovy.grails.commons.GrailsClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClass;
import org.codehaus.groovy.grails.plugins.searchable.compass.converter.DefaultCompassConverterLookupHelper;
import org.codehaus.groovy.grails.plugins.searchable.compass.converter.StringMapConverter;
import org.codehaus.groovy.grails.plugins.searchable.compass.mapping.*;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassConfigurationFactory;
import org.compass.core.impl.DefaultCompass;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Configures Compass with mappings for Grails domain class by generating in-memory XML, currently the only way
 *
 * Note: a Compass will provide a runtime Mapping API in the next milestone, yay!
 *
 * @author Maurice Nicholson
 */
// TODO delete this obsolete class
public class DomainClassXmlMappingBuilderSearchableCompassConfigurator implements SearchableCompassConfigurator {
    private static final Log LOG = LogFactory.getLog(DomainClassXmlMappingBuilderSearchableCompassConfigurator.class);

    private GrailsApplication grailsApplication;
    private SearchableCompassClassMappingXmlBuilder compassClassMappingXmlBuilder;
    private List defaultExcludedProperties;
    private Map defaultFormats;

    /**
     * Configure Grails domain class mappings
     *
     * @param compassConfiguration the runtime config instance
     * @param configurationContext a context allowing flexible parameter passing
     */
    public void configure(CompassConfiguration compassConfiguration, Map configurationContext) {
        Assert.notNull(grailsApplication, "grailsApplication cannot be null");
        Assert.notNull(compassClassMappingXmlBuilder, "compassClassMappingXmlBuilder cannot be null");

        // TODO find a better place for this
        // register custom converters
        compassConfiguration.registerConverter(StringMapConverter.CONVERTER_NAME, new StringMapConverter());

        DefaultCompassConverterLookupHelper converterLookupHelper = new DefaultCompassConverterLookupHelper();
        converterLookupHelper.setConverterLookup(((DefaultCompass) CompassConfigurationFactory.newConfiguration().setConnection("ram://dummy").buildCompass()).getConverterLookup());

        SearchableGrailsDomainClassPropertyMappingFactory domainClassPropertyMappingFactory = new SearchableGrailsDomainClassPropertyMappingFactory();
        domainClassPropertyMappingFactory.setDefaultFormats(defaultFormats);
        domainClassPropertyMappingFactory.setConverterLookupHelper(converterLookupHelper);

        CompositeSearchableGrailsDomainClassCompassClassMapper classMapper = new CompositeSearchableGrailsDomainClassCompassClassMapper();
        classMapper.setDefaultExcludedProperties(defaultExcludedProperties);
        SearchableGrailsDomainClassCompassClassMapper[] classMappers;
        try {
            SimpleSearchableGrailsDomainClassCompassClassMapper simpleClassMapper = new SimpleSearchableGrailsDomainClassCompassClassMapper();
            simpleClassMapper.setDomainClassPropertyMappingStrategyFactory(domainClassPropertyMappingFactory);
            AbstractSearchableGrailsDomainClassCompassClassMapper closureClassMapper = (AbstractSearchableGrailsDomainClassCompassClassMapper) ClassUtils.forName("org.codehaus.groovy.grails.plugins.searchable.compass.mapping.ClosureSearchableGrailsDomainClassCompassClassMapper").newInstance();
            closureClassMapper.setDomainClassPropertyMappingStrategyFactory(domainClassPropertyMappingFactory);
            classMappers = new SearchableGrailsDomainClassCompassClassMapper[] {
                simpleClassMapper, closureClassMapper
            };
        } catch (Exception ex) {
            // Log and throw runtime exception
            LOG.error("Failed to find or create closure mapping provider class instance", ex);
            throw new IllegalStateException("Failed to find or create closure mapping provider class instance: " + ex);
        }
        classMapper.setSearchableGrailsDomainClassCompassMappingDescriptionProviders(classMappers);

        List classMappings = new ArrayList();
        List grailsDomainClasses = Arrays.asList(grailsApplication.getArtefacts(DomainClassArtefactHandler.TYPE));
        for (int i = 0; i < grailsDomainClasses.size(); i++) {
            GrailsDomainClass grailsDomainClass = (GrailsDomainClass) grailsDomainClasses.get(i);
            classMappings.add(classMapper.getCompassClassMapping(grailsDomainClass, grailsDomainClasses));
        }
        for (int i = 0, max = classMappings.size(); i < max; i++) {
            CompassClassMapping classMapping = (CompassClassMapping) classMappings.get(i);
            InputStream inputStream = compassClassMappingXmlBuilder.buildClassMappingXml(classMapping);
            LOG.debug("Adding [" + classMapping.getMappedClass().getName() + "] mapping to CompassConfiguration");
            compassConfiguration.addInputStream(inputStream, classMapping.getMappedClass().getName() + ".cpm.xml");
        }
    }

    public GrailsApplication getGrailsApplication() {
        return grailsApplication;
    }

    public void setGrailsApplication(GrailsApplication grailsApplication) {
        this.grailsApplication = grailsApplication;
    }

    public SearchableCompassClassMappingXmlBuilder getCompassClassMappingXmlBuilder() {
        return compassClassMappingXmlBuilder;
    }

    public void setCompassClassMappingXmlBuilder(SearchableCompassClassMappingXmlBuilder compassClassMappingXmlBuilder) {
        this.compassClassMappingXmlBuilder = compassClassMappingXmlBuilder;
    }

    public void setDefaultExcludedProperties(List defaultExcludedProperties) {
        this.defaultExcludedProperties = defaultExcludedProperties;
    }

    public Map getDefaultFormats() {
        return defaultFormats;
    }

    public void setDefaultFormats(Map defaultFormats) {
        this.defaultFormats = defaultFormats;
    }
}
