(ns falkor.server
  (:require [falkor.parser :as falkor]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [cheshire.core :as json]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [compojure.route :as route]))

(defn json-handler [status body]
  {:status status
   :headers { "Content-Type" "application/json" }
   :body (json/generate-string body {:pretty true})})

;; Handlers
;; **************************************************

(defn query-handler
  "The query handler is used to render any xpath query"
  [url xpath]
  (json-handler 200
    (falkor/run-query url xpath)))

;; **************************************************

;; ROUTES

;; 1. Get a page basic structure and information
;; 2. Run a CSS selector query e.g. get all images) => /api/page?query=img

(defroutes app-routes
  (GET "/api/query" {params :query-params}
    (query-handler
      (get params "url") (get params "q")))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      handler/api
      (wrap-defaults api-defaults)))

(def handler app)
