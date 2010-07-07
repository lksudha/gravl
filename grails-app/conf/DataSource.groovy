dataSource {
    pooled = false
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='org.hibernate.cache.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:devDB"
            driverClassName = "org.hsqldb.jdbcDriver"
            // url = "jdbc:hsqldb:file:prodDb;shutdown=true"
            // driverClassName = "org.postgresql.Driver"
            // url = "jdbc:postgresql://localhost/gravl"
            // username = "glen"
            // password = "password"
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:hsqldb:mem:testDb"
            driverClassName = "org.hsqldb.jdbcDriver"
            //url = "jdbc:hsqldb:file:devDB"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            jndiName = "java:comp/env/jdbc/gravl"
            
        }
    }
}
