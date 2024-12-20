(ns advent-of-code.core)

(defn foo []
  (loop [i 0]
    (recur (inc i))))
