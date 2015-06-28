(ns falkor.util
  (:require             [cheshire.core :as json]))

(defn allow-cross-origin
  "Middleware function to allow crosss origin"
  [handler]
  (fn [request]
   (let [response (handler request)]
    (assoc-in response [:headers "Access-Control-Allow-Origin"]
         "*"))))

(defmacro enforce-params
  "Checks is required parameters are present. If they are not
   return a 400 bad request"
  [m & body]
  `(let [missing# (for [[k# v#] ~m :when (nil? v#)] k#)]
    (if (empty? missing#)
      ~@body
      (let [msg# (str "The following params are missing: "
                        (->> (identity missing#)
                             (map name)
                             (interpose " ")
                             (apply str)))]
        (json-handler 400
          {:status 400
           :error msg#})))))

(defn json-handler [status body]
  {:status status
   :headers {
     "Content-Type" "application/json"
     "Access-Control-Allow-Methods" "GET"
     "Access-Control-Allow-Origin" "*" }
   :body (json/generate-string body {:pretty true})})

(defn wrap-as-result [m url query]
  {:url url
   :query query
   :results m})
