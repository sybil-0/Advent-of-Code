(ns advent-of-code.2016.day20
  (:require [clojure.string :as str]
            [advent-of-code.utils :as u]))


(def segments (->> (slurp "resources/2016/day20.txt")
                   (str/split-lines)
                   (map (fn [line] (str/split line #"-")))
                   (map (fn [line] (map bigint line)))
                   (sort-by first)))

(defn calc-regions []
  (loop [[hd & tl] segments low 0 high 0 regions []]
    (let [[x y] hd]
      (cond
        (nil? hd) (conj regions [low high])
        (> x (inc high)) (recur tl x y (conj regions [low high]))
        :else (recur tl low (max y high) regions)))))

(defn diff [[[a b] [c d]]]
  (dec (- c b)))

(defn solve []
  (let [r (calc-regions)
        gaps (reduce + (map diff (partition 2 1 r)))]
    (println (inc (last (first r))))
    (println gaps)))
