(ns falkor.main
  (:require
    [falkor.server :as fk]
    [environ.core :refer [env]]
    [ring.adapter.jetty :as jetty]))

(defn -main
  "Run the server"
  [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty fk/app {:port port :join? false})))
