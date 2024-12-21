(ns advent-of-code.2024.day20
  (:require [clojure.string :as str]
            [advent-of-code.utils :as u]))

(def racetrack (->> (slurp "resources/2024/day20.txt")
                    (str/split-lines)
                    (u/index2d)))

(def end [95 63])

(defn in-range [[x y] [i j]]
  (<= (+ (abs (- x i)) (abs (- y j))) 20))

(defn walk-path [cur seen]
  (let [nbs (filter #(= "." (racetrack %)) (remove (set seen) (u/neighbors cur)))
        nxt (first nbs)]
    (if (= cur end) seen
        (recur nxt (conj seen cur)))))

(defn shortcuts [path l]
  (->> (for [i (range 0 (- l 100))]
         (for [j (range (inc i) l)]
           (when (in-range (nth path i) (nth path j)) (- j i))))
       (flatten)
       (remove nil?)))

(defn part-1 []
  (let [path (conj (walk-path [111 75] []) end) 
        l (dec (count path))
        diffs (shortcuts path l)]
    (->> (filter #(>= % 102) diffs)
         (count))))
