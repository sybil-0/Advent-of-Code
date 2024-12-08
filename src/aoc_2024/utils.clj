(ns aoc-2024.utils
  (:require [clojure.string :as str]))

(defn remove-idx [i items]
  (keep-indexed #(when-not (= i %1) %2) items))
