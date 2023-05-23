(ns my-body-app.login
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [my-body-app.utils :refer [write-current-user read-current-user authenticate-user]]
            [clojure.java.io :as io]
            [cheshire.core :as json]
            [ring.util.response :refer [redirect]]))






(defn handle-login [params]

  (let [user (authenticate-user (:username params) (:password params))]
    (if user
      (do 
        (write-current-user user) 
        (hiccup/html
         [:script "setTimeout(function() { window.location.href = '/';console.log('Timeout complete'); }, 500);"]))
      
      "Invalid username or password.")))



(defn login-page []
  (let [current-user (read-current-user)]
    (if (empty? current-user)

      (layout
       "Login page"
       (hiccup/html
        [:h1 "Please Log In"]
        [:form {:action "/login" :method "POST"}
         [:label {:for "username" :style "display:inline-block; width:120px; margin-bottom:20px;" } "Username:" ]
         [:input {:type "text" :name "username"}]
         [:br]
         [:label {:for "password" :style "display:inline-block; width:120px; margin-bottom:20px;" } "Password:"]
         [:input {:type "password" :name "password"}]
         [:br]
         [:input {:type "submit" :value "Log In" :style "margin-left:120px; width:120px; cursor:pointer;"}]]))
      (redirect "/"))))
   


   