In app notification demo for integrating betteruptime annoucements in android app

This demo includes practice with:
- Retrofit/Gson
- Databinding
- Dependency Injection with Hilt
- Fragment/Activity -> ViewModel -> Repository (Room implementation coming soon)

1. Make api call with your tool of choice. I used postman.
Do this to view how json object will look like when it is requested.

2. Now we know how to model the json object. Create your classes so Retrofit/Gson can map.
Betteruptime.java

3. Create the api interface retrofit will be implementing.
BetteruptimeApi.java

4. Create the fragment class and the xml layout for it
AnnouncementFragment.java
fragment_announcement.xml
** Make sure whatever layout you use (ie framelayout, etc) is wrapped in layout tag and move names to the tag **
** This is to implement databinding between fragment and viewmodel **

5. Speaking of viewModel, create the viewmodel class
AnnouncementViewModel.java


