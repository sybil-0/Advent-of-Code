(ns aoc-2024.day3
  (:require [clojure.string :as str]))


(def input (slurp "resources/day3.txt"))

(defn find-mul [s]
  (re-seq #"mul\(\d+,\d+\)" s))

(defn eval-mul [s]
  (let [[x y] (re-seq #"\d+" s)]
    (* (Integer/parseInt x) (Integer/parseInt y))))

(defn cleanup [s]
  (str/replace s #"don't\(\)[\S\s]+?do\(\)" ""))

(defn part-1 []
  (reduce + (map eval-mul (find-mul input))))

(defn part-2 []
  (reduce + (map eval-mul (find-mul (cleanup input)))))
