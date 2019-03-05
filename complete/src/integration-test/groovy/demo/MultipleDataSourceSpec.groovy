package demo


import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import groovy.json.JsonSlurper
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import spock.lang.Shared
import spock.lang.Specification

@Integration
class MultipleDataSourceSpec extends Specification {

    @Shared
    HttpClient client

    @Shared
    JsonSlurper jsonSlurper = new JsonSlurper()

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        this.client  = HttpClient.create(baseUrl.toURL())
    }

    private HttpResponse<Map> saveResource(String resource, String itemTitle, List<String> itemKeywords) {
        HttpRequest request = HttpRequest.POST("/$resource", [title: itemTitle, keywords: itemKeywords])
        client.toBlocking().exchange(request, Map)
    }

    private HttpResponse<Map> deleteResource(String resource, String itemTitle) {
        HttpRequest request = HttpRequest.DELETE("/$resource", [title: itemTitle])
        client.toBlocking().exchange(request, Map)
    }

    private HttpResponse<String> fetchResource(String resource) {
        HttpRequest request = HttpRequest.GET("/$resource")
        client.toBlocking().exchange(request, String)
    }

    private HttpResponse<Map> resourceKeywords(String resource) {
        HttpRequest request = HttpRequest.GET("/$resource/keywords")
        client.toBlocking().exchange(request, Map)
    }

    def "Test Multi-Datasource support saving and retrieving books and movies"() {
        when:
        HttpResponse<Map> resp = saveResource('book', 'Change Agent', ['dna', 'sci-fi'])

        then:
        resp.status == HttpStatus.CREATED

        when:
        resp = saveResource('book', 'Influx', ['sci-fi'])

        then:
        resp.status == HttpStatus.CREATED

        when:
        resp = saveResource('book', 'Kill Decision', ['drone', 'sci-fi'])

        then:
        resp.status == HttpStatus.CREATED

        when:
        resp = saveResource('book', 'Freedom (TM)', ['sci-fi'])

        then:
        resp.status == HttpStatus.CREATED

        when:
        resp = saveResource('book', 'Daemon', ['sci-fi'])

        then:
        resp.status == HttpStatus.CREATED

        when:
        resp = saveResource('movie', 'Pirates of Silicon Valley', ['apple', 'microsoft', 'technology'])

        then:
        resp.status == HttpStatus.CREATED

        when:
        resp = saveResource('movie', 'Inception', ['sci-fi'])

        then:
        resp.status == HttpStatus.CREATED

        when:
        resp = fetchResource('book')

        then:
        resp.status == HttpStatus.OK
        List<Map> booksMap = jsonSlurper.parseText(resp.body())
        booksMap.collect { it.title }.sort() == ['Change Agent', 'Daemon', 'Freedom (TM)', 'Influx', 'Kill Decision']

        when:
        resp = fetchResource('movie')

        then:
        resp.status == HttpStatus.OK
        List<Map> moviesMap = jsonSlurper.parseText(resp.body())
        moviesMap.collect { it.title }.sort() == ['Inception', 'Pirates of Silicon Valley']

        when:
        resp = resourceKeywords('book')

        then:
        resp.status == HttpStatus.OK
        resp.body().keywords == ['dna', 'drone', 'sci-fi']


        when:
        resp = resourceKeywords('movie')

        then:
        resp.status == HttpStatus.OK
        resp.body().keywords == ['apple', 'microsoft', 'sci-fi', 'technology']

        when:
        resp = deleteResource('book', 'Change Agent')

        then:
        resp.status == HttpStatus.NO_CONTENT

        when:
        resp = deleteResource('book', 'Influx')

        then:
        resp.status == HttpStatus.NO_CONTENT

        when:
        resp = deleteResource('book', 'Kill Decision')

        then:
        resp.status == HttpStatus.NO_CONTENT

        when:
        resp = deleteResource('book', 'Freedom (TM)')

        then:
        resp.status == HttpStatus.NO_CONTENT

        when:
        resp = deleteResource('book', 'Daemon')

        then:
        resp.status == HttpStatus.NO_CONTENT

        when:
        resp = deleteResource('movie', 'Pirates of Silicon Valley')

        then:
        resp.status == HttpStatus.NO_CONTENT

        when:
        resp = deleteResource('movie', 'Inception')

        then:
        resp.status == HttpStatus.NO_CONTENT
    }
}
