(ns org.saberstack.hn.api-v0
  "Official HackerNews API docs: https://github.com/HackerNews/API"
  (:require [babashka.http-client :as http-client]))

(def base-uri "https://hacker-news.firebaseio.com/v0/")

(defn uri
  ([suffix] (str base-uri suffix ".json"))
  ([suffix id] (str base-uri suffix "/" id ".json")))

(defn item!
  "Story, comment, etc"
  [id]
  (http-client/get (uri "item" id)))

(defn user!
  "Retrieves user information by username.
   Note: username is case-sensitive."

  [username]
  (http-client/get (uri "user" username)))

(defn max-item!
  "The current largest item id"
  []
  (http-client/get (uri "maxitem")))

(defn updates!
  "Item and profile changes"
  []
  (http-client/get (uri "updates")))

(comment

  (item! 42)

  (pmap
    item!
    (range 1 11))

  (max-item!)

  (updates!))
