(ns aoc-2024.day2
  (:require [clojure.string :as str]))

(defn to-int [s]
  (->> (str/split s #"\s+")
       (map (fn [x] (Integer/parseInt x)))))

(def input (->> (slurp "resources/day2.txt")
                (str/split-lines)
                (map to-int)))

(defn remove-idx [i items]
  (keep-indexed #(when-not (= i %1) %2) items))

(defn prox [xs]
  (map (fn [[a b]] (- a b)) (partition 2 1 xs)))

(defn safe? [xs]
  (let [diffs (prox xs)
        val-r #{-1 -2 -3 1 2 3}]
    (and (or (every? pos-int? diffs)
             (every? neg-int? diffs))
         (every? #(contains? val-r %) diffs))))

(defn maybe-safe? [xs]
  (let [variants (map #(remove-idx % xs) (range (count xs)))]
    (some safe? variants)))

(defn part-1 [] (count (filter safe? input)))
(defn part-2 [] (count (filter maybe-safe? input)))
