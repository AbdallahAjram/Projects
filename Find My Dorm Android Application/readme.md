# Find My Dorm - Android Application 🏠📱

A mobile application designed for university students to easily find dorms for rent.  
Built with Android Studio (Java), MySQL, PHP, and Volley, it provides a complete platform for browsing available dorms, posting dorm listings, and contacting owners.

---

## Table of Contents
- [About the Project](#about-the-project)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Future Improvements](#future-improvements)
- [Contact](#contact)

---

## About the Project

**Find My Dorm** aims to solve the problem of scattered, outdated dorm rental information for students.  
The app provides a centralized, easy-to-use platform where:
- Students can browse available dorms based on location, price, and facilities.
- Dorm owners can list their properties with images and detailed descriptions.
- Users can manage their personal profiles and listings.

The application uses a MySQL database and a PHP-based backend for storing and retrieving data through REST APIs.

---

## Features

- **User Authentication**  
  - Sign up, log in, and session management using `SharedPreferences`.

- **Browse Dorm Listings**  
  - View dorms with images, descriptions, pricing, and location information.

- **Dynamic Filtering**  
  - Filter dorms based on location for easier searching.

- **Add New Listings**  
  - Dorm owners can upload dorm details along with images directly from the app.

- **Profile Management**  
  - View, edit, and delete user profiles and personal listings.

- **Contact Owners**  
  - Call dorm owners directly from within the app to inquire about listings.

- **Smooth UX**  
  - Fast image loading with `Glide` and efficient API communication with `Volley`.

---

## Technologies Used

- **Frontend (Mobile App)**
  - Android Studio
  - Java
  - Volley (API communication)
  - Glide (image loading)
  - SharedPreferences (session management)

- **Backend**
  - PHP (REST APIs)
  - MySQL (Database)

- **Hosting & Development Tools**
  - XAMPP (for local testing)
  - FileZilla (optional for server upload)

---

\
---

## Setup and Installation

### Prerequisites
- Android Studio installed
- XAMPP or any MySQL + PHP server setup
- Basic knowledge of PHP and MySQL databases

### Installation Steps

1. **Clone or Download the Repository**
   ```bash
   git clone https://github.com/abdallahajram/find-my-dorm.git
