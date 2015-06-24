(ns falkor.parser
  (:import [org.jsoup.Jsoup]
           [org.jsoup.nodes Document Element]))

;; ****************************************************************
;; Low level API for parsing web pages into Clojure data structures
;; ****************************************************************

(defn get-document
  "Retrieve a web page and cast it to a Document object.
   TODO: Replace the HTTP element with an async HTTP client eventually"
  [url]
  (.get (org.jsoup.Jsoup/connect url)))

(defn extract-attributes-from-element
  "Given an HTML element := extract all the attributes into a map"
  [element]
  (let [attrs (.attributes element)]
    (reduce
      (fn [acc attr]
        (let [k (.getKey attr)
              v (.getValue attr)]
          (assoc acc (keyword k) v)))
        {} attrs)))

;; ***********************************************

(defrecord DOMElement [tag text attributes])

(defn element->map
  "Reduces a complex HTML element into a simple Clojure map.
   This abstracts away the need for complex rules around turning different element types
   into sensible representations"
  ([element render-children]
  (let [base-element { :tag (.tagName element)
                       :text (.ownText element)}
        attributes (extract-attributes-from-element element)]
  (if render-children
    (merge (assoc base-element :children
      (map #(element->map % true)
        (.children element))))
    (merge base-element {:attributes attributes}))))
  ([element]
    (element->map element false)))

;; ***********************************************

(def as-clojure (partial mapv element->map))

(defn ?
  "Select an element in a document by xpath selector"
  [^org.jsoup.nodes.Document doc xpath]
  (.select doc (name xpath)))

(defn ?>
  "Query a URL for a given xpath selector"
  [url xpath]
  (? (get-document url) xpath))

(defn filter-by
  "Filter a sequence of results where k matches v
   For example we might want to filter a list of tags by class name like this
   (filter-by results [:attributes :class] \"foo\")
  "
  [xs k v]
  (filter
    (fn [m]
      (= (get-in m k) v)) xs))

(defn ??>
  "Given a document : run an xpath query and return the result
   transformed into an internal map"
  [doc xpath]
  (->> (? doc xpath)
       (map element->map)))

;; ***********************************************

(defn run-query [url xpath]
  (??> (get-document url) xpath))

;; Utility functions (WIP)
;; ***********************************************

(def first-selector (comp element->map first ?>))

(defn head [url]        (first-selector url :head))
(defn body [url]        (first-selector url :body))

(defn stylesheets [url] (?> url "link[rel=stylesheet]"))
