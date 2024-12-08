(ns aoc-2024.day5
  (:require [clojure.string :as str]))

(def input (slurp "resources/day5.txt"))

(defn parse [pred s]
  (->> (str/split input #"\r\n\r\n")
       (pred)
       (str/split-lines)
       (mapv #(mapv read-string (str/split % s)))))

(def orderings (parse first #"\|"))

(def pages (parse second #","))

(defn middle [xs] (nth xs (quot (count xs) 2)))

(defn page-comp [x y]
  (cond
    (.contains orderings [x y]) -1
    (.contains orderings [y x]) 1
    :else 0))

(defn p-sorted? [p] (= (sort page-comp p) p))

(defn part-1 []
  (->> (filter p-sorted? pages)
       (map middle)
       (reduce +)))

(defn part-2 []
  (->> (remove p-sorted? pages)
       (map (comp middle (partial sort page-comp)))
       (reduce +)))
