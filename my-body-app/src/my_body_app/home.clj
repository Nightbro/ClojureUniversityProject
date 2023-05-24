(ns my-body-app.home
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [my-body-app.utils :refer [read-current-user]]))

(defn home-page []
  (let [user (read-current-user)]
    (if (empty? user)
      (layout "Home Page - Default"
              (hiccup/html
               [:h1 "Welcome to the Home Page"]
               [:p "Please sign in to use this application."]))
      (layout "Home Page - Logged In"
              (hiccup/html
               [:h1 (str "Hello " (:name user) ", and welcome to the Home Page!")]
               [:p "This is the content of the logged-in home page."])))))
