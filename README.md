# Quiz Microservices Application

A microservices-based quiz application built with Spring Boot, Spring Cloud, and Docker.

## Architecture

This application consists of 4 microservices:

- **Service Registry (Eureka Server)** - Port 8761: Service discovery server
- **API Gateway** - Port 8765: Single entry point for all client requests
- **Question Service** - Port 8081: Manages quiz questions
- **Quiz Service** - Port 8090: Manages quizzes and quiz sessions

## Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Cloud (Eureka, Gateway)
- PostgreSQL 13
- Docker & Docker Compose
- Maven

## Prerequisites

- Docker and Docker Compose installed
- Java 21 (for local development)
- Maven 3.9+ (for local development)

## Quick Start

### Using Docker Compose (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/yourusername/your-repo-name.git
cd your-repo-name
```

2. Start all services:
```bash
docker-compose up --build
```

3. Wait for all services to start (approximately 1-2 minutes)

4. Access the services:
   - Eureka Dashboard: http://localhost:8761
   - API Gateway: http://localhost:8765
   - Question Service: http://localhost:8765/question-service
   - Quiz Service: http://localhost:8765/quiz-service

### Local Development

1. Start PostgreSQL:
```bash
docker run -d \
  --name postgres \
  -e POSTGRES_DB=quizquestiondb \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin123 \
  -p 5432:5432 \
  postgres:13
```

2. Update application.properties to use localhost instead of postgres hostname

3. Start services in order:
```bash
# Terminal 1 - Eureka Server
cd service-registry
mvn spring-boot:run

# Terminal 2 - API Gateway
cd api-gateway
mvn spring-boot:run

# Terminal 3 - Question Service
cd question-service
mvn spring-boot:run

# Terminal 4 - Quiz Service
cd quiz-service
mvn spring-boot:run
```

## API Endpoints

### Question Service
- `GET question/allQuestions` - Get all questions
- `GET question/category/{cat}` - Get all questions in a given Category
- `POST question/add` - Create a new question
- `GET /question/generate` - Generates a quiz for quiz-service
- `POST /question/getQuestions` - Get all questions based on questionIDs
- `POST /question/score` - Calculate score based on right answers for quiz-service

### Quiz Service
- `GET /quiz/create` - Create a new quiz based on Category, number of Questions with a given title. 
- `POST /quiz/get/{id}` - Get the questions of a quiz by ID
- `POST /quiz/quizresult/{id}` - Submit quiz answers and get final scores.

## Accessing via API Gateway

All services can be accessed through the API Gateway:

```bash
# Question Service
curl http://localhost:8765/question-service/question

# Quiz Service
curl http://localhost:8765/quiz-service/quiz
```

## Stopping the Application

```bash
docker-compose down

# To remove volumes as well
docker-compose down -v
```

## CI/CD

This project uses GitHub Actions for CI/CD:

- Builds and tests all services on push/PR
- Builds Docker images and pushes to Docker Hub (main branch only)
- Runs integration tests

## Environment Variables

Key environment variables (set in docker-compose.yml):

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/quizquestiondb
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=admin123
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-service:8761/eureka/
```

## Troubleshooting

### Services not registering with Eureka
- Wait 30-60 seconds for initial registration
- Check Eureka dashboard at http://localhost:8761

### Database connection errors
- Ensure PostgreSQL container is healthy: `docker-compose ps`
- Check logs: `docker-compose logs postgres`

### Port conflicts
- Ensure ports 8761, 8765, 8081, 8090, 5432 are not in use
- Use `docker-compose down` to stop existing containers

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.