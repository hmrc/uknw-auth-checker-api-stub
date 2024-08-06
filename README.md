
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

> `sbt it/test`


### All tests

This is a sbt command alias specific to this project. It will run a scala format
check, run a scala style check, run unit tests, run integration tests and produce a coverage report.
> `sbt runAllChecks`

> ### Pre-Commit

This is a sbt command alias specific to this project. It will run a scala format , run a scala fix,
run unit tests, run integration tests and produce a coverage report.
> `sbt runAllChecks`

### Format all

This is a sbt command alias specific to this project. It will run a scala format
check in the app, tests, and integration tests
> `sbt fmtAll`

### Fix all

This is a sbt command alias specific to this project. It will run the scala fix
linter/reformatter in the app, tests, and integration tests
> `sbt fixAll`

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

### Sample Requests and Responses
| Requests                                                                                                | Responses |
|---------------------------------------------------------------------------------------------------------| --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| {   "validityDate": "{{date}}",   "authType": "UKNW",   "eoris" : ["GB000000000200"] }                  | {   "processingDate": "{{dateTime}}",   "authType": "UKNW",   "results": [     {       "eori": "GB000000000200",       "valid": true,       "code": 0     }   ] }                                                                                                                                                                                                                                                                                                                                      |
| {   "validityDate": "{{date}}",   "authType": "UKNW",   "eoris" : ["GB000000000200","XI000000000200"] } | {   "processingDate": "{{dateTime}}",   "authType": "UKNW",   "results": [     {       "eori": "GB000000000200",       "valid": true,       "code": 0     },     {       "eori": "XI000000000200",       "valid": true,       "code": 0     }   ] }                                                                                                                                                                                                                                                    |

Any `validityDate` body parameter in requests should be replaced with the `{{date}}` token. It will be replaced on load with today's date formatted as `YYYY-MM-DD` (ISO_LOCAL_DATE)

Finally, go to `app/uk/gov/hmrc/uknwauthcheckerapistub/services/StubDataService.scala` and add a new `case`.