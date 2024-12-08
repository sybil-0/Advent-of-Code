(ns aoc-2024.day1
  (:require [clojure.string :as str]))

(defn to-int [s] (map read-string (str/split s #"\s+" )))

(def input (->> (slurp "resources/day1.txt")
                (str/split-lines)
                (map to-int)))

(defn diff [[a b]] (abs (- a b)))

(def col-1 (sort (map first input)))
(def col-2 (sort (map second input)))

(defn p2-score [x]
  (* x (get (frequencies col-2) x 0)))

(defn part-1 []
  (reduce + (map (comp diff vector) col-1 col-2)))

(defn part-2 []
  (reduce + (map p2-score col-1)))
