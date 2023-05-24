# ClojureUniversityProject
An university project made in clojure. Started as a Game application, ended as a web application for body measurements. 

## Used repositories:
https://repo.clojars.org/ring/
https://repo.clojars.org/cheshire/cheshire/



# Plan
- [x] Setup an app
- [x] Add pages and a basic style
- [x] Add data storage for users
- [x] Add menu list
  - [x] Add general menu
  - [x] Add logged in menu
  - [x] Add admin menu
- [x] Add a login functionality
  - [x] Style
  - [x] Hashing of the password 
  - [x] Different menu depending on whether you are logged in or not
- [x] Add a session to store information about logged user - Done, used current.json file since the session cookies didnt seem to work
  - [x] remove password from the logged user info
- [x] Add a BMI calculator page, with option to store information in database
- [x] Register user
- [x] View a randomly generated plan for today

- [ ] To add 
    - [x] Round up to two decimals the BMI and calory intake

- [ ] To be added in future
    - [ ] Page to add, update and delete users 
    - [ ] Page to add, update and delete meals
    - [ ] Review history of body measurements
    - [ ] Page to review and update your general information made on registration
    - [ ] Migrate to a database engine
    - [ ] Add more options to Meal plan

