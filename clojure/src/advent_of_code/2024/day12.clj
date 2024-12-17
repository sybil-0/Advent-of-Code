(ns advent-of-code.2024.day12
  (:require [clojure.string :as str]
            [advent-of-code.utils :refer [neighbors index2d adjacent]]
            [clojure.set :refer [union]]))


(def garden (->> (slurp "resources/2024/day12.txt")
                 (str/split-lines)
                 (index2d)))

(def plants (group-by #(garden %) (keys garden)))

(defn flood [start p]
  (loop [queue #{start} seen #{}]
    (if (empty? queue) seen
        (let [queue* (remove seen (filter #(= (garden %) p) (mapcat neighbors queue)))]
          (recur (set queue*) (union seen queue))))))

(defn partition-regions [plant]
  (loop [coords (plants plant) regions []]
    (if (empty? coords) regions
        (let [cur-region (flood (first coords) plant)]
          (recur (remove cur-region coords) (conj regions cur-region))))))


(defn corners [s p]
  (for [[x y] s]
    (let [ul (garden [(dec x) (dec y)])
          uc (garden [(dec x) y])
          ur (garden [(dec x) (inc y)])
          cl (garden [x (dec y)])
          cr (garden [x (inc y)])
          bl (garden [(inc x) (dec y)])
          bc (garden [(inc x) y])
          br (garden [(inc x) (inc y)])]
      (cond
        (and (= bc p) (= cr p) (not= ul p) (not= cl p) (not= uc p)) 1
        (and (= bc p) (= cl p) (not= ur p) (not= cr p) (not= uc p)) 1
        (and (= uc p) (= cr p) (not= bl p) (not= cl p) (not= bc p)) 1
        (and (= uc p) (= cl p) (not= br p) (not= cr p) (not= bc p)) 1
        (and (= bc p) (= cr p) (not= br p)) 1
        (and (= uc p) (= cr p) (not= ur p)) 1
        :else 0
        ))))

(defn price [plant]
  (reduce + (for [plot (partition-regions plant)]
              (let [perimeter (remove plot (mapcat #(neighbors %) plot))]
                (* (corners plot) (count plot))
                ))))

(defn solve [] (reduce + (map price (keys plants))))


