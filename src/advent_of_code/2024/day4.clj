(ns aoc-2024.day4
  (:require [clojure.string :as str]))

(def grid (->> (slurp "resources/day4.txt")
                (str/split-lines)))

(def directions
  [[[0 0] [0 1] [0 2] [0 3]]
   [[0 0] [0 -1] [0 -2] [0 -3]]
   [[0 0] [-1 0] [-2 0] [-3 0]]
   [[0 0] [1 0] [2 0] [3 0]]
   [[0 0] [1 1] [2 2] [3 3]]
   [[0 0] [-1 -1] [-2 -2] [-3 -3]]
   [[0 0] [-1 1] [-2 2] [-3 3]]
   [[0 0] [1 -1] [2 -2] [3 -3]]])

(def directions-p2
  [[[-1 -1] [0 0] [1 1]]
   [[1 1] [0 0] [-1 -1]]
   [[1 -1] [0 0] [-1 1]]
   [[-1 1] [0 0] [1 -1]]])

(defn idx-get [[x y]]
  (get (get grid x []) y "."))

(defn adjacent [[x y] coll]
  (map (fn [[i j]] [(+ x i) (+ j y)]) coll))

(defn xmas? [w coll]
  (= w (str/join (map idx-get coll))))

(defn check [point]
  (let [words (map (partial adjacent point) directions)]
    (count (filter (partial xmas? "XMAS") words))))

(defn check-p2 [point]
  (let [words (map (partial adjacent point) directions-p2)]
    (= 2 (count (filter (partial xmas? "MAS") words)))))

(defn solve []
  (reduce + (for [x (range (count grid))
                  y (range (count (grid 0)))]
              ;; (check [x y])
              (if (check-p2 [x y]) 1 0))))
