(ns aoc-2024.day8
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def input (str/split-lines (slurp "resources/day8.txt")))
(def rows (count input))
(def cols (count (nth input 0)))

(defn in-bounds? [[x y]]
  (and (>= x 0) (< x rows) (>= y 0) (< y cols)))

(def antennas
  (->> (for [x (range rows) y (range cols) ] [x y])
       (group-by (fn [[x y]] (nth (nth input x) y)))
       (#(dissoc % \.))))

(defn gen-nodes [[[x1 y1] [x2 y2]]]
  [[(+ x1 (- x1 x2)) (+ y1 (- y1 y2))]
   [(+ x2 (- x2 x1)) (+ y2 (- y2 y1))]])

(defn gen-nodes-2 [[[x1 y1] [x2 y2]]]
  (apply concat (for [m (range 0 100)]
     [[(+ x1 (* m (- x1 x2))) (+ y1 (* m (- y1 y2)))]
      [(+ x2 (* m (- x2 x1))) (+ y2 (* m (- y2 y1)))]])))

(defn antinodes [coords]
  (let [pairs (combo/combinations coords 2)]
    ;; (mapcat gen-nodes pairs)
    (mapcat gen-nodes-2 pairs)))

(defn solve []
  (->> (mapcat antinodes (vals antennas))
       (filter in-bounds?)
       (set)
       (count)))
