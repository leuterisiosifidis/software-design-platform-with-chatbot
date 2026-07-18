# Software Design Platform with Chatbot

A Spring Boot web application for creating and managing software-design projects.

Users can manage projects, document use cases, create CRC cards, view diagrams, and interact with a context-aware AI assistant powered by the Groq API.

## Features

- User registration, login, and profile management
- Software-project creation and management
- Use-case documentation
- CRC card management
- Project diagrams
- Context-aware AI assistant
- MySQL data persistence
- Secure access with Spring Security

## Technology Stack

| Area | Technologies |
|---|---|
| Backend | Java 17, Spring Boot, Spring MVC |
| Security | Spring Security |
| Database | MySQL, Spring Data JPA, Hibernate |
| Frontend | Thymeleaf, HTML |
| AI | Groq API |
| Build Tool | Maven |

## Architecture

The application follows a layered architecture:

```text
Browser
   ↓
Spring Security
   ↓
Controllers
   ↓
Services
   ↓
Repositories
   ↓
MySQL
```

The chatbot communicates with the Groq API through a dedicated service.

## Project Structure

```text
src/
├── main/
│   ├── java/com/myy803/project/
│   │   ├── config/
│   │   ├── controllers/
│   │   ├── domain/
│   │   ├── repositories/
│   │   └── services/
│   │
│   └── resources/
│       ├── templates/
│       └── application.properties
│
└── test/
```

## Requirements

- Java 17
- MySQL
- Groq API key

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/leuterisiosifidis/software-design-platform-with-chatbot.git
cd software-design-platform-with-chatbot
```

### 2. Configure the environment variables

#### Windows PowerShell

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
$env:GROQ_API_KEY="your_groq_api_key"
```

#### Linux or macOS

```bash
export DB_USERNAME="root"
export DB_PASSWORD="your_mysql_password"
export GROQ_API_KEY="your_groq_api_key"
```

### 3. Run the application

#### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

#### Linux or macOS

```bash
./mvnw spring-boot:run
```

Open the application at:

```text
http://localhost:8081
```

## Screenshots

### Dashboard

<p align="center">
  <img src="docs/screenshots/dashboard.png" alt="Application dashboard" width="850">
</p>

<p align="center">
  Main dashboard for viewing and managing software-design projects.
</p>

### Project Management and Use Cases

<table>
  <tr>
    <td align="center" width="50%">
      <strong>Projects</strong>
    </td>
    <td align="center" width="50%">
      <strong>Use Cases</strong>
    </td>
  </tr>
  <tr>
    <td>
      <img src="docs/screenshots/projects.png" alt="Project management page" width="100%">
    </td>
    <td>
      <img src="docs/screenshots/use-cases.png" alt="Use-case management page" width="100%">
    </td>
  </tr>
  <tr>
    <td align="center">
      Create, edit, and organize software projects.
    </td>
    <td align="center">
      Document actors, requirements, and system flows.
    </td>
  </tr>
</table>

### CRC Cards

<p align="center">
  <img src="docs/screenshots/crc-cards.png" alt="CRC card management page" width="850">
</p>

<p align="center">
  Create and manage classes, responsibilities, and collaborators.
</p>

### Authentication

<table>
  <tr>
    <td align="center" width="50%">
      <strong>Login</strong>
    </td>
    <td align="center" width="50%">
      <strong>Registration</strong>
    </td>
  </tr>
  <tr>
    <td>
      <img src="docs/screenshots/login.png" alt="Login page" width="100%">
    </td>
    <td>
      <img src="docs/screenshots/register.png" alt="Registration page" width="100%">
    </td>
  </tr>
</table>

## Academic Context

Developed for the **MYY803 Software Engineering** course at the **University of Ioannina**.

The project demonstrates:

- Layered application architecture
- MVC web development
- Authentication and authorization
- Relational data persistence
- Software-design documentation
- External AI service integration

## License

This project is licensed under the [MIT License](LICENSE).
