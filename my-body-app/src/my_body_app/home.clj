(ns my-body-app.home
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]))

(defn home-page []
  (layout 
   "Home page"
  (hiccup/html
   [:h1 "Welcome to the Home Page!"]
   [:p "This is the content of the home page."])))