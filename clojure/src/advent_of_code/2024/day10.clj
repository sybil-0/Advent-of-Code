(ns advent-of-code.2024.day10
  (:require [clojure.string :as str]
            [advent-of-code.utils :as utils]))


(def trail (->> (slurp "resources/day10.txt")
                (str/split-lines)
                (utils/index2d)))

(def trailheads (filter #(= 0 (trail %)) (keys trail)))

(defn valid? [p1 p2]
  (= (trail p2) (inc (trail p1))))

(defn walk-trail [pos seen]
  (let [nbs (remove seen (filter (partial valid? pos) (utils/neighbors pos)))]
    (if (= (trail pos) 9) [pos]
       (mapcat #(walk-trail % (conj seen pos)) nbs))))

(defn solve []
  (reduce #(+ %1 (count (walk-trail %2 #{}))) 0 trailheads))
