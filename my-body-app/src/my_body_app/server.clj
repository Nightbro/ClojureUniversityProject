(ns my-body-app.server
  (:require [my-body-app.core :as app]
            [ring.adapter.jetty :refer [run-jetty]]))

(defn -main []
  (run-jetty app/app {:port 3000}))