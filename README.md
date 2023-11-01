# Notes App

This application can allow users to create, save, update notes, log in, sign up, and access their profile to log out. This allows users to log in to a Firebase Authentication system in order to access notes that are stored in a Firebase Realtime Database. this project ensures that users can efficiently store notes in order to go about their life in a more organized manner.

## Functionality 

The following **required** functionality is completed:

* [✅] User sees all their previous notes from last session
* [✅] User can create new notes
* [✅] User can update pre-existing notes
* [✅] User can delete notes
* [✅] User can log in, sign up, and log out of Firebase Authentication
* [✅] User notes are stored in Firebase Realtime Database
* [✅] User can only access notes when signed in

The following **extensions** are implemented:
* Sign in and sign up pages are on separate fragments rather than all on one fragment to simulate a more accurate log in process.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

![](https://github.com/egoel5/C323_Project6/blob/project7/Project%207%20Video%20Walkthrough.gif)

GIF created with [Adobe Express](https://new.express.adobe.com).

## Notes

A challenge I had while building the project was that I couldn't get my note data to store in the Realtime Database because I had set the rules to locked mode on creation in the Firebase console. I fixed this by setting the rules so that anyone could access the database until I had my user logins working, after which I set the rules to only allow access to authorized users who have signed up. 

## License

    Copyright [2023] [Eshan Goel]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
