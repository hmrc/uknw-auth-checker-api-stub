
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

## Stub records

New stub records may be added in [StubDataService](https://github.com/hmrc/uknw-auth-checker-api-stub/blob/main/app/uk/gov/hmrc/uknwauthcheckerapistub/services/StubDataService.scala).

Any `validityDate` body parameter in requests should be replaced with the `{{date}}` token. It will be replaced on load with today's date formatted as `YYYY-MM-DD` (ISO_LOCAL_DATE).

Any `processingDate` body parameter in responses should be replaced with the `{{dateTime}}` token. it will be replaced on load with today's date formatted as `yyyy-MM-dd'T'HH:mm:ss.SSS'Z'` (ISO 8601 UTC).

### Request and responses

<table>
<tr>
    <th>Name</th>
    <th>Description</th>
    <th>Request</th>
    <th>Response</th>
</tr>

<tr>
<td>req200_single</td>
<td>Valid request with 1 EORI matching <strong>^(GB&#124;XI)[0-9]{12}&#124;(GB&#124;XI)[0-9]{15}$</strong></td>
<td>
    <pre>
{
  "validityDate":"{{date}}",
  "authType":"UKNW",
  "eoris":[
    "GB000000000200"
  ]
}
    </pre>
</td>
<td>
    <pre>
{
   "processingDate":"{{dateTime}}",
   "authType":"UKNW",
   "results":[
      {
         "eori":"GB000000000200",
         "valid":true,
         "code":0
      }
   ]
}
    </pre>
</td>
</tr>

<tr>
<td>req200_multiple</td>
<td></td>
<td>
    <pre>
{
   "validityDate":"{{date}}",
   "authType":"UKNW",
   "eoris":[
      "GB000000000200",
      "XI000000000200"
   ]
}
    </pre>
</td>
<td>
    <pre>
{
   "processingDate":"{{dateTime}}",
   "authType":"UKNW",
   "results":[
      {
         "eori":"GB000000000200",
         "valid":true,
         "code":0
      },
      {
         "eori":"XI000000000200",
         "valid":true,
         "code":0
      }
   ]
}
    </pre>
    </td>
</tr>

<tr>
<td>req400_multipleEori</td>
<td></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris" : [
    "ABCD000000000200",
    "EFGH000000000200"
  ]
}
    </pre>
</td>
<td>
    <pre>
{
  "code": "BAD_REQUEST",
  "message": "Bad request",
  "errors": [
    {
      "code": "INVALID_FORMAT",
      "message": "ABCD000000000200 is not a supported EORI number",
      "path": "eoris"
    },
    {
      "code": "INVALID_FORMAT",
      "message": "EFGH000000000200 is not a supported EORI number",
      "path": "eoris"
    }
  ]
}
    </pre>
    </td>
</tr>

<tr>
<td>req400_noEoris</td>
<td></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris": []
}
    </pre>
</td>
<td>
    <pre>
{
  "code": "BAD_REQUEST",
  "message": "Bad request",
  "errors": [
    {
      "code": "INVALID_FORMAT",
      "message": "eoris field missing from JSON",
      "path": "eoris"
    }
  ]
}
    </pre>
    </td>
</tr>

<tr>
<td>req400_tooManyEoris</td>
<td></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris": [
    "GB837826880909874",
    "XI968840631629",
    "XI436105828614",
    "XI670738444417",
    "XI299090776708",
    ...
  ]
}
    </pre>
</td>
<td>
    <pre>
{
  "code": "BAD_REQUEST",
  "message": "Bad request",
  "errors": [
    {
      "code": "INVALID_FORMAT",
      "message": "The request payload must contain between 1 and 3000 EORI entries",
      "path": "eoris"
    }
  ]
}
    </pre>
    </td>
</tr>

<tr>
<td>req403_single</td>
<td></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris" : ["GB999999999403"]
}
    </pre>
</td>
<td>
    <pre>
{
  "code": "FORBIDDEN",
  "message": "You are not allowed to access this resource"
}
    </pre>
    </td>
</tr>

<tr>
<td>perfTest_1Eori</td>
<td>Valid request with 1 EORI matching <strong>^(GB&#124;XI)[0-9]{12}&#124;(GB&#124;XI)[0-9]{15}$</strong></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris" : ["GB000000000200"]
}
    </pre>
</td>
<td>
    <pre>
{
  "processingDate": "{{dateTime}}",
  "authType": "UKNW",
  "results": [
    {
      "eori": "GB000000000200",
      "valid": true,
      "code": 0
    }
  ]
}
    </pre>
    </td>
</tr>

<tr>
<td>perfTest_100Eori</td>
<td>Valid request with 100 EORI matching <strong>^(GB&#124;XI)[0-9]{12}&#124;(GB&#124;XI)[0-9]{15}$</strong></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris": [
    "GB473815539404392",
    "XI372463470482",
    "XI308718311576210",
    "GB264000739143105",
    "XI872151595641106",
    ...
  ]
}
    </pre>
</td>
<td>
    <pre>
{
  "processingDate": "{{dateTime}}",
  "authType": "UKNW",
  "results": [
    {
      "eori": "GB473815539404392",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI372463470482",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI308718311576210",
      "valid": true,
      "code": 0
    },
    {
      "eori": "GB264000739143105",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI872151595641106",
      "valid": true,
      "code": 0
    },
    ...
  ]
}
    </pre>
    </td>
</tr>

<tr>
<td>perfTest_500Eori</td>
<td></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris": [
    "GB067923337129",
    "XI041177131154142",
    "XI675926161105887",
    "GB889342696852",
    "XI555810835227",
    ...
  ]
}
    </pre>
</td>
<td>
    <pre>
{
  "processingDate": "{{dateTime}}",
  "authType": "UKNW",
  "results": [
    {
      "eori": "GB067923337129",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI041177131154142",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI675926161105887",
      "valid": true,
      "code": 0
    },
    {
      "eori": "GB889342696852",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI555810835227",
      "valid": true,
      "code": 0
    },
    ...
  ]
}
    </pre>
    </td>
</tr>

<tr>
<td>perfTest_1000Eori</td>
<td></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris": [
    "GB702601139907",
    "GB305827433009",
    "XI569274482382127",
    "GB823797040419",
    "XI777849853217",
    ...
  ]
}
    </pre>
</td>
<td>
    <pre>
{
  "processingDate": "{{dateTime}}",
  "authType": "UKNW",
  "results": [
    {
      "eori": "GB702601139907",
      "valid": true,
      "code": 0
    },
    {
      "eori": "GB305827433009",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI569274482382127",
      "valid": true,
      "code": 0
    },
    {
      "eori": "GB823797040419",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI777849853217",
      "valid": true,
      "code": 0
    },
    ...
  ]
}
    </pre>
    </td>
</tr>

<tr>
<td>perfTest_3000Eori</td>
<td></td>
<td>
    <pre>
{
  "validityDate": "{{date}}",
  "authType": "UKNW",
  "eoris": [
    "GB837826880909874",
    "XI968840631629",
    "XI436105828614",
    "XI670738444417",
    "XI299090776708",
    ...
  ]
}
    </pre>
</td>
<td>
    <pre>
{
  "processingDate": "{{dateTime}}",
  "authType": "UKNW",
  "results": [
    {
      "eori": "GB837826880909874",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI968840631629",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI436105828614",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI670738444417",
      "valid": true,
      "code": 0
    },
    {
      "eori": "XI299090776708",
      "valid": true,
      "code": 0
    },
    ...
  ]
}
    </pre>
    </td>
</tr>
</table>

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").