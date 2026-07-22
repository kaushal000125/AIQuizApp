# AIQuizApp

AIQuizApp is a beginner-friendly Java console application that generates multiple-choice Java quiz questions using the Groq AI API. It allows users to enter a Java topic and answer AI-generated quiz questions directly from the terminal.

## Features

* Generates 3 beginner-level Java MCQ questions
* Uses Groq AI API for dynamic quiz generation
* Includes a fallback demo quiz if the API fails
* Tracks and displays the final score
* Simple Java console application — no external libraries required

## Technologies Used

* Java 11+ (uses `java.net.http.HttpClient`)
* Groq API
* Java Collections and Arrays

## Project Structure

```
AIQuizApp/
│── AIQuizApp.java
│── README.md
```

## Setup and Run

### 1. Clone the repository

```bash
git clone https://github.com/kaushal000125/AIQuizApp.git
cd AIQuizApp
```

### 2. Set your Groq API key

**Windows (Command Prompt):**

```cmd
set GROQ_API_KEY=your_new_api_key_here
```

**Windows (PowerShell):**

```powershell
$env:GROQ_API_KEY="your_new_api_key_here"
```

**Linux / macOS:**

```bash
export GROQ_API_KEY=your_new_api_key_here
```

### 3. Compile the Java file

```bash
javac AIQuizApp.java
```

### 4. Run the application

```bash
java AIQuizApp
```

## Example

```text
Groq Java Quiz Generator
Enter Java topic: loops

Question 1
Which loop is commonly used to repeat code?
A. if
B. for
C. class
D. import

Your answer A/B/C/D: B
Correct!
```

## Important Security Note

Do **not** hardcode your API key in the source code before uploading to GitHub. This project reads the Groq API key from the `GROQ_API_KEY` environment variable to keep your credentials secure.
