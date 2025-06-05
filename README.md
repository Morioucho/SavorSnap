<p align="center">
    <img src="docs/images/SavorSnap.png" width="400px" alt="SavorSnap Logo">
</p>

<p align="center">
    <i>SavorSnap is an AI-enabled tool that helps you make the best meal choice with your current ingredients.</i>
</p>

In the United States alone, people waste 92 billion pounds of food annually, equal to 145 million meals. With SavorSnap, we aim to reduce food waste globally by introducing people to more recipes they may like.

## Features
- **AI-enabled:** Use AI to identify your ingredients from an image.
- **Snap to Search:** Take a picture of all your ingredients and find relevant recipes based on our AI model.
- **Rich User Profile:** Put your preferences, such as allergies, dietary restrictions, cuisine, and spice tolerance, to automatically find recipes that suit your needs.
- **Favorite Recipes:** If you find a recipe you like, you can favorite it to save it for future reference.
- **Massive Catalog:** Access a massive catalog of recipes indexed by cuisine and various other factors.

## Quick Start
In order to run SavorSnap, you will need the following installed:
- [JDK 17](https://adoptium.net/temurin/releases/?os=any&arch=any&version=17) **or higher.**
- [Python 3.12](https://www.python.org/) **or higher.**
- [Tensorflow](https://www.tensorflow.org/)
- [Docker](https://www.docker.com/)
- [Node.js](https://nodejs.org/en)

Before running the project, you will need to compile the Springboot project; we do not need to compile the JavaScript or the Python, as they are **interpreted.**

```bash
# This is assuming you're in the /savorsnap/ folder.
cd backend

# (!!) macOS Only - You must give mvnw execute (+x) permissions, you only have to run this once!
chmod +x ./mvnw

# Compile the Springboot project and generate a `.jar` file, we can skip tests as our database connection will fail without other Docker containers running.
./mvnw compile -DskipTests
```

Following that, you can run the entire project using Docker:
```bash
# This is assuming you're in the /savorsnap/backend folder.
cd ..

# Run with Docker, consecutive runs do NOT need the "--build" flag, you only need it for your inital build.
docker compose up --build

# Once you're done, you can close down docker.
docker compose down
```

## Architecture
This project is built using SpringBoot (Java), Tensorflow (Python), React.js (JavaScript), and PostgreSQL (SQL).

### SpringBoot
The Springboot layer is responsible for persisting data and managing the API for the frontend to access. The Springboot layer connects to the PostgreSQL Database via JDBC drivers.

The Springboot layer is also responsible for managing User data and setting up the APIs. User data is persisted into the PostgreSQL, and in simplified terms, is contained in this POJO (Plain Old Java Object).

> [!IMPORTANT]
> This class below is just a mockup, in reality, user information, such as passwords and allergies, is encrypted using AES.

```java
// MOCKUP

public class User {
    private String username;
    private String password;

    private List<int> favoriteRecipies;
    private List<int> allergies;
    private List<int> preferredCuisines;

    private float spiceTolerance; 

    // Getters and setters..
}
```

The Springboot layer also provides an API for the frontend to interact with the database as well as for the frontend to interact with the AI layer. These endpoints are shown better in the API's **Swagger**.

### React.js
We use React.js to create the frontend for our website. Some aspects of user authentication are also taken care of in the React.js layer.

The React.js layer is also responsible for managing the user's sessions and tokens in a cookie to prevent the need to login repeatedly.

### Tensorflow
Tensorflow is used to create the AI model that is used for analyzing inputted images. When a user uploads an image into the Springboot layer, it is redirected here in order to analyze and return the list of ingredients spotted in the image.

We use algorithms such as morphological image processing in order to save computing power and generate more accurate results.

## Licensing
This project is licensed under the **GPU GPL v3** license. You can read this license [here](LICENSE).

## Credits
SavorSnap is developed by Saumil Sharma, Kason Lai, Ceazar Jensen Opaon, and Christian Hsu.

