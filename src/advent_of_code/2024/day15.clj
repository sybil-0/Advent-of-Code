(ns advent-of-code.2024.day15
  (:require [clojure.string :as str]
            [advent-of-code.utils :as utils]))

(defn expand-cell [c]
  (case c
    \# "##"
    \O "[]"
    \. ".."
    \@ "@."))

(def warehouse (->> (slurp "resources/2024/day15.txt")
                    (str/split-lines)
                    (utils/index2d)))

(def warehouse-wide (->> (slurp "resources/2024/day15.txt")
                         (str/split-lines)
                         (mapv #(mapv (partial expand-cell) %))
                         (mapv #(str/join %))
                         (utils/index2d)))

(def move-input (str/trim (slurp "resources/2024/day15_steps.txt")))

(def start [24 48])

(defn step [[x y] direction]
  (case direction
    "<" [x (dec y)]
    ">" [x (inc y)]
    "v" [(inc x) y]
    "^" [(dec x) y]
    [x y]))

(defn box? [m p] (or (= (m p) "[") (= (m p) "]")))

(defn swap-keys [m seen dir]
  (let [xs (map (fn [p] [(step p dir) (m p)]) seen)]
    (-> (reduce (fn [acc [p v]] (assoc acc p v)) m xs)
        (assoc (first seen) "."))))

(defn next-row [m [x y] dir]
  (let [c (m [x y])
        ur [(dec x) (inc y)]
        ul [(dec x) (dec y)]
        bl [(inc x) (dec y)]
        br [(inc x) (inc y)]
        md [(inc x) y]
        mu [(dec x) y]]
    (case [c dir]
      ["[" "v"] (if (= (m md) "]") [md bl] [md])
      ["[" "^"] (if (= (m mu) "]") [mu ul] [mu])
      ["]" "v"] (if (= (m md) "[") [md br] [md])
      ["]" "^"] (if (= (m mu) "[") [mu ur] [mu]))))

(defn connected-boxes [m [x y :as p] dir]
  (loop [xs [p (if (= (m p) "[") [x (inc y)] [x (dec y)])] boxes (set xs)]
    (let [nbs (mapcat #(next-row m % dir) xs)
          xs* (filter #(box? m %) nbs)]
      (if (empty? xs*) (apply conj boxes xs)
          (recur xs* (apply conj boxes xs*))))))

(defn push-boxes [m p dir]
  (loop [p* (step p dir) seen []]
    (condp = (m p*)
      "." (swap-keys m seen dir)
      "#" m
      (recur (step p* dir) (conj seen p*)))))

(defn push-stack [m p dir]
  (let [boxes (vec (connected-boxes m p dir))
        boxes* (map (fn [x] [(step x dir) (m x)]) boxes)]
    (if (some #(= (m %) "#") (map first boxes*))
      m
      (reduce (fn [acc [x y]] (assoc acc x y))
              (reduce #(assoc %1 %2 ".") m boxes) boxes*))))

(defn simulate [wh]
  (loop [p start [x & xs] move-input m wh]
    (let [d (str x)
          p* (step p d)
          m* (if (and (or (= d "v") (= d "^")) (box? m p*))
               (push-stack m p* d)
               (push-boxes m p d))]
      (cond
        (nil? x) m
        (= (m p*) "#") (recur p xs m)
        (= (m p*) ".") (recur p* xs m)
        :else (if (not= m m*)
                (recur p* xs m*)
                (recur p xs m))))))

(defn solve []
  (let [result (simulate (assoc warehouse-wide start "."))]
    (->> (filter #(= "[" (result %)) (keys result))
         (reduce (fn [acc [x y]] (+ acc (+ (* 100 x) y))) 0))))

