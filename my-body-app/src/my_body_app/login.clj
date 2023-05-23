(ns my-body-app.login
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [clojure.java.io :as io]
            [cheshire.core :as json]))

(defn get-users []
  (-> "users.json"
      io/resource
      slurp
      (json/parse-string true)))


(defn authenticate-user [username password]
  (println username)
  (let [users (get-users)
        user (first (filter #(= username (:username %)) (:users users)))]
    (and user
         (= password (:password user))
         user)))

(defn handle-login [params]

  (let [user (authenticate-user (:username params) (:password params))]
    (if user
      (str "Welcome, " (:name user))
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
   


   