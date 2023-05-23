(ns my-body-app.login
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [my-body-app.utils :refer [write-current-user get-users hash-password]]
            [clojure.java.io :as io]
            [cheshire.core :as json]))




(defn authenticate-user [username password]
  (let [users (get-users)
        user (first (filter #(= username (:username %)) (:users users)))]
    (and user
         (= (hash-password password) (:password user))
         user)))

(defn handle-login [params]

  (let [user (authenticate-user (:username params) (:password params))]
    (if user
      (do 
        (write-current-user user) 
        (hiccup/html
         [:script "setTimeout(function() { window.location.href = '/';console.log('Timeout complete'); }, 500);"]))
      
      "Invalid username or password.")))



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
      [:input {:type "submit" :value "Log In"}]])))
   


   