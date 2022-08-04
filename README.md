# Task for a Great Android developer

If you found this task it means we are looking for you!

> Note: To clone this repository you will need [GIT-LFS](https://git-lfs.github.com/)

## Few simple steps

1. Fork this repo
2. Do your best
3. Prepare pull request and let us know that you are done

## Few simple requirements

- Send authorization request (POST) to http://playground.tesonet.lt/v1/tokens to generate token with body: `{"username": "tesonet", "password": "partyanimal"}`. (Don't forget Content-Type)
- Get servers list from http://playground.tesonet.lt/v1/servers. Add header to request: `Authorization: Bearer <token>`
- Design should be recreated as closely as possible
- Bonus: implement smooth animated transition from login through loader to server list screen
- Bonus: implement persistent storage of the downloaded server data
- Bonus: have a good set of unit tests

*Note:* The bonus requirements are optional. While they are nice to have, it's much more important to have the basics nailed.



Thank you so much for giving me the chance to complete the assignment.

This project is built using the following componets.

* Project is created using MVVM architecture
* I have used dagger-Hilt for dependency injection
* Retrofit for networking calls
* Coroutines
* View model and live data for updating the view
* View and data binding for binding the views
* Navigation components for fragment navigation
* Room database for persistent storage
* shared preferences for for storing login token.
* Recyclerview for showing the list
* Data binding for list items

Login screen is authenicating the user by fetching the login token from the api and on success authenication login token is
saved in shared preferences and the user will redirect to the next screen which is a server list screen.
Server List screen uses the authentication token and fetch the list of servers and saves in database.


