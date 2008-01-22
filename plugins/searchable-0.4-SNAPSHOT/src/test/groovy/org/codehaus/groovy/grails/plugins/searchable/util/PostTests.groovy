/**
 *
 *
 * @author Maurice Nicholson
 */
package org.codehaus.groovy.grails.plugins.searchable.util

import org.codehaus.groovy.grails.plugins.searchable.test.domain.blog.Post

class PostTests extends GroovyTestCase {

    void testIt() {
        println "Post.searchable == ${Post.searchable}"
        assert Post.searchable == true
    }
}
