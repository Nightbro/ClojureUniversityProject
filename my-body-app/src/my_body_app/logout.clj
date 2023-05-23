(ns my-body-app.logout
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [clojure.java.io :as io] 
            [ring.util.response :refer [response redirect]]
            [my-body-app.utils :refer [write-current-user]]
            [cheshire.core :as json]))


;; logout and redirect to home page
(defn logout-page []
  (write-current-user {})
  (layout
   "Logged out"
   (hiccup/html
    [:h1 "Please wait!"]
    [:p "You are being logged out."] 
    [:script "setTimeout(function() { window.location.href = '/';console.log('Timeout complete'); }, 5000);"])))



   