(ns advent-of-code.2024.day11
  (:require [clojure.string :as str]))

(def puzzle-input [4610211 4 0 59 3907 201586 929 33750])

(defn split-int [k i]
  (let [s (str k)
        l (quot (count s) 2)]
    (map (fn [x] [(Integer/parseInt (str/join x)) i]) (split-at l s))))

(defn merge-keys [pairs]
  (reduce (fn [acc [k v]] (update acc k (fnil + 0) v)) {} pairs))

(defn change [stones]
  (mapcat  (fn [[k i]]
             (cond
               (zero? k) [[1 i]]
               (even? (count (str k))) (split-int k i)
               :else [[(* 2024 k) i]])) stones))

(defn solve [limit]
  (loop [stones (frequencies puzzle-input) i 0]
    (if (= i limit) (reduce + (vals stones))
      (recur (merge-keys (change stones)) (inc i)))))

