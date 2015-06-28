(ns falkor.server
  (:require [falkor.parser :as falkor]
            [falkor.util :refer [enforce-params json-handler wrap-as-result]]
            [falkor.middleware :as logging]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [compojure.route :as route]))

;; Handlers
;; **************************************************

(defn query-handler
  "The query handler is used to render any xpath query"
  [params]
  (let [{:strs [url query] :as q} params]
    (enforce-params {:url url :query query}
      (try
        (let [result (falkor/run-query url query)]
          (json-handler 200
           (wrap-as-result result url query)))
      (catch Exception e
        (json-handler 500
          {:body "Request failed"}))))))

(defn root-handler
  []
  (json-handler 200
    {:body "OK"}))

;; **************************************************

;; ROUTES

;; 1. Get a page basic structure and information
;; 2. Run a CSS selector query e.g. get all images) => /api/page?query=img

(defroutes app-routes

  (GET "/" [] (root-handler))

  (GET "/api/query" {params :query-params}
    (query-handler params))

  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      handler/api
      logging/wrap-logging
      (wrap-defaults api-defaults)))

(def handler app)
