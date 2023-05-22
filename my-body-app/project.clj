(defproject my-body-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0"]
                 [ring/ring-core "1.9.2"]
                 [ring/ring-defaults "0.3.4"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [hiccup "1.0.5"]]
  :main my-body-app.core 
  :repl-options {:init-ns my-body-app.core})
