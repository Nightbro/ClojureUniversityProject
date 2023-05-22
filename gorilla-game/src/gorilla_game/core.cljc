(ns gorilla-game.core
  (:require [gorilla-game.utils :as utils]
            [gorilla-game.move :as move]
            [play-cljc.gl.core :as c]
            [play-cljc.gl.entities-2d :as e]
            [play-cljc.transforms :as t]
            #?(:clj  [play-cljc.macros-java :refer [gl math]]
               :cljs [play-cljc.macros-js :refer-macros [gl math]])))

(defonce *state (atom {:g-width:1600
                       :g-height:900
                       :mouse-x 0
                       :mouse-y 0
                       :mouse-button nil
                       :pressed-keys #{}
                       :x-velocity 0
                       :y-velocity 0
                       :player-x 0
                       :player-y 0
                       :can-jump? false
                       :direction :right
                       :player-images {}
                       :player-image-key :walk1
                       :background nil}))

(defn init [game]
  ;; allow transparency in images
  (gl game enable (gl game BLEND))
  (gl game blendFunc (gl game SRC_ALPHA) (gl game ONE_MINUS_SRC_ALPHA))

  (utils/get-image "day.png"
                   (fn [{:keys [data width height]}]
                     (let [background-entity (e/->image-entity game data width height)
                           background-entity (c/compile game background-entity)]
                       (swap! *state assoc :background background-entity))))

  ;; load images and put them in the state atom
  (doseq [[k path] {:walk1 "player_walk1.png"
                    :walk2 "player_walk2.png"
                    :walk3 "player_walk3.png"}]
    (utils/get-image path
                     (fn [{:keys [data width height]}]
                       (let [;; create an image entity (a map with info necessary to display it)
                             entity (e/->image-entity game data width height)
              ;; compile the shaders so it is ready to render
                             entity (c/compile game entity)
              ;; assoc the width and height to we can reference it later
                             entity (assoc entity :width width :height height)]
          ;; add it to the state
                         (swap! *state update :player-images assoc k entity))))))

(def screen-entity
  {:viewport {:x 0 :y 0 :width 0 :height 0}
   :clear {:color [(/ 173 255) (/ 216 255) (/ 230 255) 1] :depth 1}
   :background nil})

(defn tick [game]
  (let [{:keys [pressed-keys
                g-width
                g-height 
                player-x
                player-y
                direction
                player-images
                player-image-key
                background]
         :as state} @*state
        [game-width game-height] (utils/get-size game)]
    (when (and (pos? game-width) (pos? game-height))
      ;; render the background image
      (when-let [bg (get background :data)]
        (c/render game
                  (-> (e/->image-entity game bg (:width background) (:height background))
                      (t/project game-width game-height))))
      ;; render the blue background
      ;;(println game)
      (c/render game (update screen-entity :viewport
                             assoc :width game-width :height game-height :background background))
      
      
      ;; get the current player image to display
      (when-let [player (get player-images player-image-key)]
        (let [player-width (/ game-width 10)
              player-height (* player-width (/ (:height player) (:width player)))]
          ;; render the player
          (c/render game
            (-> player
                (t/project game-width game-height)
                (t/translate (cond-> player-x
                                     (= direction :left)
                                     (+ player-width))
                             player-y)
                (t/scale (cond-> player-width
                                 (= direction :left)
                                 (* -1))
                         player-height)))
          ;; change the state to move the player
          (swap! *state
            (fn [state]
              (->> (assoc state
                          :player-width player-width
                          :player-height player-height)
                   (move/move game)
                   (move/prevent-move game)
                   (move/animate game))))))))
  ;; return the game map
  game)

