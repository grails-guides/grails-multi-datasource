# grails-multi-datasource

Sample app for the apache/grails-static-website guide [Grails Multi-datasource](https://grails.apache.org/guides/grails-multi-datasource/4/guide/index.html).

This branch is the Grails 8 companion for **multiple data sources and transactions**: a REST API with a default H2 datasource for movies, a `books` datasource for books, a shared `Keyword` domain mapped to both, GORM Data Services with `@Transactional` / `@ReadOnly` / `withConnection`, JSON views, and a Spock functional test.

## Layout

| Directory | What it is |
|---|---|
| [`initial/`](initial/) | Grails 8 REST API starter (`rest-api` profile, Hibernate, H2, JSON views). Start here and follow the guide. |
| [`complete/`](complete/) | The finished sample with multi-datasource config, `Movie` / `Book` / `Keyword` domains, services, controllers, JSON views, and the functional test. |

## Running

Requires JDK 21+.

```bash
cd complete
./gradlew test integrationTest
./gradlew bootRun
```

Example endpoints: `POST /book`, `GET /book`, `GET /book/keywords`, `POST /movie`, `GET /movie`, `GET /movie/keywords`.

## Branches

| Branch | Grails version |
|---|---|
| `grails8` | Apache Grails 8.0.0-M5 |
| `grails5` | Apache Grails 5 (published guide baseline) |

## Guide prose

Published narrative lives on [grails.apache.org/guides](https://grails.apache.org/guides/) in [apache/grails-static-website](https://github.com/apache/grails-static-website) under `guides/grails-multi-datasource/`.

## License

Apache License 2.0. See [LICENSE](LICENSE).
