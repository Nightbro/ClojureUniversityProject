(ns my-body-app.utils
  (:require [clojure.java.io :as io]
            [cheshire.core :as json]))
(import 'java.security.MessageDigest)

(defn sha256 [string]
  (let [digest (.digest (MessageDigest/getInstance "SHA-256") (.getBytes string "UTF-8"))]
    (apply str (map (partial format "%02x") digest))))  

(defn hash-password [password]
  (sha256 password))

(defn get-users []
  (-> "users.json"
      io/resource
      slurp
      (json/parse-string true)))

(defn read-current-user []
   (-> "current.json"
       io/resource
       slurp
       (json/parse-string true)))


(defn write-users [users] 
  (let [json-str (json/generate-string users)]
    (spit "resources/users.json" json-str))
  )

(defn write-current-user [user]
  (let [user-without-password (dissoc user :password)
        json-str (json/generate-string user-without-password)]
    (spit "resources/current.json" json-str)))

(defn authenticate-user [username password]
  (let [users (get-users)
        user (first (filter #(= username (:username %)) (:users users)))]
    (and user
         (= (hash-password password) (:password user))
         user)))

(defn update-info [username new-info]
  (let [data (get-users)
        users (:users data)
        updated-users (map #(if (= (:username %) username)
                              (assoc % :info new-info)
                              %)
                           users)
        updated-data (assoc data :users updated-users)]
    (spit "resources/users.json" (json/generate-string updated-data))))


(defn get-breakfast []
  (-> "breakfast.json"
      io/resource
      slurp
      (json/parse-string true)))

(defn get-snacks []
  (-> "snacks.json"
      io/resource
      slurp
      (json/parse-string true)))

(defn get-lunch []
  (-> "lunch.json"
      io/resource
      slurp
      (json/parse-string true)))

(defn get-dinner []
  (-> "dinner.json"
      io/resource
      slurp
      (json/parse-string true)))


(defn get-all-meals []
  (let [breakfast (get-breakfast)
        snack (get-snacks)
        lunch (get-lunch)
        dinner (get-dinner)]
    {:breakfast breakfast
     :snack snack
     :lunch lunch
     :dinner dinner}))