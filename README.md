
# uknw-auth-checker-api-stub

This is the stub microservice that is a substitute for the EIS service.

## Pre-requisites

### Services

Start `UKNW_AUTH_CHECKER_API` services as follows:

```bash
sm2 --start NOTIFICATION_OF_PRESENTATION_ALL
```

## Running the service

> `sbt run`

The service runs on port `9071` by default.

## Running tests

### Unit tests

> `sbt test`

### Integration tests

> `sbt it/test`

## Custom commands

### All tests

This is a sbt command alias specific to this project. It will run a scala format
check, run a scala style check, run unit tests, run integration tests and produce a coverage report.

> `sbt runAllChecks`

### Pre-Commit

This is a sbt command alias specific to this project. It will run a scala format , run a scala fix,
run unit tests, run integration tests and produce a coverage report.

> `sbt preCommit`

### Format all

This is a sbt command alias specific to this project. It will run a scala format
check in the app, tests, and integration tests

> `sbt fmtAll`

### Fix all

This is a sbt command alias specific to this project. It will run the scala fix
linter/reformatter in the app, tests, and integration tests

> `sbt fixAll`

## Request and responses

Any `validityDate` body parameter in requests should be replaced with the `{{date}}` token. It will be replaced on load with today's date formatted as `YYYY-MM-DD` (ISO_LOCAL_DATE).

Any `processingDate` body parameter in responses should be replaced with the `{{dateTime}}` token. it will be replaced on load with today's date formatted as `yyyy-MM-dd'T'HH:mm:ss.SSS'Z'` (ISO 8601 UTC).

Also remember to check `app/uk/gov/hmrc/uknwauthcheckerapistub/utils/Constants.scala` for the list of valid Eoris, any Eori that is not on that list will return `valid: false` and `code: 1`, while the listed Eoris will return `valid: true` and `code: 0` 

Any duplicate eori numbers in the body of the request will be flattened and the response will only contain one instance of the eori.
### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").