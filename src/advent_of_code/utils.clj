(ns advent-of-code.utils 
  (:require [clojure.string :as s]))

(defn str-to-ints
  [xs]
  (->> (re-seq  #"\d+" xs)
       (map #(Integer/parseInt %))))

(defn transpose
  [xs]
  (vec (apply map vector xs)))

(defn adjacent [[i j]]
  (remove #{[i j]}
          (for [x [-1 0 1] y [-1 0 1]]
            [(+ i x) (+ j y)])))

(defn pprint-map [m]
  (let [min-x (apply min (map first (keys m)))
        max-x (inc (apply max (map first (keys m))))
        min-y (apply min (map second (keys m)))
        max-y (inc (apply max (map second (keys m))))]
    (doseq [i (range min-x max-x)]
      (doseq [j (range min-y max-y)]
        (print (get m [i j] " ")))
      (println))))

(defn pprint-array [array]
  (doseq [row array]
    (println (clojure.string/join row))))

(defn re-seq-pos [pattern string] 
  (let [m (re-matcher pattern string)] 
    ((fn step [] 
       (when (. m find) 
         (cons {:start (. m start) :end (. m end) :number (. m group)} 
               (lazy-seq (step))))))))

(defn index2d 
  [matrix]
  (->> (for [x (range (count matrix))
             y (range (count (first matrix)))
             v (str (get (get matrix x) y))]
         [[x y] (str v)])
       (into {})))

(defn map-to-2d [m]
  (let [row-count (map first (keys m))]
    (map (range row-count))))

(defn pprint-map [m]
  (let [x-min (apply min (map first (keys m)))
        x-max (inc (apply max (map first (keys m))))
        y-min (apply min (map second (keys m)))
        y-max (inc (apply max (map second (keys m))))]
    (doseq [i (range x-min x-max)]
      (doseq [j (range y-min y-max)]
        (print (get m [i j] " ")))
      (println))))

(def neighbors 
  (memoize (fn [[x y]]
             [[(dec x) y] [(inc x) y] [x (dec y)] [x (inc y)]])))

(defn tentative [g grid candidate current]
  (let [dist (+ current (grid candidate))]
    (if (> (g candidate) dist)
      (assoc g candidate dist)
      g)))

(defn dijkstra [start target grid]
  (loop [g (-> (into {} (map (fn [k] {k 999999}) (keys grid)))
               (assoc [0 0] 0))
         unvisited (set (keys grid))
         current start]
    (let [nbs (neighbors current unvisited)
          g* (reduce (fn [acc n] (tentative acc n (g current))) g nbs)
          unvisited* (set (remove #{current} unvisited))
          current*   (if (empty? unvisited*) nil
                         (apply min-key #(g %) unvisited*))]
      (if (not (contains? unvisited* target))
        (g target)
        (recur g* unvisited* current*)))))


(defn swap [v i1 i2]
  "swap to values in a vector."
  (assoc v i2 (v i1) i1 (v i2)))


(defn insert
  "insert into vector at position i" 
   [v i e] (vec (concat (subvec v 0 i) [e] (subvec v i))))

(defn index-exclude [r ex] 
  "Take all indices execpted ex." 
  (filter #(not (ex %)) (range r))) 

(defn dissoc-idx [v & ds]
  "remove one or more indices from vector."
  (mapv v (index-exclude (count v) (into #{} ds))))
