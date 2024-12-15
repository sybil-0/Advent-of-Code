(ns advent-of-code.2024.day15
  (:require [clojure.string :as str]
            [advent-of-code.utils :as utils]))

(defn expand-cell [c]
  (case c
    \# "##"
    \O "[]"
    \. ".."
    \@ "@."))

(def warehouse (->> (slurp "resources/2024/day15.txt")
                    (str/split-lines)
                    (utils/index2d)))

(def warehouse-wide (->> (slurp "resources/2024/day15.txt")
                         (str/split-lines)
                         (mapv #(mapv (partial expand-cell) %))
                         (mapv #(str/join %))
                         (utils/index2d)))

(def move-input (str/trim (slurp "resources/2024/day15_steps.txt")))

(def start [4 4])
(def start-2 [4 8])

(defn swap-keys [m seen dir]
  (let [xs (map #(step % dir) seen)]
    (-> (reduce (fn [acc p] (assoc acc p (m (first seen)))) m xs)
        (assoc (first seen) "."))))

(defn step [[x y] direction]
  (case direction
    "<" [x (dec y)]
    ">" [x (inc y)]
    "v" [(inc x) y]
    "^" [(dec x) y]
    [x y]))

(defn push-boxes [m p dir]
  (loop [p* (step p dir) seen []]
    (condp = (m p*)
      "." (swap-keys m seen dir)
      "#" m
      "O" (recur (step p* dir) (conj seen p*)))))

(defn simulate []
  (loop [p start [x & xs] move-input m warehouse]
    (let [p* (step p (str x))
          m* (push-boxes m p (str x))]
      (cond
        (nil? x) m
        (= (m p*) "#") (recur p xs m)
        (= (m p*) ".") (recur p* xs m)
        :else (if (not= m m*)
                (recur p* xs m*)
                (recur p xs m))))))

(defn part-1 []
  (let [result (simulate)]
    (->> (filter #(= "O" (result %)) (keys result))
         (reduce (fn [acc [x y]] (+ acc (+ (* 100 x) y))) 0))))

