(ns advent-of-code.2017.day11
  (:require [clojure.string :as str]))

(def all-distances (atom []))

(def input (-> (slurp "resources/2017/day11.txt")
               (str/trim)
               (str/split #",")))

(defn distance [[x y z]]
  (/ (+ (abs x) (abs y) (abs z)) 2))

(defn move [[x y z] dir]
  (swap! all-distances #(conj % (distance [x y z])))
  (case dir
    "n" [x (dec y) (inc z)]
    "ne" [(inc x) (dec y) z]
    "se" [(inc x) y (dec z)]
    "s" [x (inc y) (dec z)]
    "sw" [(dec x) (inc y) z]
    "nw" [(dec x) y (inc z)]))

(distance (reduce move [0 0 0] input))
(apply max @all-distances)
