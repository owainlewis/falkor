(ns falkor.middleware
  (:require [clojure.java.io :as io]))

(defn format-request-method [method]
  (.toUpperCase (name method)))

(defn format-log-message
  [req]
  (format "Request %s\n"
    (format-request-method (:request-method req))))

(defn wrap-logging [handler & opts]
  (fn [req]
    (with-open [wrtr (io/writer "logs.txt" :append true)]
      (println req)
      (.write wrtr (format-log-message req))
      (handler req))))
