(ns my-body-app.login
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]))

(defn login-page []
  (layout 
   "Login page" 
   (hiccup/html
    [:h1 "Please Log In"]
    [:form {:action "/login" :method "POST"}
     [:label {:for "username"} "Username:"]
     [:input {:type "text" :name "username"}]
     [:br]
     [:label {:for "password"} "Password:"]
     [:input {:type "password" :name "password"}]
     [:br]
     [:input {:type "submit" :value "Log In"}]]) 
   ))