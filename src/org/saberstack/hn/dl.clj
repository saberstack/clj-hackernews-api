(ns org.saberstack.hn.dl
  (:require [charred.api :as charred]
            [org.saberstack.hn.api-v0 :as api]
            [taoensso.nippy :as nippy]
            [taoensso.timbre :as timbre]))

(comment

  (nippy/freeze-to-file
    "items-test-2"
    (into []
      (pmap
        api/item!
        (range 1))))

  (nippy/thaw-from-file "items-test-2")
  )


(defonce halt? (atom false))
(defonce stopped-at (atom nil))
(defonce sleep-ms (atom 500))
(defonce last-dl (atom nil))

(defn pmap-items! [item-ids]
  (let [resps (pmap api/item! item-ids)]
    {:item-ids    item-ids
     :items       (into [] (map :body) resps)
     :status-200? (every? #(= 200 %) (map :status resps))
     :resps       resps}))

(defn items-to-disk! [items start-id end-id]
  (nippy/freeze-to-file
    (str "./hndl/" "items-" start-id "-" end-id)
    items))

(defn download-items! [start-id]
  (reset! halt? false)
  (transduce
    (comp
      (partition-all 1000)
      (map pmap-items!)
      (map (fn [{:keys [items item-ids status-200? resps]}]
             (if status-200?
               (do
                 (items-to-disk! items (first item-ids) (peek item-ids))
                 (reset! last-dl [(first item-ids) (peek item-ids)])
                 true)
               (do
                 (reset! stopped-at resps)
                 false))))
      (halt-when (fn [download-ok?]
                   (or
                     (not download-ok?)
                     (true? @halt?))))
      (map (fn [_] (Thread/sleep @sleep-ms))))
    conj
    (iterate inc start-id)))

(defn thaw-items [file-name]
  (into []
    (comp
      (map (fn [s] (charred/read-json s :key-fn keyword)))
      (map (juxt :type :score keys)))
    (nippy/thaw-from-file (str "./hndl/" file-name))))



(comment

  (reset! halt? true)
  (reset! sleep-ms 20)
  (future
    (download-items! 25286001))
  )
