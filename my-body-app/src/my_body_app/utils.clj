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