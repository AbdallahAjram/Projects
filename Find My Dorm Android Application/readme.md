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


## Setup and Installation

### Prerequisites
- Android Studio installed
- XAMPP or any MySQL + PHP server setup
- Basic knowledge of PHP and MySQL databases

### Installation Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/find-my-dorm.git
   ```

   *(Or if you already have the local project files, you can skip cloning.)*

2. **Setup Backend**
   - Place the backend folder (e.g., `loginsignupgraded`) into your `htdocs` directory inside XAMPP.
   - Import the provided `.sql` database file into your MySQL server (e.g., using phpMyAdmin).

3. **Configure API URLs**
   - In your Android Studio project, update the API endpoint URLs in your code to match your local server IP or domain (depending on your hosting setup).

4. **Run the App**
   - Open the project in Android Studio.
   - Connect a real Android device or use an emulator.
   - Build and run the app!

---

## Future Improvements

- Add Google Maps integration to display dorm locations visually.
- Implement a messaging system for chat between users and owners.
- Add in-app notifications for new dorm listings.
- Enhance security with password encryption and validation.

---

## Contact

If you have any questions, suggestions, or feedback, feel free to reach out:

- **Email**: ajrama_04@outlook.com
- **GitHub**: https://github.com/yourusername
