(ns my-body-app.users
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [my-body-app.utils :refer [read-current-user]]))

(defn handle-changes [params]
  
)

(defn users-page[]
  (layout
   "users page"
   (hiccup/html
    [:h1 "Hello " (:name (read-current-user)) ", and welcome to the Home Page!"]
    [:p "This is the content of the home page."])))