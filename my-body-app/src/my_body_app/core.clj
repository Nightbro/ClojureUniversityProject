(ns my-body-app.core
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :as response]
            [my-body-app.home :as home]
            [my-body-app.login :as login]
            [my-body-app.logout :as logout]
            [my-body-app.bmi :as bmi]
            [my-body-app.user :as user]
            [my-body-app.plan :as plan]))

(defroutes app-routes
  (GET "/" [] (home/home-page))
  (GET "/login" [] (login/login-page))
  (POST "/login" {params :params} (login/handle-login params))
  (GET "/bmi" [] (bmi/bmi-page))
  (POST "/bmi" {params :params} (bmi/handle-bmi-changes params))
   (GET "/user" [] (user/user-page))
  (POST "/user" {params :params} (user/handle-changes params))
  (GET "/plan" [] (plan/plan-page))
  (GET "/logout" [] (logout/logout-page))
  (route/not-found "Not Found")
  (route/not-found {:status 404 :body "Page not found."}))
  

(defroutes favicon-routes
  (GET "/favicon.ico" [] (response/redirect "/static/favicon.ico")))

(def app
  (-> (handler/site app-routes favicon-routes)
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false)))
  )