(ns advent-of-code.2024.day19
  (:require [clojure.string :as str]))

(def patterns (-> (str/split (slurp "resources/2024/day19.txt") #"\r\n\r\n")
                   (first)
                   (str/split #",")
                   (#(map str/trim %))))

(def towels (-> (str/split (slurp "resources/2024/day19.txt") #"\r\n\r\n")
                (second)
                (str/split-lines)))

(defn matches? [towel p]
  (if (< (count towel) (count p)) false
      (= (subs towel 0 (count p)) p)))

(def possible
  (memoize (fn [towel]
             (if (empty? towel) 1
                 (let [ps (filter (partial matches? towel) patterns)]
                   (reduce + (map #(possible (subs towel (count %))) ps)))))))

(defn part-1 [] (count (filter (comp not zero? possible) towels))) 

(defn part-2 [] (reduce #(+ %1 (possible %2)) 0 towels)) 
