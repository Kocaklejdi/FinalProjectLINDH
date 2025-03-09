# FinalProjectLINDH

Job Portal Backend
A REST API for a job portal built with Spring Boot, JPA, and MySQL. Supports admins, employers, and job seekers with role-based access.
Features

User Management: Registration, authentication with JWT
Admin: Manage users
Employer: Post jobs, review applications, update statuses, add reviews
Job Seeker: Browse jobs, apply, upload resumes, track applications
Core Features: Pagination, filtering, role-based security

Tech Stack

Spring Boot 3.x
MySQL 8.0
Spring Data JPA
Spring Security with JWT
Maven

API Endpoints
Auth

POST /api/v1/auth/register - Register user
POST /api/v1/auth/login - Get JWT token

Admin

GET /api/v1/user/all - Get all users
GET /api/v1/admin/all-users/{role}/{page} - Filter users by role
DELETE /api/v1/admin/delete/{userId} - Delete user

Employer

POST /api/v1/employer/new - Post job
GET /api/v1/employer/jobs - Get employer's jobs
POST /api/v1/employer/jobs/{employerId}/{page} - Filter jobs
GET /api/v1/employer/applicantsToJob/{jobId} - View applications
PUT /api/v1/employer/update/{jobId}/{applicationId} - Update status
POST /api/v1/employer/jobs/review/{jobId} - Add review
GET /api/v1/employer/{userId}/download-resume - Download resume

Job Seeker

GET /api/v1/user/jobs/{page} - View jobs
POST /api/v1/user/{userId}/upload-resume - Upload resume
GET /api/v1/user/appliedJobs/{page} - View applications

Public

GET /api/v1/public/reviews/{page} - Get reviews

Setup

Clone repository
git clone https://github.com/Kocaklejdi/FinalProjectLINDH.git

Configure MySQL in application.properties:
Copyspring.datasource.url=jdbc:mysql://localhost:3306/job_portal
spring.datasource.username=root
spring.datasource.password=your_password

Run: mvn clean install

Run: mvn spring-boot:run

Access API at: http://localhost:8080

Testing
Import the included Postman collection to test all endpoints:

Register a user to get credentials
Login to get JWT token
Use token in Authorization header for subsequent requests

Database Entities

User: Role-based (ADMIN, EMPLOYER, JOB_SEEKER)
Job Listing: Title, description, location, salary range
Job Application: Status (PENDING, ACCEPTED, REJECTED)
Review: Ratings and comments for jobs
