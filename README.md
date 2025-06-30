# Gallery App

An Android gallery application built with Jetpack Compose, following clean architecture principles and Material Design 3 guidelines.

## Features

- **Album Browsing**: View all photo and video albums on your device
- **View Modes**: Switch between grid and list layouts for optimal viewing
- **Media Preview**: Browse individual photos and videos within albums
- **Video Thumbnails**: Automatic video thumbnail generation with play overlay
- **Permission Handling**: Graceful permission request flow with user-friendly prompts
- **Responsive Design**: Adapts to different screen sizes and orientations
- **Modern UI**: Material Design 3 components with smooth animations

## Architecture

The app follows clean architecture principles with clear separation of concerns:

```
app/
├── feature/
│   ├── galleryview/          # Album list feature
│   │   ├── data/            # Data layer implementation
│   │   ├── domain/          # Business logic and use cases
│   │   └── presentation/    # UI components and ViewModels
│   └── gallerydetails/      # Album detail feature
│       ├── data/            # Data layer implementation
│       ├── domain/          # Business logic and use cases
│       └── presentation/    # UI components and ViewModels
├── core/                    # Shared components and utilities
│   ├── components/          # Reusable UI components (now in separate module)
│   ├── navigation/          # Navigation configuration
│   ├── permission/          # Permission handling utilities
│   ├── theme/               # Material Design 3 theming
│   └── util/                # Constants and utilities
└── components/              # Separate reusable components module
```

## Tech Stack

- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Clean Architecture
- **Dependency Injection**: Hilt
- **Navigation**: Jetpack Navigation Compose
- **Image Loading**: Coil
- **State Management**: Kotlin Flow with lifecycle-aware collection
- **Testing**: JUnit, Compose Testing, MockK
- **Build System**: Gradle with Kotlin DSL

## Project Structure

### Core Components Module

The app includes a separate `components` module containing reusable UI components:

- **CircularLoader**: Loading spinner with accessibility support
- **ErrorMessageCard**: Error display with optional retry functionality
- **ViewModeToggle**: Toggle between grid and list view modes
- **GridLayout**: Reusable grid layout component
- **ListLayout**: Reusable list layout component
- **PermissionPromptCard**: Permission request card with Material Design 3

### Feature Modules

#### Gallery View Feature
- **AlbumListViewModel**: Manages album list state and data loading
- **AlbumListScreen**: Main screen displaying all albums
- **AlbumCard**: Individual album card component

#### Gallery Details Feature
- **AlbumDetailViewModel**: Manages album detail state and media loading
- **AlbumDetailScreen**: Screen displaying media within an album
- **MediaItemCard**: Individual media item card component

### Domain Layer

- **FetchAlbumCollectionUseCase**: Retrieves all albums from the device
- **RetrieveAlbumContentsUseCase**: Fetches media items within a specific album
- **MediaRepository**: Interface for media data operations
- **Album & MediaItem**: Domain models representing core entities

## Setup

### Prerequisites

- Android Studio Hedgehog or later
- Android SDK 34 or later
- Kotlin 1.9.0 or later

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/gallery-app.git
cd gallery-app
```

2. Open the project in Android Studio

3. Sync Gradle files and build the project

4. Run the app on a device or emulator

### Permissions

The app requires the following permissions:
- `READ_EXTERNAL_STORAGE` - To access photos and videos
- `READ_MEDIA_IMAGES` - For Android 13+ devices
- `READ_MEDIA_VIDEO` - For Android 13+ devices

## Usage

1. **Launch the App**: Open the Gallery App
2. **Grant Permissions**: Allow storage access when prompted
3. **Browse Albums**: View all your photo and video albums
4. **Switch Views**: Use the toggle button to switch between grid and list layouts
5. **Open Album**: Tap on an album to view its contents
6. **View Media**: Browse photos and videos within albums

## Testing

### Unit Tests

Run unit tests with:
```bash
./gradlew test
```

### Android Tests

Run Android tests with:
```bash
./gradlew connectedAndroidTest
```



### Dependencies

Key dependencies are managed through the version catalog in `gradle/libs.versions.toml`:
- Compose BOM for consistent versioning
- Hilt for dependency injection
- Coil for image loading
- Navigation Compose for navigation
