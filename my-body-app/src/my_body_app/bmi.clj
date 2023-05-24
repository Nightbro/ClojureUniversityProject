(ns my-body-app.bmi
  (:require [my-body-app.layout :refer [layout]]
            [hiccup.core :as hiccup]
            [my-body-app.utils :refer [read-current-user write-current-user update-info]]
            [ring.util.response :refer [redirect]]))

(defn ratio-to-decimal [ratio]
  (.doubleValue ratio))

(defn round [s n]
  (.setScale (bigdec n) s java.math.RoundingMode/HALF_EVEN))

(defn calculate-bmi [params]
  (let [weight (-> params :weight (clojure.edn/read-string) )
        height (-> params :height (clojure.edn/read-string) (/ 100))]
    (if (some nil? [weight height])
      nil
        (ratio-to-decimal (/ weight (* height height)))) ))

(defn calculate-bmr [params]
  (let [weight (-> params :weight (clojure.edn/read-string))
        height  (-> params :height (clojure.edn/read-string))
        age (-> params :age (clojure.edn/read-string))
        gender (-> params :gender (clojure.edn/read-string))]
    (if (some nil? [weight height age gender])
      nil
      (if (= gender "male")
        (+ (* 10 weight) (* 6.25 height) (* -5 age) 5)
        (+ (* 10 weight) (* 6.25 height) (* -5 age) -161)))))

(defn calculate-calorie-intake [params]
  (let [bmr (calculate-bmr params)
        activity-level (:activity params)]
    (if (some nil? [bmr activity-level])
      nil
      (case activity-level
        "sedentary" (* bmr 1.2)
        "lightly-active" (* bmr 1.375)
        "moderately-active" (* bmr 1.55)
        "very-active" (* bmr 1.725)
        "extra-active" (* bmr 1.9)))))


(defn handle-bmi-changes [params]
  (let [user (read-current-user)
        info (:info user)
        updated-info (assoc info
                            :height (:height params)
                            :weight (:weight params)
                            :age (:age params)
                            :gender (:gender params)
                            :activity (:activity params)
                            :calorie-intake (round 0 (calculate-calorie-intake params))
                            :bmi (round 4 (calculate-bmi params)))]
    (update-info (:username user) updated-info)
    (write-current-user (assoc user :info updated-info))
    (redirect "/bmi")))



(defn bmi-page []
  (let [user (read-current-user)
        info (:info user)]
    (layout
     "BMI page"
     (hiccup/html
      [:h1 "Hello " (:name user) ", and welcome to the BMI calculator!"]
      [:script
       "function validateForm() {"
       "  var height = document.forms['bmi-form']['height'].value;"
       "  var weight = document.forms['bmi-form']['weight'].value;"
       "  var age = document.forms['bmi-form']['age'].value;"
       "  var gender = document.forms['bmi-form']['gender'].value;"
       "  var activity = document.forms['bmi-form']['activity'].value;"
       "  if (height === '' || weight === '' || age === '' || gender === '' || activity === '') {"
       "    alert('Please fill in all the required information.');"
       "    return false;"
       "  }"
       "}"]
      [:div.content
       [:div.form
        [:form {:action "/bmi" :method "POST"  :onsubmit "return validateForm();"}
         [:label {:for "height" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Height (cm):"]
         [:input {:type "text" :name "height" :value (:height info) :required true}]
         [:br]
         [:label {:for "weight" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Weight (kg):"]
         [:input {:type "text" :name "weight" :value (:weight info) :required true}]
         [:br]
         [:label {:for "age" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Age:"]
         [:input {:type "text" :name "age" :value (:age info) :required true}]
         [:br]

         [:label {:for "gender" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Gender:"]
         [:select {:name "gender"}
          [:option {:value "male" :selected (= (:gender info) "male")} "Male"]
          [:option {:value "female" :selected (= (:gender info) "female")} "Female"]]
         [:br]
         [:label {:for "activity" :style "display:inline-block; width:120px; margin-bottom:20px;"} "Activity Level:"]
         [:select {:name "activity"}
          [:option {:value "sedentary" :selected (= (:activity info) "sedentary")} "Sedentary"]
          [:option {:value "lightly-active" :selected (= (:activity info) "lightly-active")} "Lightly Active"]
          [:option {:value "moderately-active" :selected (= (:activity info) "moderately-active")} "Moderately Active"]
          [:option {:value "very-active" :selected (= (:activity info) "very-active")} "Very Active"]
          [:option {:value "extra-active" :selected (= (:activity info) "extra-active")} "Extra Active"]]
         [:br]
         [:input {:type "submit" :value "Save Changes" :style "margin-left:120px; width:120px;"}]]]
       [:div.calories {:style "margin-left:20px;"}
        (when (not (empty? (str(:bmi info))))
          [:p [:b "BMI: "]   (:bmi info)])
        (when (not (empty? (str(:calorie-intake info))))
          [:p [:b "Base Calorie Intake to maintain weight: "] (:calorie-intake info) " Calories/day"])]]))))