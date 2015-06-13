(ns extract.core
  (:import [org.jsoup.Jsoup]
           [org.jsoup.nodes Document Element]))

;; ****************************************************************
;; Low level API for parsing web pages into Clojure data structures
;; ****************************************************************

(defn get-document [url]
  (.get (org.jsoup.Jsoup/connect url)))

;; (def d (get-document "https://news.ycombinator.com/"))
;; (def rt (get-document "http://www.rottentomatoes.com"))

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

(defn element->map
  "Reduces a complex HTML element into a simple Clojure map"
  [element render-children]
  (let [base-element { :tag (.tagName element)
                       :text (.ownText element)}
        attributes (extract-attributes-from-element element)]
  (if render-children
    (merge (assoc base-element :children
      (map #(element->map % true)
        (.children element))))
    (merge base-element {:attributes attributes}))))

(defn ? [doc xpath]
  (.select doc (name xpath)))

(defn ?? [doc xpath]
  (map #(element->map % false) (? doc xpath)))

(defn run-query [url xpath]
  (?? (get-document url) xpath))
