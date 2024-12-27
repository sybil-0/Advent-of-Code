(ns advent-of-code.2015.day14)

(defn move-reindeer [[s t d speed sleep fly]]
  (cond
    (and (= s :flying) (> t 0)) [:flying (dec t) (+ d speed) speed sleep fly]
    (and (= s :resting) (> t 0)) [:resting (dec t) d speed sleep fly]
    (and (= s :flying) (zero? t)) [:resting (dec sleep) d speed sleep fly]
    (and (= s :resting) (zero? t)) [:flying (dec fly) d speed sleep fly]))

(defn part-1 []
  (let [reindeer {:vixen (nth (iterate move-reindeer [:flying 8 0 8 53 8]) 2503)
                  :blitzen (nth (iterate move-reindeer [:flying 4 0 13 49 4]) 2503)
                  :rudolph (nth (iterate move-reindeer [:flying 7 0 20 132 7]) 2503)
                  :cupid (nth (iterate move-reindeer [:flying 4 0 12 43 4]) 2503)
                  :donner (nth (iterate move-reindeer [:flying 5 0 9 38 5]) 2503)
                  :dasher (nth (iterate move-reindeer [:flying 4 0 10 37 4]) 2503)
                  :comet (nth (iterate move-reindeer [:flying 37 0 3 76 37]) 2503)
                  :prancer (nth (iterate move-reindeer [:flying 12 0 9 97 12]) 2503)
                  :dancer (nth (iterate move-reindeer [:flying 1 0 37 36 1]) 2503)}]
    (map (fn [[k v]] (nth v 2)) reindeer)
    ))

(part-1)
(apply max (part-1))
