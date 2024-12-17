(ns advent-of-code.2024.day13
  (:require [clojure.string :as str]
            [clojure.core.logic :as l]
            [clojure.core.logic.fd :as fd]))

(defn parse [s]
  (map #(bigint %) (re-seq #"\d+" s)))

(def input (->> (str/split (slurp "resources/2024/day13.txt") #"\r\n\r\n")
                (map parse)))
        
(defn solve
  [x1 y1 x2 y2 t1 t2]
  (let [solutions
        (l/run* [q]
          (l/fresh [i j]
            (l/== q [i j])
            (fd/in i j  (fd/interval 1 10000000000000))
            (fd/eq
             (= (+ (* i x1) (* j x2)) (+ 10000000000000 t1))
             (= (+ (* i y1) (* j y2)) (+ 10000000000000 t2)))))]
    (if (empty? solutions) 0
        (apply min (map (fn [[a b]] (+ (* 3 a) b)) solutions))))) 

(defn -run []
  (reduce + (map #(apply solve %) input)))

