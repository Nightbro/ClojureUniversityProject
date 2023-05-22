(ns my-body-app.layout
  (:require [hiccup.core :as hiccup]))

(defn layout [title content]
  (hiccup/html
   [:html
    [:head
     [:style
      "body { background-color: lightblue; padding: 0px; margin: 0px}"
      ".header { background-color: #333; color: white; padding: 10px; text-transform: uppercase; font-size:24px }"
      ".content { display: flex; min-height: calc(100vh - 70px); }"
      ".menu { flex: 0 0 200px; background-color: #f1f1f1; padding: 20px;}"
      ".main { flex: 1; padding: 10px; }"
      ".footer { background-color: #333; color: white; padding: 10px; text-align: center;  position: sticky; bottom: 0;}"
      "a { text-decoration: none; color: #333; }"
      "a:hover { color: #666; }"
       "a.menu-link { display: block; padding-top:0px; padding-bottom:0px; font-size:16px}"]
     [:title title]]
     [:link {:rel "icon" :type "image/x-icon" :href "static/favicon.ico"}]
    [:body
     [:div.header "My body tracker"]
     [:div.content
      [:div.menu
       [:a.menu-link {:href "/"} "Home"]
       [:br]
       [:a.menu-link {:href "/login"} "Login"]
       [:br]
       [:a.menu-link {:href "/about"} "About"]]
      [:div.main content]]]
    [:div.footer "Ivan Jordanovic 2022/3735 @ Faculty of Organisational Sciences - University of Belgrade"]]))