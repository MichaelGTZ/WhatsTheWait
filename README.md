# What's The Wait?

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
An app that provides real-time data on your favorite restaurant's availability, so you know exactly how long you have until you will be seated. Book a reservation at any time, from anywhere, and browse an online menu while you wait. As soon as your time to be seated approaches, place orders on the app to have drinks and an appetizer waiting for you at your table and the main course being prepared in the kitchen. Or, for carry out, place your order on the app and receive a notification to start heading to the restaurant to pick up your order as soon as it is ready.


### App Evaluation
- **Category:** Food & Drink / Social
- **Mobile:** For ease of access and quick actions, mobile will be the dominant platform for finding open restaurants with low wait times on a moments notice or for booking a reservation and browsing a menu on your own time and own device.
- **Story:** Giving notice to long wait times and allowing for pre-ordering will streamline user experience and restaurant efficiency, making for a more enjoyable experience for short notice dinners or reservations made days in advance.
- **Market:** Higher end restaurants will be the biggest market for dine in traffic tracking and menu pre-ordering, but, in a post-pandemic world, a more attractive take out offering will be supported.
- **Habit:** With decisions on where to eat not being that easy, being able to gather more data on what is reasonably empty, has the food you're looking for, and can be reserved instantly might make those decisions easier and the app can become your companion to social or quick meal plans.
- **Scope:** V1 (MVP): Wireless access point data is essential to understanding an idea of the traffic in the restaurant and use that to create a visual or quantitative estimation on how busy the restaurant is. Allow restaurants to upload and easily update their menus. Provide contact information to the restaurant to allow for traditional booking/reservations. V2: Provide in-app reservation abilities as well as menu ordering as long as the user is within X time from being seated (so some dynamic metric of wait times after reserving is necessary). V3: Add additional insights like typical time spent in one sitting, typical party size, highest and lowest traffic days and times, and online ratings and reviews. V4: Support for carry out orders, wherein you are able to place your order through the app and will be notified when it is ready so you can travel to the restaurant and pick it up on time.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
* A way to measure and report dynamic traffic data for each restaurant via Google Places API (or similar API)
* List view on home screen (Map view comes later)
    * filters for list view based on proximity (default)
* A way to search for restaurants
* A way to estimate wait times based on party size
* A way to book reservations for one's party size
* A way to count down time-till-seated to open menu and allow for ordering
* A way to browse menus via SinglePlatform API (or similar)
* A way to handle carry out orders with its own timing system.
* A way for users to "favorite" restaurants to add them to their profile

**Optional Nice-to-have Stories**
* A Google Maps view of nearby restaurants, their Yelp reviews, estimated wait time, and a preview of their menu. A compliment to search function.
* Filter search results by rating, distance, party size, etc.
* Daily/Weekly deals unique to each restaurant advertised on its main page.
* Carry out orders with timers based on how long it will take, according to Google Maps, to travel to restaurant based on current conditions.
* Ability to review restaurants from the app.
* Add highest and lowest traffic days/times for each location.
* Implement a rewards system similar to Opentable to promote trying new restaurants as well as earning rewards for repeat customers.
* Allow check splitting within the app via Splitwise API (or similar)
* Allow users to invite other users to their reservation and have them RSVP. This requires a friends list system.
* Display how many favorites each restaurant has by all users on the platform
* After eating at a restaurant, allow users to rate on a 1-5 star basis and show that for each restaurant
* Let users save their favorite meals for each restaurant they visit

### 2. Screen Archetypes

* Log In Screen
* Sign Up Screen
* Home Feed
    * Search bar
* Favorites Feed
* Restaurant Detaied View
    * Current traffic inside of restaurant
    * Estimated wait times
    * Menu
* Make Reservation
    * Dine in
        * Menu viewing
        * Order placing (if <= x minutes till seated)
        * Count down till seated
    * Take out
        * Menu viewing and immediate order placing
        * Count down till food ready for pickup
* Profile
    * Log out
    * Reset password


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Feed
* Favorite Restaurants
* My Reservations
* My Profile

**Flow Navigation** (Screen to Screen)

* Home Screen showing Login
    * Links to Account Sign up
* Home Screen (after authentication) List view of nearby restaurants (Map later)
    * Click on any restaurant for more information on it and to book
    * Filter by food type, closing soon, etc.
    * Search bar at the top
    * Bottom nav bar showing current home screen, favorites tab, my future reservations, and my account.
* Favorites tab (List view first)
    * List view of your favorite restaurants and some basic info about them
        * Click on it to go the same detailed view from the home screen
* My Reservations
    * List of your open reservations that have been approved or are pending.
        * Each reservation shows the people invited and their RSVP status.
* My profile
    * Reset password
    * View your favorite restaurants list
    * Log out

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]

# Additional Documentation
- [App Benchmark and Brainstorm](https://hackmd.io/@MichaelGTZ/AppPlanning)
- [App Design README Template (Used for this README)](https://hackmd.io/@MichaelGTZ/AppDesignMilestone)
