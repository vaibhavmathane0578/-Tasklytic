# Tasklytic (status: Inprogress)

**Tasklytic** is a real-time collaboration platform designed to streamline team workflows. With a robust microservices architecture, Tasklytic ensures efficient communication, task management, and seamless integration of collaborative features.

---

## **Features**

- **User Management**
  - JWT-based authentication.
  - Role-based access control.
  - Secure user registration, login, and profile management.

- **Task Management**
  - Create, update, and assign tasks.
  - Track progress with detailed statuses.
  - Filter and prioritize tasks based on roles and deadlines.

- **Real-Time Collaboration**
  - Chat functionality for team communication.
  - Collaboration sessions for simultaneous task handling.

- **Notification Service**
  - Stay updated with real-time notifications.
  - Configurable alerts for task updates and messages.

- **File Management**
  - Secure upload and download of files.
  - Support for multiple file formats.

- **AI Integration**
  - Personalized recommendations for task management.
  - Insights to optimize workflows.

---

## **Microservices Architecture**

Tasklytic leverages a microservices architecture for scalability and flexibility. The services include:

1. **User Service**
   - Handles authentication, authorization, and user management.
   - Features OTP-based email verification for secure updates.

2. **Task Service**
   - Manages tasks with detailed attributes and role-specific functionalities.

3. **Collaboration Service**
   - Includes entities like `ChatMessageEntity` and `CollaborationSessionEntity`.

4. **Notification Service**
   - Ensures timely notifications for critical updates.

5. **File Service**
   - Provides secure file storage and access.

6. **AuthToken Service**
   - Manages token validation and security.

---

## **Technology Stack**

- **Backend**: Spring Boot with Java.
- **Frontend**: (yet to planned for development).
- **Database**: Relational and non-relational databases.
- **Authentication**: JWT with secure role-based access.
- **Build Tool**: Maven.
- **AI Features**: AI-driven recommendations.

---

## **Setup Instructions**

1. Clone the repository:
   ```bash
   git clone https://github.com/vaibhavmathane0578/tasklytic.git
   ```

2. Navigate to the project directory:
   ```bash
   cd tasklytic
   ```

3. Set up the services:
   - Use **Spring Initializr** to bootstrap services.
   - Configure database connections in `application.yml`.

4. Build and run the services:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Access the application at `http://localhost:8080`.

---

## **Workflow**

1. Users authenticate using the **User Service**.
2. Tasks are created and managed via the **Task Service**.
3. Real-time communication happens through the **Collaboration Service**.
4. Notifications are dispatched by the **Notification Service**.
5. Files are securely managed with the **File Service**.

---

## **Roadmap**

- [x] Develop microservices architecture.
- [ ] Implement frontend .
- [ ] Add AI-powered analytics and recommendations.
- [ ] Deploy using Docker and Kubernetes.
- [ ] Introduce API Gateway for unified service access.

---

## **Contributing**

We welcome contributions to Tasklytic! To contribute:

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your feature description"
   ```
4. Push the changes:
   ```bash
   git push origin feature/your-feature-name
   ```
5. Submit a pull request.

---

## **License**

Tasklytic is licensed under the **MIT License**. See `LICENSE` for details.

---

## **Contact**

For any queries or suggestions, feel free to reach out:

- **Author**: Vaibhav Sanjay Mathane
- **Email**: vaibhav.s.mathane@gmail.com
