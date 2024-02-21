
# PairBnb

This is a progressive web app (PWA) for place booking, designed to allow users to discover and book various destinations. The application provides a seamless experience with user-friendly interfaces and robust backend functionalities. It is built using Ionic 7 for the frontend, Spring Boot 3 with Spring Security for the backend, and PostgreSQL as the database. The application employs email and password-based authentication, generating JWT tokens for secure communication between the frontend and backend, while passwords are encrypted using BCrypt for enhanced security.


## Tech Stack

#### **Frontend**
**Ionic 7**: A powerful and popular cross-platform mobile application development framework based on Angular, enabling the creation of native-like user interfaces.
**Google Maps API**: Used for location services, allowing users to choose destinations interactively on the map.

#### **Backend**
**Spring Boot 3**: A versatile and lightweight Java-based framework that simplifies the development of robust, scalable, and secure web applications.
**Spring Security 6**: Ensures robust authentication and authorization mechanisms for the application's security requirements.

#### **Database**
**PostgreSQL**: A powerful open-source relational database management system, chosen for its reliability and performance.


## Features

#### 1. User Authentication and Registration
Users can sign up and log in using their email and password credentials.
JWT tokens are generated upon successful authentication, and users can securely access the application's features with their tokens.
#### 2. Auto Login and Auto Logout
The application implements automatic login using saved JWT tokens to provide a seamless user experience. Users are automatically logged out after their token expires, ensuring security and privacy.
#### 3. Place Creation and Management
Authenticated users can create places with various details, including names, image URLs, and locations chosen using Google Maps API.
Each place has a date range indicating its availability, enabling users to book the destination for a specific period.
#### 4. Place Booking
Users can make reservations for available places by selecting the desired destination, specifying the number of guests, and choosing booking dates.
Bookings are validated to prevent overlaps and conflicts, ensuring a smooth reservation process.
#### 5. Booking cancellation
Authenticated users can easily cancel their existing bookings.
#### 6. Place Discovery
Users can browse and explore all available places within the application, viewing essential details such as names, images, and locations.
#### 7. Bookable Places
The application also provides a list of bookable places, allowing users to identify destinations available for reservations.

## Native Functionalities
#### 1. Auto-Locate
User can use **auto-locate** functionality on both iOS and Android devices (also web browser if it is supported). Geolocation Capacitor plugin is used for implementation of this feature.
#### 2. Camera
While creating travel place users can open their **camera** (or pick photo from **file-manage**) to be displayed alongside with created destination.

## Domain

**UML Class Diagram**

![Screenshot_1](https://github.com/Djolee00/place-booker-MOBILE/assets/93478227/6b5bf9ea-7c41-4671-b785-b3ae44329b9d)

## Installation (using Docker)

1. Clone git repository

```
git clone https://github.com/Djolee00/place-booker-MOBILE.git
cd your-repository
```
2. Import *sql. script* in your Postgres DBMS
3. Populate empty fields in *application-example.properties* and rename it to *application.properties* (BE)
4. Populate empty fields in *environment.ts* files. You have to provied Google API key so you can you Google Map API (FE)
5. Populate fields in *docker-compose-example.yml* and rename it to *docker-compose.yml*
6. Run command (you need to install docker if not present on your machine)
```
docker compose up --build
```
7. Every other run you can use 
```
docker-compose up
```

    
## Screenshots

![Web_Photo_Editor](https://github.com/Djolee00/place-booker-MOBILE/assets/93478227/34d07273-4cdc-4a82-8dd6-b6903cf101ba)
