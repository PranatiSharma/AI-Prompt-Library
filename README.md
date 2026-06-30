# AI-Prompt-Library
AI Prompt Library is a secure Spring Boot web application that enables users to create, organize, search, and manage AI prompts efficiently. It features user authentication, dashboard analytics, favorites, and PDF/Excel export using Spring Security, PostgreSQL, JPA, and Thymeleaf.

---

## About the Project

AI Prompt Library is designed to simplify prompt management for AI users by providing a centralized platform to store, organize, and access prompts. Each user has a secure account where prompts are managed independently, ensuring data privacy and personalized organization.

The application follows a layered MVC architecture and demonstrates the implementation of authentication, database integration, and modern Java web development practices.

---

## Key Features

- Secure User Registration and Authentication
- Personal Prompt Library for Every User
- Create, View, Update and Delete AI Prompts
- Search Prompts by Title or Content
- Organize Prompts by Category
- Mark Frequently Used Prompts as Favorites
- Dashboard with Category-wise Statistics
- Export Prompt Library to PDF
- Export Prompt Library to Excel
- Responsive User Interface

---

## Technologies Used

**Backend**
- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate

**Frontend**
- Thymeleaf
- Bootstrap 5
- HTML
- CSS

**Database**
- PostgreSQL

**Build Tool**
- Maven

**Libraries**
- Apache POI (Excel Export)
- OpenPDF (PDF Export)

---

## Architecture

```
Browser
      │
      ▼
Spring MVC Controller
      │
      ▼
Business Service
      │
      ▼
Spring Data JPA Repository
      │
      ▼
Hibernate
      │
      ▼
PostgreSQL
```

---

## Security

The application uses Spring Security to provide:

- Secure Login & Logout
- Password Encryption using BCrypt
- Session Management
- User-specific Data Access

---

## Application Modules

- Authentication Module
- Dashboard Module
- Prompt Management Module
- Search & Filter Module
- Favorites Module
- Export Module

---

## Future Improvements

- AI-powered prompt recommendations
- Prompt tagging
- Prompt version history
- Prompt sharing
- REST API integration
- Cloud deployment

---

## Author

**Pranati Sharma**
