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
package org.codehaus.groovy.grails.plugins.searchable

import org.codehaus.groovy.grails.commons.*
import org.codehaus.groovy.grails.plugins.searchable.test.domain.blog.*
import org.codehaus.groovy.grails.plugins.searchable.test.domain.nosearchableproperty.NoSearchableProperty
import org.codehaus.groovy.grails.plugins.searchable.test.domain.annotated.AnnotatedSearchable
import org.codehaus.groovy.grails.plugins.searchable.test.domain.annotated.Other
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader

/**
* @author Maurice Nicholson
*/
class SearchableUtilsTests extends GroovyTestCase {
//    def currentSearchableValues
    def postDc
    def commentDc
    def userDc

    void setUp() {
        Post.searchable = true
        assert Post.searchable == true
        for (c in [Post, Comment, User]) {
            println "before ${c.getName()}#searchable = ${c.searchable}"
            c.searchable = true
            println "after ${c.getName()}#searchable = ${c.searchable}"
            assert c.searchable == true
        }
//        currentSearchableValues = [(Post): Post.searchable, (Comment): Comment.searchable, (User): User.searchable]
//        assert [currentSearchableValues.values()].unique() == [true]
        postDc = new DefaultGrailsDomainClass(Post)
        commentDc = new DefaultGrailsDomainClass(Comment)
        userDc = new DefaultGrailsDomainClass(User)
    }

    void tearDown() {
//        currentSearchableValues.each { c, v -> c.searchable = v }
//        currentSearchableValues = null
        postDc = null
        commentDc = null
        userDc = null
    }

    void testIsSearchableForSearchableProperty() {
        // when true
        assert SearchableUtils.isSearchable(postDc, null)
        // when false
        Post.searchable = false
        assert SearchableUtils.isSearchable(postDc, null) == false
        // when closure (any closure)
        Post.searchable = { -> }
        assert SearchableUtils.isSearchable(postDc, null)

        assert SearchableUtils.isSearchable(new DefaultGrailsDomainClass(NoSearchableProperty), null) == false
    }

    void testIsSearchableForCompassXml() {
        def resourceLoader = [
            getResource: { name->
                assert name == "classpath:/" + NoSearchableProperty.name.replaceAll(/\./, "/") + ".cpm.xml"
                [exists: { true }] as Resource
            }] as ResourceLoader
        assert SearchableUtils.isSearchable(new DefaultGrailsDomainClass(NoSearchableProperty), resourceLoader)

        resourceLoader = [
            getResource: { name->
                assert name == "classpath:/" + NoSearchableProperty.name.replaceAll(/\./, "/") + ".cpm.xml"
                [exists: { false }] as Resource
            }] as ResourceLoader
        assert !SearchableUtils.isSearchable(new DefaultGrailsDomainClass(NoSearchableProperty), resourceLoader)
    }

    void testIsSearchableForCompassAnnotated() {
        assert SearchableUtils.isSearchable([getPropertyValue: {name -> null}, getClazz: {AnnotatedSearchable}] as GrailsDomainClass, null)
        assert !SearchableUtils.isSearchable([getPropertyValue: {name -> null}, getClazz: {Other}] as GrailsDomainClass, null)
    }

    void testGetSearchablePropertyAssociatedClassPropertyArg() {
        // when other side is searchable
        // one
        assert SearchableUtils.getSearchablePropertyAssociatedClass(postDc.getPropertyByName("author"), [userDc, postDc]) == User
        // many
        assert SearchableUtils.getSearchablePropertyAssociatedClass(userDc.getPropertyByName("posts"), [userDc, postDc]) == Post

        // when other side NOT searchable
        // one
        assert SearchableUtils.getSearchablePropertyAssociatedClass(postDc.getPropertyByName("author"), [postDc]) == null
        // many
        assert SearchableUtils.getSearchablePropertyAssociatedClass(userDc.getPropertyByName("posts"), [userDc]) == null
    }

    void testGetSearchablePropertyAssociatedClassPropertyNameArg() {
        // when other side is searchable
        // one
        assert SearchableUtils.getSearchablePropertyAssociatedClass(postDc, "author", [userDc, postDc]) == User
        // many
        assert SearchableUtils.getSearchablePropertyAssociatedClass(userDc, "posts", [userDc, postDc]) == Post

        // when other side NOT searchable
        // one
        assert SearchableUtils.getSearchablePropertyAssociatedClass(postDc, "author", [postDc]) == null
        // many
        assert SearchableUtils.getSearchablePropertyAssociatedClass(userDc, "posts", [userDc]) == null
    }

    void testIsIncludedProperty() {
        assert SearchableUtils.isIncludedProperty("firstname", true)
        assert SearchableUtils.isIncludedProperty("password", true)
        assert !SearchableUtils.isIncludedProperty("password", [except: "password"])
        assert !SearchableUtils.isIncludedProperty("password", [except: ["password", "securityNumber"]])
        assert SearchableUtils.isIncludedProperty("firstname", [except: "password"])
        assert SearchableUtils.isIncludedProperty("firstname", [except: ["password", "securityNumber"]])
        assert !SearchableUtils.isIncludedProperty("firstname", [only: "surname"])
        assert !SearchableUtils.isIncludedProperty("firstname", [only: ["surname", "idHash"]])
        assert SearchableUtils.isIncludedProperty("firstname", [only: "firstname"])
        assert SearchableUtils.isIncludedProperty("firstname", [only: ["firstname", "surname"]])

        // Simple pattern matching support
        assert SearchableUtils.isIncludedProperty("addressLine1", [only: 'address*'])
        assert SearchableUtils.isIncludedProperty("addressLine1", [only: ['name', 'address*', 'building']])
        assert SearchableUtils.isIncludedProperty("addressLine1", [only: 'addressLine?'])
        assert SearchableUtils.isIncludedProperty("addressLine1", [only: ['building*', 'addressLine?']])
        assert !SearchableUtils.isIncludedProperty("addressLine1", [only: 'house*'])
        assert !SearchableUtils.isIncludedProperty("addressLine1", [except: ['address*', 'name']])
        assert !SearchableUtils.isIncludedProperty("addressLine1", [except: ['name', 'addressLine?']])
        assert SearchableUtils.isIncludedProperty("addressLine1", [except: 'house*'])
    }
}