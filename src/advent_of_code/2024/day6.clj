(ns aoc-2024.day6
  (:require [clojure.string :as str]))

(def lab (->> (slurp "resources/day6.txt")
              (str/split-lines)))

(defn turn [{f :facing :as guard}]
  (assoc guard :facing
         (case f :up :right :right :down :down :left :left :up)))

(defn move [guard]
  (case (guard :facing)
    :up (update guard :x dec)
    :right (update guard :y inc)
    :down (update guard :x inc)
    :left (update guard :y dec)))

(defn move-guard [m]
  (loop [g {:x 43 :y 37 :facing :up} seen #{}]
    (let [{x :x y :y :as g*} (move g)
          alt (turn g)
          pos (try (aget m x y) (catch Exception e) (finally nil))
          seen* (conj seen (vals g))]
      (cond
        (nil? pos) seen*
        (contains? seen (vals g)) -1
        :else (recur (if (= pos \#) alt g*) seen*)))))

(def guard-path
  (->> (move-guard (to-array-2d lab))
       (map (partial take 2))
       (set)))

(defn part-2 []
  (->> (for [[x y] guard-path]
         (let [m (to-array-2d lab)]
           (aset m x y \#)
           (move-guard m)))
       (filter #{-1})
       (count)))

