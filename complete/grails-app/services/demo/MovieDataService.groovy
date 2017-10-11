package demo

import grails.gorm.services.Join
import grails.gorm.services.Service
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
@Service(Movie)
interface MovieDataService {

    @Transactional('movies')
    void deleteByTitle(String title)

    @ReadOnly('movies')
    @Join('keywords') // <1>
    List<Movie> findAll()
}