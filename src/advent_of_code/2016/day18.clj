(ns advent-of-code.2016.day18
  (:require [clojure.string :as str]))

(def testinput ".^..^....^....^^.^^.^.^^.^.....^.^..^...^^^^^^.^^^^.^.^^^^^^^.^^^^^..^.^^^.^^..^.^^.^....^.^...^^.^.")

(defn apply-rules [triplet]
  (case triplet
    [\^ \^ \.] \^
    [\. \^ \^] \^
    [\^ \. \.] \^
    [\. \. \^] \^
    \.))

(defn next-row [row]
  (let [xs (str/join ["." row "."])]
    (str/join (map apply-rules (partition 3 1 xs)))))

(defn part-1 []
  (->> (take 400000 (iterate next-row testinput))
       (apply concat)
       (filter #{\.})
       (count)))

