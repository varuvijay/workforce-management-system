# Smart Workforce Management System

A robust and scalable backend system for managing workforce, projects, and employee assignments, built with **Java 21** and **Spring Boot 3**.

## 🚀 Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.8
- **Database:** PostgreSQL
- **Security:** Spring Security & JWT (JSON Web Tokens)
- **Build Tool:** Maven
- **Architecture:** Modular Monolith (Layered Architecture)

## 🌟 Key Features

- **Authentication & Authorization**: Secure login with JWT, Role-Based Access Control (RBAC) (Admin, Manager, Employee, HR).
- **Employee Management**: CRUD operations for employees, profile management.
- **Project Management**:
  - Full Lifecycle: Create, Update, Delete.
  - Granular Access: Managers control projects; Employees view assigned work.
  - Team Management: Assign/Remove employees dynamically.
- **Transactional Integrity**: Ensures data consistency across complex operations.

## 🛠️ Setup & Installation

### Prerequisites

- Java 21 SDK installed.
- Maven installed.
- PostgreSQL database running.

### Configuration

The application uses **Environment Variables** for secrets.
Create a `.env` file in the root directory (or set system env vars) with the following:

```properties
SERVER_PORT=8080

# Database
DB_URL=jdbc:postgresql://localhost:5432/workforce_db
DB_DRIVER=org.postgresql.Driver
DB_USERNAME=your_username
DB_PASSWORD=your_password

# Security
JWT_SECRET=your_super_secret_key_at_least_32_chars_long
JWT_EXPIRATION=900000
# (900000 ms = 15 minutes)
```

### Running the Application

```bash
mvn spring-boot:run
```

The server will start at `http://localhost:8080`.

## 🔌 API Endpoints

### Projects

| Method | Endpoint                       | Description                           | Access        |
| ------ | ------------------------------ | ------------------------------------- | ------------- |
| POST   | `/api/v1/projects/`            | Create a new project                  | Manager       |
| GET    | `/api/v1/projects/{id}`        | Get project details                   | Authenticated |
| GET    | `/api/v1/projects/`            | Get all projects (RBAC filtered)      | Authenticated |
| PUT    | `/api/v1/projects/{id}`        | Update project details                | Manager       |
| DELETE | `/api/v1/projects/{id}`        | Delete a project                      | Manager       |
| POST   | `/api/v1/projects/{id}/assign` | Assign employee (param: `employeeId`) | Manager       |
| POST   | `/api/v1/projects/{id}/remove` | Remove employee (param: `employeeId`) | Manager       |

## 🤝 Contribution

1.  Create a feature branch: `git checkout -b feature/amazing-feature`
2.  Commit your changes: `git commit -m 'feat: add amazing feature'`
3.  Push to the branch: `git push origin feature/amazing-feature`
4.  Open a Pull Request.
