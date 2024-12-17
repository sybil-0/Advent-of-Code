(ns advent-of-code.2024.day17
  (:require [clojure.string :as str]))

(def p1-input 51571418)

(def program [2,4,1,1,7,5,0,3,1,4,4,5,5,5,3,0])

(def start {:registers {:A 0 :B 0 :C 0}
            :pos 0
            :output []
            })

(defn get-reg [i] ({4 :A 5 :B 6 :C} i))

(defn step [{regs :registers p :pos out :output :as vm}]
  (let [opcode (program p)
        i (program (inc p))
        operand (if (> i 3 ) (regs (get-reg i)) i)
        [a b c] (map regs [:A :B :C])]
    (-> (case opcode
          0 (assoc-in vm [:registers :A] (biginteger (/ a (Math/pow 2 operand))))
          1 (assoc-in vm [:registers :B] (.xor (biginteger b) (biginteger i)))
          2 (assoc-in vm [:registers :B] (mod operand 8))
          3 (if (zero? a) vm (assoc vm :pos i))
          4 (assoc-in vm [:registers :B] (.xor b c))
          5 (update vm :output #(conj % (mod operand 8)))
          6 (assoc-in vm [:registers :B] (biginteger (/ a (Math/pow 2 operand))))
          7 (assoc-in vm [:registers :C] (biginteger (/ a (Math/pow 2 operand))))
          (throw (Exception. "invalid state")))
        (update :pos #(+ % (if (and (= opcode 3) (not (zero? a))) 0 2))))))

(defn process [vm]
  (if (>= (vm :pos) (count program))
    vm (recur (step vm))))

(defn part-1 [] (process (assoc-in start [:registers :A] p1-input)))

(defn -main []
  (loop [i 0]
    (let [vm (assoc-in start [:registers :A] i)
          result ((process vm) :output)
          xs (drop (- (count program) (count result)) program)]
      (println [i xs result])
      (cond
        (= result program) i
        (= xs result) (recur (* i 8))
        :else (recur (inc i))))))
