(ns org.saberstack.hn.api-v0
  "Official HackerNews API docs: https://github.com/HackerNews/API"
  (:require [babashka.http-client :as http-client]))

(def ^:private base-url "https://hacker-news.firebaseio.com/v0/")
(def ^:private json-suffix ".json")

(defn- uri
  ([suffix] (str base-url suffix json-suffix))
  ([suffix id] (str base-url suffix "/" id json-suffix)))

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
  (http-client/get (uri "max-item")))

(defn updates!
  "Item and profile changes"
  []
  (http-client/get (uri "updates")))

(defn new-stories!
  []
  (http-client/get (uri "newstories")))

(defn top-stories!
  []
  (http-client/get (uri "topstories")))

(defn best-stories!
  []
  (http-client/get (uri "beststories")))

(defn ask-stories!
  []
  (http-client/get (uri "askstories")))

(defn show-stories!
  []
  (http-client/get (uri "showstories")))

(defn job-stories!
  []
  (http-client/get (uri "jobstories")))

(comment

  (item! 42)

  (user! "raspasov")

  (pmap
    item!
    (range 1 11))

  (max-item!)

  (updates!))
