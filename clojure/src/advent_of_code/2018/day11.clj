(ns advent-of-code.2018.day11
  (:require [advent-of-code.utils :as u]))

(def serial 5535)

(defn hundreth [x]
  (int (mod (quot x (Math/pow 10 2)) 10)))

(defn power-level [[x y]]
  (let [rack-id (+ x 10)
        pl (* rack-id (+ serial (* rack-id y)))]
    (- (hundreth pl) 5)))

(defn square-level [p]
  (let [square (conj (u/adjacent p) p)]
    (reduce + (map power-level square))))

(defn part-1 []
  (->> (for [x (range 1 299) y (range 1 299)] [x y])
       (apply max-key square-level)))
