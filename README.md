
# uknw-auth-checker-api-stub

This is the stub microservice that as stand in for EIS service

## Running the service

> `sbt run`

The service runs on port `9071` by default.

## Running dependencies

Using [service manager](https://github.com/hmrc/service-manager)
with the service manager profile `UKNW_AUTH_CHECKER_API_STUB` will start
the UKNW auth checker stub.

> `sm --start UKNW_AUTH_CHECKER_API_STUB`

## Running tests

### Unit tests

> `sbt test`

### Integration tests

> `sbt it:test`


### All tests

This is a sbt command alias specific to this project. It will run a scala format
check, run a scala style check, run unit tests, run integration tests and produce a coverage report.
> `sbt runAllChecks`

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").