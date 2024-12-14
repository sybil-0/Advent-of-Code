(ns advent-of-code.2017.day15)

(defn generator [factor start m]
  (loop [coll [] i 0 n start]
    (let [n* (mod (* n factor) 2147483647)]
      (cond
        (= i 5000000) coll
        (zero? (mod n m)) (recur (conj coll n) (inc i) n*)
        :else (recur coll i n*)))))

(defn gen-match [[a b]]
  (if (= (mod a 65536) (mod b 65536)) 1 0))

(defn solve []
  (let [gen-a (generator 16807 722 4)
        gen-b (generator 48271 354 8)
        coll (map vector gen-a gen-b)]
    (reduce #(+ %1 (gen-match %2)) 0 coll)))

