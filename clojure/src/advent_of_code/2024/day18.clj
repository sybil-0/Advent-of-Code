(ns advent-of-code.2024.day18
  (:require [clojure.string :as str]
            [advent-of-code.utils :as u]))

(def walls (->> (str/split-lines (slurp "resources/2024/day18.txt"))
                (map u/str-to-ints)))

(defn open [i] (->> (for [x (range 71) y (range 71)] [x y])
                    (remove (set (take i walls)))))

(defn shortest-path [start end s]
  (loop [coll [start] seen #{} i 0]
    (let [xs (set (mapcat #(remove seen (filter s (u/neighbors %))) coll))]
      (cond
        (contains? xs end) i
        (empty? xs) -1
        :else (recur  xs (apply conj seen coll) (inc i))))))

(defn part-1 [] (shortest-path [0 0] [70 70] (set (open 1024))))

(defn part-2 []
  (->> (for [i (range 1024 (count walls))]
         [i (shortest-path [0 0] [70 70] (set (open i)))])))
