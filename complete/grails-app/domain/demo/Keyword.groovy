package demo

import org.grails.datastore.mapping.core.connections.ConnectionSource

class Keyword {
    String name

    static mapping = {
        datasources(['movies', 'books']) // <1>
    }
}