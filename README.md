# Android Framework

A modular Android application framework designed for rapid app development with best practices and modern Android architecture.

## Features

- **Multi-Module Architecture**: Clean separation of concerns with modular design
- **Hilt Dependency Injection**: Type-safe dependency injection using Dagger Hilt
- **Retrofit Network Layer**: REST API integration with real weather API
- **Room Database**: Local data persistence with SQLite
- **DataStore**: Modern preference storage replacing SharedPreferences
- **Unidirectional Data Flow**: MVI pattern with Repository pattern
- **Common Utilities**: Reusable utility classes and extensions

## Project Structure

```
├── app/                          # Main application module
├── core/
│   ├── common/                   # Common utilities and extensions
│   ├── network/                  # Retrofit network layer
│   ├── database/                 # Room database
│   ├── datastore/                # DataStore preferences
│   ├── domain/                   # Domain layer (use cases, repositories)
│   └── ui/                       # Base UI components
└── feature/
    └── weather/                  # Weather feature module
```

## Core Modules

### core:common
Contains common utilities and extensions:
- `Result` sealed class for handling async operations
- `DateUtils` for date/time formatting
- `LogUtils` for logging
- `NetworkUtils` for network state checking
- View and String extensions

### core:network
Retrofit-based network layer:
- OkHttp client with logging interceptor
- Weather API integration (OpenWeatherMap)
- Network response models

### core:database
Room database implementation:
- Weather entity and DAO
- Database migrations support
- Type converters

### core:datastore
DataStore preferences:
- User preferences management
- Theme settings
- App configuration

### core:domain
Clean architecture domain layer:
- Repository interfaces
- Use cases
- Domain models

### core:ui
Base UI components:
- BaseFragment with ViewBinding
- BaseViewModel with MVI pattern
- UI state management

## Feature Modules

### feature:weather
Weather feature implementation:
- Weather display with real API data
- City search functionality
- Offline caching support
- Pull-to-refresh

## Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 34

### Building
```bash
./gradlew assembleDebug
```

### Running Tests
```bash
./gradlew test
```

## Architecture

The project follows Clean Architecture principles with MVVM/MVI pattern:

```
UI Layer (Fragment/Activity) 
    ↓ Events
ViewModel (State management)
    ↓ Use Cases
Domain Layer (Business logic)
    ↓ Repository
Data Layer (Network/Database)
```

## Dependencies

- **Kotlin**: 1.9.22
- **Hilt**: 2.50
- **Retrofit**: 2.9.0
- **Room**: 2.6.1
- **DataStore**: 1.0.0
- **Coroutines**: 1.7.3
- **Lifecycle**: 2.7.0

## License

This project is available under the MIT License.
