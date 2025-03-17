﻿# mini-moodle-site-spring
This is a **Mini Moodle**-style website that simulates a basic learning management system (LMS). It supports **Admins**, **Students**, and **Lecturers** with different levels of access and functionality.

<h1>Execution</h1>
<p>
The submission is an IntelliJ project that can be run directly from the IDE.
</p>
<h1>Assumptions</h1>
<p>
  The site uses bootstrap CDN therefore it assumes an internet connection is available.
  I am using Lombok and JPA Buddy plugins. make sure to enable annotation for Lombok.
</p>
<h1>Descriptions:</h1>
<p>

### 👩‍💼 Admin
- **Login**:
  - Use **Username:** `admin` and **Password:** `admin` to log in as an admin.
- Add **Students**:
  - Input **Student ID** (must be unique; **cannot be edited or deleted** once added).
  - Input **Student Name** (can be edited later).
- Add **Courses**:
  - Input **Course Name** (must be unique).
  - Input **Lecturer Name**.
  - Courses **cannot be edited**, but **can be deleted**.

### 👨‍🎓 Student
- **Login**:
  - Use **Student ID** as both **Username** and **Password** on the login page.
  - Can **change password** using the "Change Password" option.
- **Course Interaction**:
  - View and **enroll in available courses**.
  - **Remove enrolled courses**.
  - View course content and messages posted by lecturers.

### 👨‍🏫 Lecturer
- **Login**:
  - Use **Lecturer Name** as both **Username** and **Password** on the first login.
  - Can **change password** later.
- **Course Management**:
  - View all assigned courses.
  - **Edit course description**.
  - **Post messages** in the course forum.
  - **View enrolled students** for each course.

---

## 📌 Notes
- Once added, **Student IDs and Course Names are permanent and must be unique**.
- Password change functionality is available for both **Students** and **Lecturers**.
- Each user role (Admin, Student, Lecturer) has different access rights and UI options.


</p>
<h1>Credentials:</h1>
<p>
To log in as an Admin: the username and the password are "admin". 
</p>
<h1>video how it works:</h1>
<p>
  https://drive.google.com/file/d/1OSSfWJuZFB9oSJYVyD0rgi_e13Kj3LN2/view?usp=sharing
</p>

