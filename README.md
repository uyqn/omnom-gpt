# Omnom-GPT 🍽️

![GitHub Release](https://img.shields.io/github/v/release/uyqn/omnom-gpt?style=flat-square)
![License: MIT](https://img.shields.io/github/license/uyqn/omnom-gpt?style=flat-square&color=blue)
![Codecov](https://img.shields.io/codecov/c/github/uyqn/omnom-gpt?logo=codecov&style=flat-square)

Omnom-GPT is a smart meal-planning application that combines the power of OpenAI with a Spring Kotlin backend and a React TypeScript frontend. The app helps users plan weekly dinners based on the ingredients they already have at home, making it easy to create personalized, efficient, and cost-effective meal plans.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Setting Up the Backend](#running-the-server)
    - [Setting Up the Frontend](#running-the-client)
- [How It Works](#how-it-works)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **Personalized Meal Plans**: Generate weekly meal plans based on the ingredients you already have.
- **Smart Shopping Lists**: Get a list of missing ingredients needed to complete your meal plan.
- **Customization**: Add optional keywords tailor plans to your preferences.
- **Save and Rate Meals**: Save favorite meals, rate them, and reuse them later.
- **Editable Plans**: Modify generated plans or recipes to better fit your needs.

---

## Technologies Used

### **Server**
![Build Status](https://img.shields.io/github/actions/workflow/status/uyqn/omnom-gpt/client.yml?style=flat-square&logo=github-actions)
![Codecov](https://img.shields.io/codecov/c/github/uyqn/omnom-gpt?flag=server&logo=codecov&style=flat-square)

![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)
![Gradle](https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-74aa9c?style=for-the-badge&logo=openai&logoColor=white)
![JUnit5](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
- **Framework**: Spring
- **Language**: Kotlin 
- **Build Tool**: Gradle
- **Database**: PostgreSQL
- **Integration**: OpenAI
- **Testing**: JUnit 5

More details about the server can be found [here](server/README.md).

### **Client**
![Build Status](https://img.shields.io/github/actions/workflow/status/uyqn/omnom-gpt/client.yml?style=flat-square&logo=github-actions)
![Codecov](https://img.shields.io/codecov/c/github/uyqn/omnom-gpt?flag=client&logo=codecov&style=flat-square)

![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=FFD62E)
![TypeScript](https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white)
![Jest](https://img.shields.io/badge/Jest-C21325?style=for-the-badge&logo=jest&logoColor=white)

- **Framework**: React
- **Language**: TypeScript
- **Testing**: Jest

More details about the client can be found [here](client/README.md).

### **Other Tools**
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Github Actions](https://img.shields.io/badge/Github_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)
![CodeCov](https://img.shields.io/badge/Codecov-F01F7A?style=for-the-badge&logo=codecov&logoColor=white)
- **Version Control**: Git
- **Hosting**: GitHub
- **Containerization**: Docker
- **CI/CD**: GitHub Actions
- **Code Coverage**: Codecov

---

## Getting Started

Follow these steps to run the project locally.

### Running the server
1. Clone the repository:
    ```bash
    git clone https://github.com/uyqn/omnom-gpt.git
   ```
2. Change directory to the project folder:
    ```bash
    cd omnom-gpt
    ```
3. Create a `.env` file in the `root` directory:
    ```bash
   touch .env
   ```
4. Add the following environment variables to the `.env` file:
    ```bash
    OPENAI_API_KEY=your_openai_api_key
    OPENAI_RESOURCE=your_openai_resource
    POSTGRES_USER=omnom
    POSTGRES_PASSWORD=omnom
    ```
5. Start the PostgreSQL database:
    ```bash
    docker compose up -d
    ```
6. Run the server:
    ```bash
    ./gradlew bootRun
    ```
7. The server be available on `http://localhost:8080`.

### Running the client
1. From the `root` directory, run the following command:
    ```bash
    npm --prefix client install 
    ```
2. Start the client:
    ```bash
    npm --prefix client run dev
    ```
3. The client will be available on `http://localhost:3000`.

