# Hotel Availability Search

A Spring Boot application for searching hotel availability, storing searches, and keeping track of repeated searches.

The application exposes a REST API, uses Kafka to handle search events, and Oracle to store the data.

## Requirements

You'll need:

- Docker
- Docker Compose

## Getting started

Clone the repository and run:

```bash
git clone https://github.com/MirkoGabriel/mindata-hotel-search.git
docker compose up --build
```

Once everything is up and running, you can access:

- App: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health check: http://localhost:8080/actuator/health

## API

### POST `/search`

Creates a new hotel search and sends it to Kafka.

Example request:

```json
{
  "hotelId": "1234aBc",
  "checkIn": "29/12/2023",
  "checkOut": "31/12/2023",
  "ages": [30, 29, 1, 3]
}
```

Response:

```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000"
}
```

The request has a few basic validations:

- All fields are required.
- Dates must use the `dd/MM/yyyy` format.
- `checkIn` must be before `checkOut`.
- Ages cannot be lower than 0.

### GET `/count?searchId={searchId}`

Returns the search and the number of times the exact same search was made.

Example response:

```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000",
  "search": {
    "hotelId": "1234aBc",
    "checkIn": "29/12/2023",
    "checkOut": "31/12/2023",
    "ages": [30, 29, 1, 3]
  },
  "count": 2
}
```

### A couple of things to keep in mind

- Every `POST /search` generates a new `searchId`, even when the search is identical to a previous one.
- The order of the ages matters when checking if two searches are the same.

## Architecture

The project follows a hexagonal architecture, keeping the business logic separated from the infrastructure and external services.

```text
com.mindata.hotelsearch
├── domain
├── application
│   ├── port.in
│   ├── port.out
│   └── service
└── infrastructure
    ├── adapter.in.web
    ├── adapter.out.kafka
    ├── adapter.out.persistence
    └── config
```

The main flow is:

1. The controller receives and validates the request.
2. The application generates a unique `searchId`.
3. The search is published to Kafka.
4. A Kafka consumer receives the event and stores it in Oracle.
5. `/count` reads the stored data and counts matching searches.

## Tech stack

- Java 21
- Spring Boot 3.4
- Spring Kafka
- Spring Data JPA
- Oracle XE
- Apache Kafka
- OpenAPI / Swagger
- JUnit 5
- Mockito
- Testcontainers
- Awaitility
- JaCoCo

## Running the tests

For running Gradle locally, use Java 21 or Java 17.

For example:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
./gradlew test jacocoTestReport
```

If you're using IntelliJ, you can check the Gradle JVM here:

**Settings → Build Tools → Gradle → Gradle JVM**

and select Java 21.

The JaCoCo report can be found at:

```text
build/reports/jacoco/test/html/index.html
```

The project requires at least 80% coverage for lines, branches, and methods.

Integration tests use Testcontainers. If Docker isn't available, those tests are skipped automatically.

## Docker services

| Service | Purpose | Port |
|---|---|---|
| app | Spring Boot application | 8080 |
| kafka | Message broker | 9092 |
| oracle | Database | 1521 |

## Some design decisions

- Immutable `records` are used across the domain and API layers.
- Lists are copied defensively to avoid unexpected modifications.
- Database queries use parameters to prevent SQL injection.
- Kafka producers and consumers are separated by responsibility.
- Virtual threads are used for asynchronous persistence from the Kafka consumer.
- `LocalDate` is used for dates instead of `java.util.Date`.

## Stopping the project

To stop the containers:

```bash
docker compose down
```

If you also want to remove the Oracle data:

```bash
docker compose down -v
```