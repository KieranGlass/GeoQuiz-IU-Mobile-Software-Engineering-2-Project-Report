# GeoQuiz
### Android Geography & General Knowledge Quiz App

GeoQuiz is an Android quiz application developed in **Android Studio** using **Java**. The application is designed for offline use, providing a fast and responsive quiz experience without requiring an internet connection.

The app tests users' knowledge across a variety of geography and general knowledge topics while tracking progress and high scores.


# Features

The application currently includes quizzes covering:

- Countries
- National Flags
- Capitals
- National Cuisine
- Famous Landmarks
- Global Brands
- Sports Teams

Each quiz presents an image or clue and provides multiple-choice answers for the user to select from.


# Difficulty System

Each quiz offers multiple difficulty levels.

As the difficulty increases:

- More answer choices are presented.
- Incorrect answers become more challenging.
- Distractor answers are selected intelligently based on geographical proximity.

For example, if the correct answer is **Burkina Faso**, an easier quiz may include countries from around the world, while a harder quiz will include neighbouring or nearby African countries that are much more difficult to distinguish.

This creates a progressively more realistic and challenging learning experience.


# Progress Tracking

The application stores user progress locally using SQLite.

Features include:

- Previous quiz scores
- Best percentage score for each quiz
- Automatic updating of personal bests when a higher score is achieved
- Overall Completion percentage
- 

# Technology

The project is built using:

- **Java**
- **Android Studio**
- **SQLite**

The application is designed to work entirely offline, making it lightweight, responsive, and usable without an internet connection.


# Image Management

To reduce the overall application size, quiz assets are currently stored as large sprite sheets.

Instead of storing hundreds of individual image files, the application crops the required section of a high-resolution composite image whenever a question is generated.

This approach reduces the number of assets included in the project while maintaining image quality.


# Future Development

Several improvements are planned for future versions of GeoQuiz.

## Cloud-Based Assets

Replace locally stored image sprite sheets with an online API or cloud storage solution to:

- Reduce application size
- Improve loading efficiency
- Simplify maintenance
- Allow new quiz content to be added without requiring app updates


## 👤 User Accounts

Introduce optional user accounts to enable:

- Cloud-based progress synchronisation
- Cross-device play
- Global leaderboards
- Achievement tracking
