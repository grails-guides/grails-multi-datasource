package demo

class Movie {
    String title
    static hasMany = [keywords: Keyword]

    static mapping = {
        datasource 'movies' // <1>
    }
}