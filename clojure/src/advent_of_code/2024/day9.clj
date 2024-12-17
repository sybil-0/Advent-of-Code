(ns advent-of-code.2024.day9
  (:gen-class)
  (:require [clojure.string :as str]))


(def input (->> (str/trim (slurp "resources/day9.txt"))
                (map #(Character/digit % 10))))

(defn swap [v i1 i2] 
  (assoc v i2 (v i1) i1 (v i2)))

(defn index-last [pred xs]
  (loop [i (dec (count xs))]
    (if (pred (xs i)) i (recur (dec i)))))

(defn expand [i x]
  (if (even? i) (repeat x (/ i 2)) (repeat x ".")))

(defn blocks [xs]
  (vec (flatten (map-indexed expand xs))))

(defn sort-blocks [xs]
  (let [i (.indexOf xs ".")
        j (index-last number? xs)
        xs* (swap xs i j)]
    (if (every? string? (drop-while number? xs*)) xs*
        (recur xs*))))

(defn checksum [xs]
  (map-indexed (fn [i x] (* i (if (= x ".") 0 x))) xs))

(defn part-1 []
  (->> (sort-blocks (blocks input))
       (take-while number?)
       (map-indexed #(* %1 %2))
       (reduce +)))
