# 🛠️ Service Marketplace

A Spring Boot-based backend for a **Service 
Marketplace** platform where users can 
register as service **providers** or 
**customers**, create and manage 
bookings, authentication, and notifications.

## 🚀 Features

- 🔐 User Registration & Login with JWT Authentication
- 👤 Role-based access: Customer & Provider
- 📆 Booking system with creation, cancellation, and status updates
- 📩 Notification system for booking updates
- 🧾 Service listing and search
- 📧 Email notifications 
- 🛡️ Secure password hashing with BCrypt
- 📊 Admin capabilities 

---

## 📦 Tech Stack

- **Backend:** Spring Boot 3, Spring Security, JPA, Hibernate
- **Database:** MySQL 
- **Authentication:** JWT (JSON Web Tokens)
- **Build Tool:** Maven
- **Other:** Swagger (for API docs)





---

## 🔑 Authentication Flow

1. Users register with an email and password.
2. Upon login, JWT is generated.
3. All secured endpoints require the JWT in the `Authorization` header.

---

## 📄 API Endpoints (Example)

- `POST /api/auth/register` – Register a new user
- `POST /api/auth/login` – Login and receive JWT
- `GET /api/services` – List available services
- `POST /api/requests` – Create a new service request
- `PUT /api/requests/{id}/cancel` – Cancel a booking

[✅ Full API Docs via Swagger: `/swagger-ui/index.html`]

---

## 🧪 Running Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/blazemhan/service-marketplace.git


Author
---
- Samuel Ademola
- Email: ademolasamuel44@gmail.com