(ns org.saberstack.hn.api-v0
  (:require [babashka.http-client :as http-client]))

(def base-uri "https://hacker-news.firebaseio.com/v0/")

(defn uri [suffix id]
  (str base-uri suffix (str id) ".json"))

(defn item! [id]
  (http-client/get (uri "item/" id)))

(defn max-item! []
  (http-client/get (uri "" "maxitem")))

(comment

  (item! 42)

  (pmap
    item!
    (range 1 11))

  (max-item!))
