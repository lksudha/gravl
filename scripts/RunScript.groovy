/*
 * Copyright 2005-2010 the original author or authors.
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

import org.springframework.orm.hibernate3.SessionFactoryUtils
import org.springframework.orm.hibernate3.SessionHolder
import org.springframework.transaction.support.TransactionSynchronizationManager
 
includeTargets << grailsScript('_GrailsBootstrap')

/*
 * Adapted from http://naleid.com/blog/2010/12/03/grails-run-script-updated-for-grails-1-3-5/
 */

target(main: 'Execute the specified script(s) after starting up the application environment') {
    depends checkVersion, configureProxy, bootstrap, runScript
}

target(runScript: 'Main implementation that executes the specified script(s) after starting up the application environment') {

    if (!argsMap.params) {
        event('StatusError', ['ERROR: Required script name parameter is missing'])
        System.exit 1
    }

    configureHibernateSession()

    for (scriptFile in argsMap.params) {
        event('StatusUpdate', ["Running script $scriptFile ..."])
        executeScript scriptFile, classLoader
        event('StatusUpdate', ["Script $scriptFile complete!"])
    }
}

def configureHibernateSession() {
    // bind a Hibernate Session to avoid lazy initialization exceptions
    TransactionSynchronizationManager.bindResource(appCtx.sessionFactory,
        new SessionHolder(SessionFactoryUtils.getSession(appCtx.sessionFactory, true)))
}

def executeScript(scriptFile, classLoader) {
    File script = new File(scriptFile)
    if (!script.exists()) {
        event('StatusError', ["Designated script doesn't exist: $scriptFile"])
        return
    }

    def shell = new GroovyShell(classLoader, new Binding(ctx: appCtx, grailsApplication: grailsApp))
    shell.evaluate script.text
}

setDefaultTarget main

