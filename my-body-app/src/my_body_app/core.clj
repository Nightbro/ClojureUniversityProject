(ns my-body-app.core
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as response]
            [hiccup.core :refer [html]]
            [my-body-app.home :as home]
            [my-body-app.login :as login]))

(defn home-page []
  (html [:h1 "Welcome to the home page!"]))

(defn login-page []
  (html [:h1 "Please log in."]))

(defroutes app-routes
  (GET "/" [] (home/home-page))
  (GET "/login" [] (login/login-page))
  (route/not-found "Not Found")
  (route/not-found {:status 404 :body "Page not found."}))

(defroutes favicon-routes
  (GET "/favicon.ico" [] (response/redirect "/static/favicon.ico")))

(def app
  (-> (handler/site app-routes favicon-routes)
      (wrap-defaults site-defaults)))