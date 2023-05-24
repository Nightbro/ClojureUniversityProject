(ns my-body-app.user
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [ring.util.response :refer [redirect]]
            [my-body-app.utils :refer [write-current-user get-users write-users authenticate-user hash-password]]))

(defn create-user [params]
  {:username (:username params)
   :email (:email params)
   :password (hash-password(:password params))
   :phone (:phone params)
   :name (:name params)
   :surname (:surname params)
   })

(defn register-user [params]
  (let [users (get-users)
        username (:username params)
        email (:email params)]
    (if (some #(= (:username %) username) (:users users))
      (redirect (str "/user?username="(:username params)"&email="(:email params)"&phone="(:phone params)"&name="(:name params)"&surname="(:surname params)"&error=username-taken"))
      (if (some #(= (:email %) email) users)
        (redirect (str "/user?username=" (:username params) "&email=" (:email params) "&phone=" (:phone params) "&name=" (:name params) "&surname=" (:surname params) "&error=email-taken")) 
        (do
          

          (write-users (update-in users [:users] conj (create-user params)))
          (write-current-user (authenticate-user (:username params) (:password params)))
          ;;(redirect (str "/user?username=" (:username params) "&email=" (:email params) "&phone=" (:phone params) "&name=" (:name params) "&surname=" (:surname params) )))))))
          (redirect "/"))))))

(defn handle-changes [params]
  (register-user params)
)

(defn user-page []
  (layout
   "User Registration"
   (hiccup/html
    [:h1 "User Registration"]
    [:script
     (str "document.addEventListener('DOMContentLoaded', function() {"
          " var urlParams = new URLSearchParams(window.location.search);"
          "  var username = urlParams.get('username');"
          "  var email = urlParams.get('email');"
          "  var phone = urlParams.get('phone');"
          "  var name = urlParams.get('name');"
          "  var surname = urlParams.get('surname');"
          "  var error = urlParams.get('error');"
          "  var usernameField = document.querySelector('input[name=\"username\"]');"
          "  var emailField = document.querySelector('input[name=\"email\"]');"
          "  var phoneField = document.querySelector('input[name=\"phone\"]');"
          "  var nameField = document.querySelector('input[name=\"name\"]');"
          "  var surnameField = document.querySelector('input[name=\"surname\"]');"
          "  var errorField = document.querySelector('p[name=\"error\"]');"
          "  if (username) { usernameField.value = username; }"
          "  if (email) { emailField.value = email; }"
          "  if (phone) { phoneField.value = phone; }"
          "  if (name) { nameField.value = name; }"
          "  if (surname) { surnameField.value = surname; }"
          "  if (error) { errorField.textContent = error; }"
          "});")]
    [:div.form
     [:form {:action "/user" :method "POST"}
      [:label {:for "username" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Username:"]
      [:input {:type "text" :name "username"  :required true}]
      [:br]
      [:label {:for "email" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Email:"]
      [:input {:type "email" :name "email" :required true}]
      [:br]
      [:label {:for "password" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Password:"]
      [:input {:type "password" :name "password"  :required true}]
      [:br]
      [:label {:for "phone" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Phone:"]
      [:input {:type "text" :name "phone"  :required true}]
      [:br]
      [:label {:for "name" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Name:"]
      [:input {:type "text" :name "name":required true}]
      [:br]
      [:label {:for "surname" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Surname:"]
      [:input {:type "text" :name "surname" :required true}]
      [:br]
      [:p {:style "margin-left:120px; color: red" :name "error"}  ""]
      [:input {:type "submit" :value "Register" :style "margin-left:120px; width:120px;"}]]])))