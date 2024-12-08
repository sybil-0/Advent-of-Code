(ns aoc-2024.day7
  (:require [clojure.string :as str]))

(def input (->> (slurp "resources/day7.txt")
                (str/split-lines)
                (map #(map bigint (str/split % #"\s+")))))

(defn || [a b] (bigint (str a b)))

(defn gen-solutions [i t [hd & tl :as xs]]
  (cond
    (and (empty? xs) (= i t)) [true]
    (and (empty? xs) (not= i t)) [false]
    :else (mapcat #(gen-solutions (% i hd) t tl) [+ * ||])))

(defn valid? [[result x & xs]]
  (some true? (gen-solutions x result xs)))

(defn solve []
  (reduce + (map first (filter valid? input))))
