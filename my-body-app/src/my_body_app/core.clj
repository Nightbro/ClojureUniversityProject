(ns my-body-app.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as response]
            [my-body-app.home :as home]
            [my-body-app.login :as login]))

(defroutes app-routes
  (GET "/" [] (home/home-page))
  (GET "/login" [] (login/login-page))
  (POST "/login" {params :params} (login/handle-login params))
  (route/not-found "Not Found")
  (route/not-found {:status 404 :body "Page not found."}))
  

(defroutes favicon-routes
  (GET "/favicon.ico" [] (response/redirect "/static/favicon.ico")))

(def app
  (-> (handler/site app-routes favicon-routes)
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false)))
  )