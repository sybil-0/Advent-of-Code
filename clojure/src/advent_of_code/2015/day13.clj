(ns advent-of-code.2015.day13
  (:require [clojure.string :as str]
            [advent-of-code.utils :as u]
            [clojure.math.combinatorics :as combo]))

(defn parse [[a b c]]
  [[a c] (Integer/parseInt b)])

(def input (->> (slurp "resources/2015/day13.txt")
                (str/split-lines)
                (map #(str/split % #"\s+"))
                (map parse)
                (into {})))

(def people (vec (set (map first (keys input)))))

(defn seat-score [[a b c]]
  (+ (input [b a]) (input [b c])))

(defn table-score [seats]
  (let [x (first seats)
        l (last seats)
        xs (concat [l] seats [x])]
    (->> (partition 3 1 xs)
         (map seat-score)
         (reduce +))))

(defn part-1 []
  (->> (combo/permutations people)
       (map table-score)
       (apply max)))
