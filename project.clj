(defproject falkor "0.1.0-SNAPSHOT"
  :description "A web service that turns HTML pages into queryable JSON"
  :url "http://owainlewis.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.9.5"]]
  :profiles {:uberjar {:aot :all}}
  :min-lein-version "2.0.0"
  :uberjar-name "falkor-api.jar"
  :ring {:handler falkor.server/handler}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.4"]
                 [environ "0.5.0"]
                 [http-kit "2.1.18"]
                 [ring/ring-core "1.3.2"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [ring/ring-json "0.3.1"]
                 [ring/ring-defaults "0.1.4"]
                 [org.jsoup/jsoup "1.8.2"]
                 [cheshire "5.5.0"]])
