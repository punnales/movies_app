# Movie App
Just a movies app :).

## Open Source libraries used
- **Kotlin Coroutines** and **Flow** for asynchronous operations.
- **Material design** for modern UI modeling
- **Glide** Image procesing in a simple and performant way.
- **Retrofit & OkHttp** A type-safe HTTP client for Android and Java.
- **Moshi** A modern JSON library for Kotlin and Java
- **Lottie** Vector animation drawables in a simple way.
- **Hilt** for dependency injection.
- **Exoplayer** is an application level media player for Android. It provides an alternative to Android’s MediaPlayer API for playing audio and video both locally and over the Internet.
- **Android Jetpack Stack**
  - **Navigation Component** is the API and the design tool in Android Studio that makes it much easier to create and edit navigation flows. 
  - **Datastore & Protobuff** is a data storage solution that allows to store key-value pairs or typed objects with protocol buffers.
  - **Room** Provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
  - **Paging3** Helps to load and display pages of data from a larger dataset from local storage or over network. 

## Architecture used
It was developed with a base of CLEAN, MVI and some rules of MVVM architecture. It is ready to be upgraded to multimodule app, keeping isolated the different modules and layers of a CLEAN + MVI application. In each portion of any feature you can see a unidirectional flow:
![w_6vjijlycjkq6rsgxw4kfaypo8](https://user-images.githubusercontent.com/47196250/167863592-e7d025c4-1c72-4022-b459-2320ee186f2f.png)


The repository pattern was omited by the use of Interactors(Use cases), I think that in CLEAN implementations it is just a facade of two datasources. [More info here](https://www.techyourchance.com/repository-android-anti-pattern/)
