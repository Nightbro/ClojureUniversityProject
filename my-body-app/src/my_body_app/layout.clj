(ns my-body-app.layout
  (:require [hiccup.core :as hiccup]))

(defn layout [title content]
  (hiccup/html
   [:html
    [:head
     [:style
      "body { background-color: lightblue; margin-left: 0px; margin: 0px}"
      ".header { background-color: #333; color: white; padding: 10px; }"
      ".content { display: flex; }"
      ".menu { flex: 0 0 200px; background-color: #f1f1f1; padding: 10px; }"
      ".main { flex: 1; padding: 10px; }"
      ".footer { background-color: #333; color: white; padding: 10px; text-align: center; }"]
     [:title title]]
     [:link {:rel "icon" :type "image/x-icon" :href "/favicon.ico"}]
    [:body
     [:div.header "Header"]
     [:div.content
      [:div.menu
       [:a {:href "/"} "Home"]
       [:a {:href "/login"} "Login"]
       [:a {:href "/about"} "About"]]
      [:div.main content]]]
    [:div.footer "Footer"]]))