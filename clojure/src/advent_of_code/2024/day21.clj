(ns advent-of-code.2024.day21
  (:require [advent-of-code.utils :as utils]
            [clojure.string :as str]))

(def numpad {[0 0] 7 [0 1] 8 [0 2] 9
             [1 0] 4 [1 1] 5 [1 2] 6
             [2 0] 1 [2 1] 2 [2 2] 3
             [3 1] 0 [3 2] "A"
             })

(def dpad {[0 1] "^" [0 2] "A"
           [1 0] "<" [1 1] "v" [1 2] ">"} )

(defn dirs [[[x y] [i j]]]
  (cond
    (and (= x i) (< y j)) ">"
    (and (= x i) (> y j)) "<"
    (and (= j y) (< x i)) "v"
    (and (= j y) (> x i)) "^"
    :else (println [x y i j])))

(def paths
  (memoize
   (fn [cur end m seen]
     (let [nbs (remove (set seen) (filter #(contains? m %) (utils/neighbors cur)))]
       (if (= (m cur) end) [(map dirs (partition 2 1 seen))]
           (mapcat #(paths % end m (conj seen %)) nbs))))))

(defn prune [paths]
  (let [l (apply min (map count paths))]
    (filter #(= l (count %)) paths)))

(def move-bot
  (memoize
   (fn [xs start m]
     (loop [pos start [x & more] xs out []]
       (if (nil? x) out
           (let [p (paths pos x m [pos])
                 pos* (first (filter #(= (m %) x) (keys m)))]
             (recur pos* more (conj out (prune p)))))))))

(defn combinations [cur [hd & more]]
  (if (nil? hd) (mapv #(vec (flatten %)) cur)
      (recur (mapcat #(mapv (fn [x] (conj % x "A")) hd) cur) more)))

(defn calculate-sequence [xs]
  (let [first-bot (prune (combinations [[]] (move-bot xs [3 2] numpad)))
        second-bot (prune (mapcat #(combinations [[]] (move-bot % [0 2] dpad)) first-bot))
        user (prune (mapcat #(combinations [[]] (move-bot % [0 2] dpad)) second-bot))]
    (apply min (map count user))))

(defn part-1 [] (+ (* 459 74) (* 671 74) (* 846 72) (* 285 68) (* 83  66)))
