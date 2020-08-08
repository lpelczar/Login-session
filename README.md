# Login session

## Requirements

Your task is to create a login form. If a user logs in he should see "Hello <name>!". Information about users state (logged 
in/out) should be stored in the session. This means that you have to implement session mechanism on your own.

- Implement The Session mechanism. See the previous materials. Session data could be stored in-memory, you don't have to 
use a database for this exercise.
- Implement login mechanism. When a user provides proper credentials, he/she gets logged in. From now on every time the 
user refreshes the page he/she sees "Hello <name>!". If the user presses the Log out button, then the session is terminated
and he/she sees the login page again.
- Store users' data in a database. 
- Your application should be able to handle multiple users. Log in in your browser as user A. Open incognito window in your 
browser and try to log in as user B. Check if those users can work separately.
- *(Additional) store passwords as hashes in the database.

## Technologies

- Java HttpServer
- HTML5
- CSS
- JTwig
- SQLite database

## Sample screenshots

![c1](https://raw.github.com/lpelczar/Login-session/master/session.png)

## More info

Project made for [Codecool](https://codecool.com/) programming course.
