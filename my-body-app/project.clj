(defproject my-body-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0"]
                 [ring/ring-core "1.9.6"]
                 [ring/ring "1.9.6"]
                 [ring/ring-mock "0.4.0"]
                 [ring/ring-defaults "0.3.4"]
                 [ring/ring-jetty-adapter "1.9.6"] 
                 [ring/ring-anti-forgery "1.3.0"] 
                 [lein-ring "0.12.5"]
                 [hiccup "1.0.5"]
                 [cheshire "5.11.0"]]
  :main my-body-app.core
  :ring {:handler my-body-app.core/app
         :init    my-body-app.core/init
         :destroy my-body-app.core/destroy}
  :repl-options {:init-ns my-body-app.core}
  :profiles
  {:dev {:dependencies [[ring/ring-mock "0.4.0"]]
         :plugins [[lein-ring "0.12.5"]]}})
