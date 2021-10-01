# Internshala Workshop - Assignment

This repository contains the project that I was asked to make as an assignment.

## 💁‍♂️ About the application

This is a simple application that is used to register students to particular workshops. This application is a demo and lack some essential features. All the values in the application database is hardcoded (except user data and enrollment data).

### 🧪Download and test:

The application is not on Google PlayStore but you can install it from the releases _(only for testing and feedback purpose)_.

> **Download apk** to run and test the application from [Releases](https://github.com/amannirala13/Internshala_Workshop/releases).

## 👨‍💻 Technologies used

There are very basic technologies used in the application. They are lister below:

- **Platform and Language**
  - Kotlin
  - Android SDK
  - Android Studio (IDE)
- **Database**
  - SQLite (local)

## 🚶‍♂️ Application flow

> Note: `student` and `user` are the same entity and might be used interchangeably

The flow of the application is very basic and as asked in the requirements document provided. Lets discuss:

- First the application opens the `SplashScreen` activity that is just for branding. The `SplashScreen` after `2.5 sec` will open either `Introduction`(if its the first time the application is running) or `MainActivity`. The `Introduction` has a button which will ultimately lead the user to the `MainActivity`.

**MainActivity**

- In the `MainActivity` there are 3 fragments
  - `ProfileFrag` - The Home or the first fragment
  - `WorkshopFrag` - The 2nd fragment that contains all the workshops list.
  - `SettingsFrag` - The 3rd fragment that contains some basic information
- The `ProfileFrag` shows a `Sign in` button if the user is not logged in else it will show all the courses the student is rolled into if he/she is logged in. Student can choose to roll out of a course if they want to by clicking on the `Rollout` button on the workshop card.
- The `WorkshopFrag` shows all the available workshops even if the student is not logged in. Every workshop card has an `Apply` button which will redirect the student to `Authentication` activity if he/she is not logged in else it will enroll the student to the the workshop
- The `SettingsFrag` has some trivial information and an authentication button that will either login or logout the user depending upon the present auth state.

**Authentication**

- In the `Authentication` activity there are 2 fragments:
  - `SignInFrag` - Already registered users and log in using this fragment
  - `RegisterFrag` - New users can register using this fragment

## 🧱 Project Structure

The project is modulated with proper dependencies and each feature having a dedicated module. Let's see the module structure.

- ### **:app**
  This is is the core module containing the major business logic i.e the workshop enrollment operations.
- ### **:app:auth**
  This is the authentication module, responsible for user authentication process.
- ### **:app:database**
  This is the database module, responsible for all database related tasks and code.
- ### **:app:intro**
  This is the introduction module, responsible for the application first run and introduction events
- ### **:app:utils**

  This is the utility module, contains all check functions, constants and other useful functions used through out the application

> Tip: Check `build.gradle` files of each modules to understand their dependency on each other.

## 📂 Database

This project uses SQLite and thus uses SQL based relational database. Check `:app:database` module for the code related to the database.

The database is called `internshala_workshop.db`. It has 3 tables:

1. `students` - Contains registered students
2. `workshop` - Contains workshop details (hardcoded)
3. `enroll` - Contains enrollment details

### `students`

| id  | name | email | password | phone |
| --- | ---- | ----- | -------- | ----- |

### `workshop`

| id  | title | agenda | organizer | duration | tags |
| --- | ----- | ------ | --------- | -------- | ---- |

### `enroll`

| id  | student_id | workshop_id |
| --- | ---------- | ----------- |
