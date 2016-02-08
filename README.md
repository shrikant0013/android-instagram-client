# Project 1 - Instagram Popular Photos Viewer

Instagram Popular Photos Viewer is an android app that allows a user to check out popular photos from Instagram. The app utilizes Instagram API to display images and basic image information to the user.

Time spent: 6 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current popular photos** from Instagram
* [x] For each photo displayed, user can see the following details:
  * [x] Graphic, Caption, Username
  * [x] Relative timestamp, like count, user profile image

The following **optional** features are implemented:

* [x] User can **pull-to-refresh** popular stream to get the latest popular photos
* [x] Show latest comments for each photo
  * [x]  Show last 2 comments
* [x] Display each user profile image using a RoundedImageViewDisplay each user profile image using a [RoundedImageView](https://github.com/vinc3m1/RoundedImageView)
* [x] Display a nice default placeholder graphic for each image during loading
* [ ] Improved the user interface through styling and coloring

The following **bonus** features are implemented:

* [ ] Allow user to view all comments for an image within a separate activity or dialog fragment 
* [ ]  Allow video posts to be played in full-screen using the VideoView 
* [x] Apply the popular Butterknife annotation library to reduce view boilerplate

The following **additional** features are implemented:

* [x] Use View holder pattern

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/OMBI8S3.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [CircleImageView](https://github.com/hdodenhof/CircleImageView) - A circular ImageView for Android
- [Butterknife](http://jakewharton.github.io/butterknife/) - Butterknife annotation library 

## License

    Copyright [2016] [Shrikant Pandhare]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.