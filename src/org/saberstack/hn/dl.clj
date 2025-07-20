(ns org.saberstack.hn.dl
  (:require [org.saberstack.hn.api-v0 :as api]
            [taoensso.nippy :as nippy]))

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

(defn pmap-items! [item-ids]
  (let [resps (pmap api/item! item-ids)]
    {:item-ids item-ids
     :items    (into [] (map :body) resps)
     :status-200? (every? #(= 200 %) (map :status resps))
     :resps resps}))

(defn items-to-disk! [items start-id end-id]
  (nippy/freeze-to-file
    (str "items-" start-id "-" end-id)
    items))

(defn download-items! [start-id]
  (reset! halt? false)
  (transduce
    (comp
      (partition-all 250)
      (map pmap-items!)
      (map (fn [{:keys [items item-ids status-200? resps]}]
             (if status-200?
               (do
                 (items-to-disk! items (first item-ids) (peek item-ids))
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

(comment

  (reset! halt? true)
  (reset! sleep-ms 500)
  (download-items! 1)
  )
