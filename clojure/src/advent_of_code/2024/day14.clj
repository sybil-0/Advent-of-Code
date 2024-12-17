(ns advent-of-code.2024.day14
  (:require [clojure.string :as str]
            [advent-of-code.utils :as u]))

(def robots (->> (slurp "resources/2024/day14.txt")
                (str/split-lines)
                (map #(map read-string (re-seq #"-?\d+" %)))))

(def rows 103)
(def cols 101)
(def m-row (quot rows 2))
(def m-col (quot cols 2))

(defn move [[x y dx dy]]
  [(mod (+ x dx) cols) (mod (+ y dy) rows) dx dy])

(defn in-quadrant? [ xl xh yl yh [x y _ _]]
  (and (>= x xl) (< x xh) (>= y yl) (< y yh)))

(defn quadrants [robots]
  (let [ul (count (filter (partial in-quadrant? 0 m-col 0 m-row) robots))
        ur (count (filter (partial in-quadrant? (inc m-col) cols 0 m-row) robots))
        bl (count (filter (partial in-quadrant? 0 m-col (inc m-row) rows) robots))
        br (count (filter (partial in-quadrant? (inc m-col) cols (inc m-row) rows) robots))]
    [ul ur bl br]))

(defn variance [robots]
  (let [xs (map (fn [[x y _ _]] (+ (abs (- m-col x)) (abs (- m-row y)))) robots)]
    (reduce + xs)))

(defn part-1 []
  (->> (quadrants (nth (iterate #(map move %) robots) 100))
       (reduce #(* %1 %2) 1)))

(defn -main []
  (loop [r robots i 0 s #{}]
    (let [r* (map move r)
          v (variance r)]
      (when (= 13949 v) (println i)) ;; 134949 == lowest variance after 1M iterations
      (if (= i 1000000) (println (take 5 (sort s)))
          (recur r* (inc i) (conj s v))))))
