# Software Design Platform with Chatbot

A full-stack Spring Boot application for creating and managing software-design projects.

Users can manage projects, document use cases, create CRC cards, view diagrams, and interact with a context-aware AI assistant powered by the Groq API.

## Features

* User registration and login
* Project creation and management
* Use-case documentation
* CRC card management
* Diagram support
* AI chatbot integration
* MySQL data storage
* Secure access with Spring Security

## Technology Stack

* Java 17
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA
* Thymeleaf
* MySQL
* Groq API
* Maven

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
│   └── resources/
│       ├── templates/
│       └── application.properties
└── test/
```

## Requirements

* Java 17
* MySQL
* Groq API key

## Installation

Clone the repository:

```bash
git clone https://github.com/leuterisiosifidis/software-design-platform-with-chatbot.git
cd software-design-platform-with-chatbot
```

Set the required environment variables.
Run the application and open http://localhost:8081


## Screenshots

### Dashboard

![Dashboard](docs/screenshots/dashboard.png)

### Projects

![Projects](docs/screenshots/projects.png)

### Use Cases

![Use Cases](docs/screenshots/use-cases.png)

### CRC Cards

![CRC Cards](docs/screenshots/crc-cards.png)

## Academic Context

Developed for the **MYY803 Software Engineering** course at the **University of Ioannina**.

## License

Licensed under the [MIT License](LICENSE).
