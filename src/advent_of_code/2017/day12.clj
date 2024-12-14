(ns advent-of-code.2017.day12
  (:require [clojure.string :as str]))

(defn parse [line]
  (let [[x  more] (str/split line #" <-> ")
        nodes (str/split more #", ")]
    [(Integer/parseInt x) (map #(Integer/parseInt %) nodes)]))

(def connections (->> (slurp "resources/2017/day12.txt")
                      (str/split-lines)
                      (map parse)
                      (into {})))

(defn bfs [start]
  (loop [queue [start] seen #{}]
    (let [[hd & tl] queue
          more (remove seen (connections hd))
          seen* (conj seen hd)]
      (if (empty? queue) seen
          (recur (apply conj tl more) seen*)))))

(defn all-groups []
  (loop [nodes (set (keys connections)) regions []]
    (let [hd (first nodes)
          g (bfs hd)]
      (if (empty? nodes) (count regions)
          (recur (remove g nodes) (conj regions g))))))

