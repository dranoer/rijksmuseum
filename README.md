# Rijksmuseum App

This application utilizes the [Rijksmuseum API](https://data.rijksmuseum.nl/object-metadata/api/) to display artwork and related details.
<br><br>

## Application Structure 📚

The application follows the Model-View-ViewModel (MVVM) architectural pattern. Here's a brief overview of the main components:

- `data`: Contains remote data handling elements like API response models (`ArtResponse.kt`, `DetailResponse.kt`), data mappers (`ArtMapper.kt`, `DetailMapper.kt`), and the service interface for network requests (`WebService.kt`).

- `di`: This is where all the dependencies for the app are provided, specifically in the `AppModule.kt`.

- `domain`: Contains business logic components like repositories. For example, `ArtRepository.kt`.

- `ui`: This package is dedicated to UI-related classes, further divided into components, screens, and utility classes. Components like `ArtView.kt` and `DetailView.kt` represent individual UI pieces. Screens like `DetailScreen.kt` and `OverviewScreen.kt` represent complete UI screens. Utility classes like `OnClickListener.kt` and `Route.kt` handle click events and navigation, respectively. Also `MainActivity.kt`, `MainViewModel.kt`, and `RijksmuseumApp.kt`present in the package.
<br>

## Features 🚀
- Overview page with a paginated list of items
- Detail page providing more information about each artwork
- Asynchronous data and image loading
- Tests
- Automated publish CI/CD using GitHub Actions
<br>

## Tech Stack 💻

The following libraries and tools have been used for this project:

- **Kotlin** as the main language.
- **Jetpack Compose** for building the UI.
- **AndroidX Paging Library** for handling pagination in the application.
- **Navigation-Compose** for handling in-app navigation.
- **Coil** for image loading.
- **Kotlin Coroutines & Flow** for handling asynchronous tasks.
- **Hilt** for dependency injection.
- **Retrofit** for making network requests.
- **OkHttp3** for implementing interceptor, logging and networking.
- **GSON Converter** for parsing JSON.
- **JUnit, MockK and Kotlinx Coroutines Test** for Testing.
<br>

## Future Improvements 🛠

While the current state of the project is functional and robust, there's always room for growth and enhancement. Here are some areas that could be improved in the future:

- **Expand Test Coverage**: Although some tests are already present, the test coverage could be broadened to encompass more cases, ensuring the reliability and stability of the application.

- **Project Modularization**: Breaking the application down into modules could increase build speed, enable reuse of code, and aid in separating concerns even further. This would involve categorizing the code into more specific packages or even separate modules altogether.
  
- **Enhanced Error Handling**: Currently, the application handles basic error scenarios. This could be expanded to more comprehensively manage different error types and provide more descriptive error messages to the user. This would enhance the user experience by providing clear feedback when something goes wrong.
<br>

---
<br>

## Latest Update 🎉

Project modularization, an area identified earlier for future improvements, has been implemented. The application now has a more structured codebase organized into two modules: `app` and `networking`.

- The `app` module contains all user interface related components and logic, essentially all the user-facing elements of the application.

- The `networking` module, on the other hand, encapsulates all network-related tasks and entities. It includes the API service definitions, response models, and data mappers.

This significant architectural update has enhanced the clarity and maintainability of the codebase, paving the way for efficient future developments and iterations.

<br>
