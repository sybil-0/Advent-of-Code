(ns advent-of-code.2016.day9
  (:require [clojure.string :as str]))

(defn expand [s]
  (let [m (re-find #"\(\d+x\d+\)" s)
        i (str/index-of s m)
        [n rp] (map read-string (str/split (subs s (inc i) (+ i (dec (count m)))) #"x"))
        rp-str (str/join (flatten (repeat 2 (take n (subs s (+ i (count m)))))))]
    (str/re)))

(expand "A(2x2)BCD(2x2)")
