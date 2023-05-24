(ns my-body-app.plan
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [my-body-app.utils :refer [read-current-user get-all-meals]]))


(defn calculate-total-calories [plan]
  (println plan)
  (let [breakfast-calories (Integer/parseInt (:calories (:breakfast plan)))
          snack-1-calories (Integer/parseInt (:calories (:snack-1 plan)))
          lunch-calories (Integer/parseInt (:calories (:lunch plan)))
          snack-2-calories (Integer/parseInt (:calories (:snack-2 plan)))
          dinner-calories (Integer/parseInt (:calories (:dinner plan)))]
    (apply + (remove nil? [breakfast-calories snack-1-calories lunch-calories snack-2-calories dinner-calories]))
  ))


(defn get-plan []
  (let [meals (get-all-meals)
        plan {:breakfast (rand-nth (:meals (:breakfast meals)))
              :snack-1 (rand-nth (:meals (:snack meals)))
              :lunch (rand-nth (:meals (:lunch meals)))
              :snack-2 (rand-nth (:meals (:snack meals)))
              :dinner (rand-nth (:meals (:dinner meals)))}
        total-calories (calculate-total-calories plan)]
    {:plan plan
     :total-calories total-calories}))


(defn plan-page []
  (let [plan-full (get-plan)
        user (read-current-user)
        plan (:plan plan-full)]
    (layout
     "plan page"
     (hiccup/html
      [:h1 "Hello " (:name (read-current-user)) ", this is your randomly generated meal plan!"]
      [:h2 [:b "Total recomended calories:"] (:calorie-intake (:info user))]
      [:h2 [:b "Plan calories:"] (:total-calories plan-full)]
      [:h3 [:b {:style "color:#2B547E"} "Breakfast : "] (:title (:breakfast plan)) " ( " (:calories (:breakfast plan)) " )"]
      [:p (:description (:breakfast plan))]
      [:h3 [:b {:style "color:#2B547E"} "Snack 1 : "] (:title (:snack-1 plan)) " ( " (:calories (:snack-1 plan)) " )"]
      [:p (:description (:snack-1 plan))]
      [:h3 [:b {:style "color:#2B547E"} "Lunch : "] (:title (:lunch plan)) " ( " (:calories (:lunch plan)) " )"]
      [:p (:description (:lunch plan))]
      [:h3 [:b {:style "color:#2B547E"} "Snack 2 : "] (:title (:snack-2 plan)) " ( " (:calories (:snack-2 plan)) " )"]
      [:p (:description (:snack-2 plan))]
      [:h3 [:b {:style "color:#2B547E"} "Dinner : "] (:title (:dinner plan)) " ( " (:calories (:dinner plan)) " )"]
      [:p (:description (:dinner plan))]))))