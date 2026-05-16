# campus-event-final_review_complect

# Campus Event Management System

## 📌 Project Overview

The Campus Event Management System is a full-stack web application developed to simplify the process of managing college events, student registrations, event participation, and administration activities.

This project allows students to register, log in securely using Email OTP verification, view available events, join events, and manage their profiles. Administrators can create, update, delete, and monitor events through an admin dashboard.

The application is built using Java Spring Boot for backend development, MySQL for database management, and HTML/CSS/JavaScript with Thymeleaf for frontend rendering.

---

# 🚀 Features

## 👨‍🎓 Student Module

* Student Registration
* Student Login
* Email OTP Verification
* View Available Events
* Join Events
* Upload Profile Image
* View Joined Events
* Update Profile Details
* Event Payment Status Tracking

---

## 👨‍💼 Admin Module

* Admin Login
* Create New Events
* Update Existing Events
* Delete Events
* View All Registered Students
* Manage Event Participation
* Monitor Event Registrations
* Dashboard Management

---

## 🔒 Security Features

* OTP-Based Email Verification
* Password Authentication
* Session Management
* Form Validation

---

# 🛠️ Technologies Used

## Backend

* Java 21
* Spring Boot 3.2.4
* Spring MVC
* Spring Data JPA
* Hibernate
* Maven

---

## Frontend

* HTML5
* CSS3
* JavaScript
* Bootstrap
* Thymeleaf

---

## Database

* MySQL

---

## Tools & IDE

* Eclipse IDE
* MySQL Workbench
* Git
* GitHub
* Postman

---

# 📂 Project Structure

```text
campus-event-management/
│
├── src/main/java/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   └── CampusEventApplication.java
│
├── src/main/resources/
│   ├── templates/
│   ├── static/
│   └── application.properties
│
├── uploads/
├── pom.xml
└── README.md
```

---

# ⚙️ Installation & Setup

## Step 1: Clone Repository

```bash
git clone https://github.com/your-username/campus-event-management.git
```

---

## Step 2: Open Project

Open the project using:

* Eclipse IDE
* IntelliJ IDEA
* VS Code

---

## Step 3: Configure MySQL Database

Create a database in MySQL:

```sql
CREATE DATABASE event_db123;
```

---

## Step 4: Configure application.properties

```properties
spring.application.name=campus-event-management
server.port=9081

spring.datasource.url=jdbc:mysql://localhost:3306/event_db123?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=100MB
spring.servlet.multipart.max-request-size=100MB

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_GMAIL@gmail.com
spring.mail.password=YOUR_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## Step 5: Run Project

Run the following main file:

```text
CampusEventApplication.java
```

Or using Maven:

```bash
mvn spring-boot:run
```

---

# 🌐 Access Application

Open browser:

```text
http://localhost:9081
```

---

# 📧 Gmail OTP Setup

This project uses Gmail SMTP for OTP verification.

## Steps:

1. Enable 2-Step Verification in Gmail
2. Generate App Password
3. Use App Password in `application.properties`

Example:

```properties
spring.mail.username=example@gmail.com
spring.mail.password=abcd efgh ijkl mnop
```

---

# 🗄️ Database Tables

The application automatically creates tables using Hibernate.

Main Tables:

* admin_table
* student_table
* event_table
* event_join_table

---

# 🔄 Application Workflow

## Student Workflow

1. Student Registration
2. OTP Verification
3. Login Authentication
4. View Events
5. Join Events
6. View Participation Status

---

## Admin Workflow

1. Admin Login
2. Create Events
3. Edit/Delete Events
4. View Students
5. Manage Registrations

---

# 📸 Screenshots

You can add screenshots here:

* Home Page
* Login Page
* Registration Page
* Admin Dashboard
* Event List
* Student Dashboard

Example:

```md
![Home Page](screenshots/home.png)
```

---

# 🔥 Key Functionalities

## Email OTP Verification

* OTP generation
* Email sending using SMTP
* Secure student verification

---

## File Upload System

* Student profile image upload
* Multipart file handling
* Upload directory management

---

## Event Management

* Add event details
* Event date and time management
* Event participation tracking

---

# 📈 Future Enhancements

* Payment Gateway Integration
* QR Code Entry System
* AI-based Event Recommendation
* Mobile Application
* Real-time Notifications
* Attendance Tracking System
* Cloud Deployment

---

# ☁️ Deployment

The project can be deployed using:

* Railway
* Render
* AWS
* Google Cloud
* Azure
* Docker

---

# 🧪 Testing

Tools used for testing:

* Postman
* Browser Testing
* MySQL Validation

---

# 🐞 Common Issues & Fixes

## Email Not Sending

Cause:

* Invalid App Password
* SMTP blocked by network

Fix:

* Use Gmail App Password
* Try Mobile Hotspot
* Enable SMTP access

---

## Database Connection Failed

Check:

* MySQL service running
* Correct username/password
* Correct database name

---

# 📚 Learning Outcomes

Through this project, the following concepts were learned:

* Full Stack Web Development
* Spring Boot Framework
* REST APIs
* MVC Architecture
* Database Connectivity
* Authentication System
* Email Integration
* File Upload Handling
* GitHub Version Control

---

# 👨‍💻 Author

## Jinkala Vishnu Vardhan

* Computer Science Engineering Student
* Java Full Stack Developer
* Interested in AI & Machine Learning Projects

---

# 🔗 GitHub Repository

Add your repository link here:

```text
https://github.com/your-username/campus-event-management
```

---

# 📄 License

This project is developed for educational and learning purposes.

---

# ⭐ Support

If you like this project:

* Give a Star ⭐ on GitHub
* Fork the Repository
* Share with Others

---

# 🙏 Acknowledgements

Special thanks to:

* Spring Boot Documentation
* MySQL Documentation
* Open Source Community
* College Faculty & Mentors
