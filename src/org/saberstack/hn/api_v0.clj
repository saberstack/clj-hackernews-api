(ns org.saberstack.hn.api-v0
  "Official HackerNews API docs: https://github.com/HackerNews/API"
  (:require [babashka.http-client :as http-client]))

(def ^:private base-url "https://hacker-news.firebaseio.com/v0/")
(def ^:private json-suffix ".json")
(def ^:private endpoints
  {:item    "item"
   :user    "user"
   :maxitem "maxitem"
   :updates "updates"})

(defn- uri
  ([suffix] (str base-url suffix json-suffix))
  ([suffix id] (str base-url suffix "/" id json-suffix)))

(defn item!
  "Story, comment, etc"
  [id]
  (http-client/get (uri (:item endpoints) id)))

(defn user!
  "Retrieves user information by username.
   Note: username is case-sensitive."
  [username]
  (http-client/get (uri (:user endpoints) username)))

(defn max-item!
  "The current largest item id"
  []
  (http-client/get (uri (:maxitem endpoints))))

(defn updates!
  "Item and profile changes"
  []
  (http-client/get (uri (:updates endpoints))))

(comment

  (item! 42)

  (user! "raspasov")

  (pmap
    item!
    (range 1 11))

  (max-item!)

  (updates!))
