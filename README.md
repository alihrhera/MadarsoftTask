
# MadarSoft Task - User Management App

An Android application built in **Kotlin** using **Jetpack Compose**, following **Clean Architecture** and **MVVM** principles. The app allows users to be added, stored locally using Room, and displayed in a list. Input validation, modern UI, and a layered architecture ensure scalability and testability.

---

## 🕒 Estimated Time to Complete: 8-9 hours

## 🚀 Features

- **Add User Screen**
  - Fields: Name, Email, Gender
  - Validations for required fields and email format
  - Gender dropdown menu
  - Error messages with real-time feedback

- **User List Screen**
  - Displays all saved users using `LazyColumn` (Jetpack Compose)
  - Each item shows name, email, gender, and initials avatar

- **Offline Storage**
  - Users are saved locally using Room Database
  - Data persists after app closes

- **Architecture**
  - Clean Architecture separation: Data, Domain, Presentation
  - MVVM pattern with ViewModels and UseCases

- **Dependency Injection**
  - Uses Hilt to inject ViewModels, Repositories, DAOs

- **UI & Navigation**
  - Built entirely in Jetpack Compose
  - Navigation component for screen routing

- **Testing**
  - Unit tests for UseCases, Validators, Repositories, ViewModels
  - UI tests for Compose screens

---

## 🧱 Architecture Overview

Follows **Clean Architecture** with layered separation:

```
Presentation
├── adduser
├── userslist
├── navigation
│
Domain
├── UseCases
├── Models
├── Validation
├── Mapper
│
Data
├── Room Entities & DAO
├── Repository Implementation
Core

```

## Architecture Diagram
                     ┌──────────────────────────────┐
                     │         Presentation         │
                     │     (Jetpack Compose UI)     │
                     │    ─ ViewModel (State)       │
                     └──────────────────────────────┘
                                  │
                                  ▼
                     ┌───────────────────────────────┐
                     │          Domain               │
                     │ ┌───────────────────────────┐ │
                     │ │ Use Cases (Business Rules)  │ 
                     │ └───────────────────────────┘ │
                     │ ┌──────────────────────────┐  │
                     │ │      Models (Pure)       │  │
                     │ └──────────────────────────┘  │
                     │          Mappers              │
                     └───────────────────────────────┘
                                  │
                                  ▼
                     ┌──────────────────────────────┐
                     │           Data               │
                     │ ┌──── Repository Impl ─────┐ │
                     │ │   - Room (DAO)           │ │
                     │ │                          │ │
                     │ └──────────────────────────┘ │
                     └──────────────────────────────┘

---

## 📁 Project Structure

```text
src/
└── main/
    ├── java/com/madarsoft/task/
    │   ├── core/              # Base components and utilities
    │   ├── data/              # Room DB, DAO, repository
    │   ├── domain/            # UseCases, validation, models
    │   ├── di/                # Hilt modules
    │   └── presentation/      # UI Screens and ViewModels
    └── res/
        ├── layout/            # (for using XML components)
        └── values/            # Themes, strings, colors
```

---

## 📦 Dependencies

- **Jetpack Compose** – Declarative UI framework
- **Room** – Local database
- **Hilt** – Dependency injection
- **Coroutines** – Asynchronous operations
- **Navigation Component** – Screen transitions
- **ViewModel & LiveData** – Lifecycle-aware state management
- **WorkManager** – Background tasks
- **Testing**:
  - `JUnit`, `Mockk`, `Truth`, `Mockito` for unit tests
  - `Espresso`, `Compose UI Test` for UI testing

---

## 🧪 Testing

- ✅ UseCase tests
- ✅ Validator logic tests
- ✅ Repository behavior tests
- ✅ ViewModel logic
- ✅ Instrumented UI tests for Compose screens

---

## 📸 Screenshots

![XML Screens](/res/xml.png)

![Compose Screens](/res/compose.png)


## you can test the app :
find apk from url https://drive.google.com/drive/folders/1g2WPXgmOaib_sj83Rs93TfmzKlCYIVCQ?usp=sharing

---



## ✅ Requirements

- Android Studio Narwhal
- Kotlin 2.2.0
- Min SDK: 24
- Gradle (Kotlin DSL)

---

## 👤 Author

**Ali hrhera**  
Email: alihrhera1@gmail.com

---

## 📚 Useful References

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Hilt for Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)

## 🌿 Git Branching Strategy

The project follows a clear and structured Git branching strategy to maintain clean collaboration and code quality.

### 🔀 Branches Used

- `develop`: Main integration branch where all completed features are merged.
- `feature/users-list`: Responsible for implementing the user list screen.
- `feature/add-users`: Handles the add user feature.
- `feature/create-core`: Sets up core business logic and base classes.
- `feature/create-local-db`: Configures the Room database and local storage layer.

### ✅ Merging Process

All feature branches were merged into `develop` via **Pull Requests (PRs)** to ensure code review and maintain stability.

> This project loosely follows the Git Flow model to support scalability and teamwork.

