(ns advent-of-code.2024.day20
  (:require [clojure.string :as str]
            [advent-of-code.utils :as u]))

(def racetrack (->> (slurp "resources/2024/day20.txt")
                    (str/split-lines)
                    (u/index2d)))

(defn rm-invalid [m s xs]
  (remove s (filter #(= "." (m %)) xs)))

(defn shortest-path [start end m]
  (loop [coll [start] seen #{}]
    (let [xs (set (mapcat #(rm-invalid m seen (u/neighbors %)) coll))]
      (cond
        (contains? xs end) seen
        (empty? xs) nil
        :else (recur  xs (apply conj seen coll))))))

(defn shortest-path-len [start end m]
  (loop [coll [start] seen #{} i 0]
    (let [xs (set (mapcat #(rm-invalid m seen (u/neighbors %)) coll))]
      (cond
        (contains? xs end) i
        (empty? xs) -1
        :else (recur  xs (apply conj seen coll) (inc i))))))

(defn candidates [start end m]
  (let [xs (shortest-path start end m)]
    (filter (fn [[x y]] (and (or (and (= "." (m [(dec x) y])) (= "." (m [(inc x) y])))
                                 (and (= "." (m [x (dec y)])) (= "." (m [x (inc y)]))))
                             (= "#" (m [x y]))))
            (set (mapcat u/neighbors xs)))))

(defn part-1 []
  (let [start [111 75]
        end  [95 63]
        m (-> racetrack (assoc start ".") (assoc end "."))
        walls (candidates start end m)
        orig-path (shortest-path start end m)
        orig-time (count orig-path)]
    (->> (pmap #(- orig-time (shortest-path-len start end (assoc m % "."))) walls)
         (filter #(>= % 100))
         (count)
         (println))))
