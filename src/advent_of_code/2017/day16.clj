(ns advent-of-code.2017.day16
  (:require [clojure.string :as str]
            [advent-of-code.utils :as u]))


(def start (mapv (comp str char) (range 97 113)))

(def input (-> (slurp "resources/2017/day16.txt")
               (str/trim)
               (str/split #",")))

(defn swap-idx [v a b]
  (u/swap v (.indexOf v a) (.indexOf v b)))

(defn process [v instruction]
  (let [[c & more] instruction
        s (str/join more)]
    (case c
      \s (u/rotate v (Integer/parseInt s))
      \x (apply u/swap v (map read-string (str/split s #"/")))
      \p (apply swap-idx v (str/split s #"/"))
      )))

(defn part-1 []
  (->> (reduce process start input)
       (str/join)))

;; loop size = 560000
(defn part-2 []
  (loop [programs start [x & more] (cycle input) i 0]
    (let [s (str/join programs)]
      (when (= i 390353) (println s))
      (if (= i 2000000) nil
          (recur (process programs x) more (inc i))))))
