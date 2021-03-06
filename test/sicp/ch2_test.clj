(ns sicp.ch2-test
  (:require [sicp.ch2 :as ch2]
            [sicp.ch1 :refer [abs square]]
            [clojure.pprint :as pp]
            [clojure.test :refer [deftest is testing]]))

(def one-third (ch2/make-rat 1 3))

(deftest ex1-test
  (is (= "1/3" (ch2/format-rat one-third)))
  (is (= "-1/3" (ch2/format-rat (ch2/make-rat -1 3))))
  (is (= "-1/3" (ch2/format-rat (ch2/make-rat 1 -3))))
  (is (= "1/3" (ch2/format-rat (ch2/make-rat -1 -3))))
  (is (= "2/3" (ch2/format-rat (ch2/add-rat one-third one-third)))))

(deftest ex2-test
  (is (= "(1, 1)" (ch2/format-point
                   (ch2/midpoint-segment
                    (ch2/make-segment (ch2/make-point 0 0)
                                      (ch2/make-point 2 2))))))

  (is (= "(6, 6)" (ch2/format-point
                   (ch2/midpoint-segment
                    (ch2/make-segment (ch2/make-point 11 11)
                                      (ch2/make-point 1 1))))))

  (is (= "(0, -5)" (ch2/format-point
                   (ch2/midpoint-segment
                    (ch2/make-segment (ch2/make-point -5 0)
                                      (ch2/make-point 5 -10)))))))

(deftest ex3-test
  (is (= 4 (ch2/perimeter-rectangle
            (ch2/make-rectangle
             (ch2/make-point 0 0)
             (ch2/make-point 0 1)
             (ch2/make-point 1 1)
             (ch2/make-point 1 0)))))

  (is (= 8 (ch2/perimeter-rectangle
            (ch2/make-rectangle
             (ch2/make-point 0 0)
             (ch2/make-point 0 2)
             (ch2/make-point 2 2)
             (ch2/make-point 2 0)))))

  (is (= 1 (ch2/area-rectangle
            (ch2/make-rectangle
             (ch2/make-point 0 0)
             (ch2/make-point 0 1)
             (ch2/make-point 1 1)
             (ch2/make-point 1 0)))))

  (is (= 4 (ch2/area-rectangle
            (ch2/make-rectangle
             (ch2/make-point 0 0)
             (ch2/make-point 0 2)
             (ch2/make-point 2 2)
             (ch2/make-point 2 0)))))

  (is (= 4 (ch2/perimeter-rectangle
            (ch2/segments->rectangle
             (ch2/make-segment (ch2/make-point 0 0) (ch2/make-point 1 0))
             (ch2/make-segment (ch2/make-point 0 0) (ch2/make-point 0 1))))))

  (is (= 8 (ch2/perimeter-rectangle
            (ch2/segments->rectangle
             (ch2/make-segment (ch2/make-point 0 0) (ch2/make-point 2 0))
             (ch2/make-segment (ch2/make-point 0 0) (ch2/make-point 0 2))))))

  (is (= 1 (ch2/area-rectangle
            (ch2/segments->rectangle
             (ch2/make-segment (ch2/make-point 0 0) (ch2/make-point 1 0))
             (ch2/make-segment (ch2/make-point 0 0) (ch2/make-point 0 1))))))

  (is (= 4 (ch2/area-rectangle
            (ch2/segments->rectangle
             (ch2/make-segment (ch2/make-point 0 0) (ch2/make-point 2 0))
             (ch2/make-segment (ch2/make-point 0 0) (ch2/make-point 0 2)))))))

(deftest ex4-test
  (is (= 1 (ch2/car-fn (ch2/cons-fn 1 2))))
  (is (= 2 (ch2/cdr-fn (ch2/cons-fn 1 2)))))

(deftest ex5-test
  (is (= 5 (ch2/car-num (ch2/cons-num 5 6))))
  (is (= 6 (ch2/cdr-num (ch2/cons-num 5 6)))))

(deftest ex6-test
  (is (= (((ch2/add-1 ch2/zero) inc) 0) ((ch2/one inc) 0)))

  (is (= (((ch2/add-1 (ch2/add-1 ch2/zero)) inc) 0) ((ch2/two inc) 0)))

  (is (= 3 (((ch2/add-church-numerals ch2/one ch2/two) inc) 0))))

(deftest ex7-test
  (is (= 3 (ch2/lower-bound (ch2/make-interval 3 5))))

  (is (= 5 (ch2/upper-bound (ch2/make-interval 3 5)))))

(deftest ex8-test
  (let [a (ch2/make-interval 1 3)
        b (ch2/make-interval 2 6)]
    (is (= -5 (ch2/lower-bound (ch2/sub-interval a b))))

    (is (= 1 (ch2/upper-bound (ch2/sub-interval a b))))))

(deftest ex9-test
  (let [a (ch2/make-interval 1 3)
        b (ch2/make-interval 2 4)]
    (is (= (+ (ch2/width-interval a) (ch2/width-interval b))
           (ch2/width-interval
            (ch2/add-interval a b)))))

  (let [a (ch2/make-interval 1 3)
        b (ch2/make-interval 2 4)]
    (is (= (+ (ch2/width-interval a) (ch2/width-interval b))
           (ch2/width-interval
            (ch2/sub-interval a b)))))

  (let [a (ch2/make-interval 1 3)
        b (ch2/make-interval 2 4)]
    (is (not= (+ (ch2/width-interval a) (ch2/width-interval b))
              (ch2/width-interval
               (ch2/mul-interval a b)))))
  (let [a (ch2/make-interval 1 3)
        b (ch2/make-interval 2 4)]
    (is (not= (+ (ch2/width-interval a) (ch2/width-interval b))
              (ch2/width-interval
               (ch2/div-interval a b))))))

(deftest ex10-test
  (is (= "Cannot divide by interval spanning 0"
         (try (ch2/div-interval (ch2/make-interval 3 4)
                                (ch2/make-interval -1 1))
              (catch Exception e (ex-message e))))))

(deftest ex11-test
  (doseq [a (range -4 5)]
    (doseq [b (range (inc a) 5)]
      (doseq [c (range -4 5)]
        (doseq [d (range (inc c) 5)]
          (let [x (ch2/make-interval a b)
                y (ch2/make-interval c d)]
            (is (= [x y (ch2/mul-interval x y)]
                   [x y (ch2/mul-interval-via-cases x y)]))))))))

(deftest ex12-test
  (let [i (ch2/make-center-percent 100 0.05)]
    (is (= 0.05 (ch2/percent i)))

    (is (= 95.0 (ch2/lower-bound i)))

    (is (= 105.0 (ch2/upper-bound i)))

    (is (= 5.0 (ch2/width i)))))

(deftest ex13-test
  (letfn [(close-enough? [a b] (< (abs (- a b)) 0.0000001))]
    (doseq [p (range 0.0001 0.0010 0.0001)]
      (doseq [q (range 0.0001 0.0010 0.0001)]
        (let [x (ch2/make-center-percent 100 p)
              y (ch2/make-center-percent 100 q)]
          (is (close-enough? (+ p q) (ch2/percent (ch2/mul-interval x y)))))))))

(deftest ex14-test
  (letfn [(close-enough? [a b] (< (abs (- a b)) 0.0000001))]
    (doseq [p (range 0.0001 0.0010 0.0001)]
      (doseq [q (range 0.0001 0.0010 0.0001)]
        (let [x (ch2/make-center-percent 100 p)
              y (ch2/make-center-percent 100 q)]
          (is (not= (ch2/par1 x y) (ch2/par2 x y))))))))

(deftest ex15-test
    ;; Interval multiplication has a scaling effect which relates to
    ;; rational number multiplication differently than may be expected.
    ;; For example, while (x^2)/x = x holds for rationals, it does not
    ;; hold for intervals; neither does x/(x^2) = 1/x. Minimizing this
    ;; effect by minimizing the number of multiplication operations
    ;; will produce more narrow intervals, since it minimizes the
    ;; scaling effect, thus producing tighter error bounds.
  (let [one (ch2/make-interval 1 1)
        x (ch2/make-interval 1/2 2)]
    (is (not= x (ch2/div-interval (ch2/mul-interval x x) x)))

    (is (not= x (ch2/div-interval x (ch2/mul-interval x x))))))

(deftest ex16-test
  ;; It follows from the above observation of (x^2)/x not being x that
  ;; interval multiplication and division are not inverse operations.
  ;; Consequently, the algebraic properties assumed when asserting the
  ;; equations underlying par1 and par2 are equal for intervals do not
  ;; hold. Thus, while "equivalent" algebraic expressions may seem to
  ;; produce different results, this is due to false algebraic
  ;; assumptions. With interval multiplication and division defined as
  ;; they are here, it is impossible to make these two equations equal
  ;; in general, since doing so requires the above property holds.
  (let [one (ch2/make-interval 1 1)
        x (ch2/make-interval 1/2 2)]
    (is (not= x (ch2/div-interval (ch2/mul-interval x x) x)))

    (is (not= x (ch2/div-interval x (ch2/mul-interval x x))))))

(deftest ex17-test
  (is (= (ch2/list 34) (ch2/last-pair (ch2/list 23 72 149 34))))
  (is (= nil (ch2/last-pair (ch2/list))))
  (is (= (ch2/list 1) (ch2/last-pair (ch2/list 1)))))

(deftest ex18-test
  (is (= (ch2/list 25 16 9 4 1) (ch2/reverse (ch2/list 1 4 9 16 25))))

  (is (= (ch2/list) (ch2/reverse (ch2/list))))

  (is (= (ch2/list 1) (ch2/reverse (ch2/list 1))))

  (is (= (ch2/list 2 1) (ch2/reverse (ch2/list 1 2)))))

(deftest ex19-test
  ;; The order of the coin list does not effect the results, because
  ;; all combinations of coins less than or equal to amount are checked,
  ;; with the amounts combined additively, so the combinations are not
  ;; sensitive to order.
  (is (= 4 (ch2/cc 11 ch2/us-coins)))

  (is (= 4 (ch2/cc 11 (ch2/reverse ch2/us-coins)))))

(deftest ex20-test
  (is (= (ch2/list 1 3 5 7) (ch2/same-parity 1 2 3 4 5 6 7)))

  (is (= (ch2/list 2 4 6) (ch2/same-parity 2 3 4 5 6 7))))

(deftest ex21-test
  (is (= (ch2/list 1 4 9 16 25 36 49)
         (ch2/square-list-recursive (ch2/list 1 2 3 4 5 6 7))))

  (is (= (ch2/list 1 4 9 16 25 36 49)
         (ch2/square-list-higher-order (ch2/list 1 2 3 4 5 6 7)))))

(deftest ex22-test
  ;; The last value cons'd to the answer list is the first value in the
  ;; answer list, thus 5 is the last number to be squared, and thus the
  ;; first number on the answer list. This property, applied recursively,
  ;; results in a reversed answer list.
  (is (= (ch2/list 25 16 9 4 1)
         (ch2/square-list-backwards (ch2/list 1 2 3 4 5))))

  ;; The structure returned is not a flat list, but a hierarchy of
  ;; pairs. The hierarchy where each left cell is a value and each right
  ;; cell is another pair or nil is called a "list", but this is not
  ;; the same as the dual hierarchy, where each right cell is a value
  ;; and each left cell is a pair or nil. Reversing the arguments to
  ;; cons does not reverse the list, it produces this dual hierarchy.
  (is (= (ch2/cons (ch2/cons (ch2/cons (ch2/cons (ch2/cons nil 1) 4) 9) 16) 25)
         (ch2/square-list-inside-out (ch2/list 1 2 3 4 5)))))

(deftest ex23-test
  (is (= "57\n321\n88\n"
         (with-out-str
           (ch2/for-each println (ch2/list 57 321 88)))))

  (is (= nil (ch2/for-each identity (ch2/list 57 321 88)))))

(deftest ex24-test
  ;; Interpreter:
  ;;    (1 (2 (3 4)))
  ;; Box & Whisker:
  ;;    | 1 |->|/|x|
  ;;           /
  ;;        | 2 |->|/|x|
  ;;               /
  ;;            | 3 |->| 4 |x|
  ;; Tree:
  ;;        .
  ;;       / \
  ;;      1   .
  ;;         /
  ;;        .
  ;;       / \
  ;;      2   .
  ;;         /
  ;;        .
  ;;       / \
  ;;      3   .
  ;;         /
  ;;        4
  (is (= (ch2/cons 1
                   (ch2/cons
                    (ch2/cons 2
                              (ch2/cons (ch2/cons 3 (ch2/cons 4 nil))
                                        nil))
                    nil))
         (ch2/list 1 (ch2/list 2 (ch2/list 3 4))))))

(deftest ex25-test
  (is (= 7
         (ch2/car
          (ch2/cdr
           (ch2/car
            (ch2/cdr
             (ch2/cdr (ch2/list 1 3 (ch2/list 5 7) 9))))))))

  (is (= 7 (ch2/car (ch2/car (ch2/list (ch2/list 7))))))

  (is (= 7
         (ch2/car
          (ch2/cdr
           (ch2/car
            (ch2/cdr
             (ch2/car
              (ch2/cdr
               (ch2/car
                (ch2/cdr
                 (ch2/car
                  (ch2/cdr
                   (ch2/car
                    (ch2/cdr
                     (ch2/list
                      1
                      (ch2/list
                       2
                       (ch2/list
                        3
                        (ch2/list
                         4
                         (ch2/list
                          5
                          (ch2/list 6 7)))))))))))))))))))))

(deftest ex26-test
  (let [x (ch2/list 1 2 3)
        y (ch2/list 4 5 6)]
    (is (= "(1 2 3 4 5 6)\n"
           (with-out-str (pp/pprint (ch2/append x y)))))

    (is (= "((1 2 3) 4 5 6)\n"
           (with-out-str (pp/pprint (ch2/cons x y)))))

    (is (= "((1 2 3) (4 5 6))\n"
           (with-out-str (pp/pprint (ch2/list x y)))))))

(deftest ex27-test
  (let [x (ch2/list (ch2/list 1 2) (ch2/list 3 4))]
    (is (= "((1 2) (3 4))\n"
           (with-out-str (pp/pprint x))))

    (is (= "((3 4) (1 2))\n"
           (with-out-str (pp/pprint (ch2/reverse x)))))

    (is (= "((4 3) (2 1))\n"
           (with-out-str (pp/pprint (ch2/deep-reverse x)))))))

(deftest ex28-test
  (let [x (ch2/list (ch2/list 1 2) (ch2/list 3 4))]
    (is (= "(1 2 3 4)\n"
           (with-out-str (pp/pprint (ch2/fringe x)))))

    (is (= "(1 2 3 4 1 2 3 4)\n"
           (with-out-str (pp/pprint (ch2/fringe (ch2/list x x))))))))

(deftest ex29a-test
  (let [left (ch2/make-branch 1 2)
        right (ch2/make-branch 3 4)
        m (ch2/make-mobile left right)]
    (is (= left (ch2/left-branch m)))

    (is (= right (ch2/right-branch m)))

    (is (= 1 (ch2/branch-length left)))

    (is (= 3 (ch2/branch-length right)))

    (is (= 2 (ch2/branch-structure left)))

    (is (= 4 (ch2/branch-structure right)))))

(deftest ex29b-test
  (is (= 0 (ch2/total-weight nil)))

  (is (= 3 (ch2/total-weight
            (ch2/make-mobile
             (ch2/make-branch 1 1)
             (ch2/make-branch 2 2)))))

  (is (= 10 (ch2/total-weight
             (ch2/make-mobile
              (ch2/make-branch 1 1)
              (ch2/make-branch
               2
               (ch2/make-mobile
                (ch2/make-branch
                 3
                 (ch2/make-mobile
                  (ch2/make-branch 2 2)
                  (ch2/make-branch 3 3)))
                (ch2/make-branch 4 4))))))))

(deftest ex29c-test
  (is (ch2/balanced? nil))

  (is (ch2/balanced?
       (ch2/make-mobile
        (ch2/make-branch 1 2)
        (ch2/make-branch 2 1))))

  (is (ch2/balanced?
       (ch2/make-mobile
        (ch2/make-branch 1 (ch2/make-mobile
                            (ch2/make-branch 1 1)
                            (ch2/make-branch 1 1)))
        (ch2/make-branch 2 1))))

  (is (ch2/balanced?
       (ch2/make-mobile
        (ch2/make-branch 10 (ch2/make-mobile
                             (ch2/make-branch 6 2)
                             (ch2/make-branch 3 4)))
        (ch2/make-branch 20 3)))))

(deftest ex29d-test
  ;; The accessors for right branches and branch structures must be
  ;; updated, but the other accessors still work. This also means the
  ;; total-weight, torque, and balanced? functions would either need
  ;; to be updated to use the new accessors, or written more generally
  ;; to accept the accessors they should use, or to use accessors that
  ;; dispatch properly based on the type of input. A Clojure protocol
  ;; would be the easiest approach here, where each mobile is a
  ;; different record implementing the same Protocol.
  (let [left (ch2/make-branch2 1 2)
        right (ch2/make-branch2 3 4)
        m (ch2/make-mobile2 left right)]
    (is (= left (ch2/left-branch m)))

    (is (= right (ch2/right-branch2 m)))

    (is (= 1 (ch2/branch-length left)))

    (is (= 3 (ch2/branch-length right)))

    (is (= 2 (ch2/branch-structure2 left)))

    (is (= 4 (ch2/branch-structure2 right)))))

(deftest ex30-test
  (is (= (ch2/list 1
                   (ch2/list 4 (ch2/list 9 16) 25)
                   (ch2/list 36 49))
         (ch2/square-tree
          (ch2/list 1
                    (ch2/list 2 (ch2/list 3 4) 5)
                    (ch2/list 6 7))))))

(deftest ex31-test
  (is (= (ch2/square-tree
          (ch2/list 1
                    (ch2/list 2 (ch2/list 3 4) 5)
                    (ch2/list 6 7)))
         (ch2/tree-map
          square
          (ch2/list 1
                    (ch2/list 2 (ch2/list 3 4) 5)
                    (ch2/list 6 7))))))

(deftest ex32-test
  (is (= (ch2/list (ch2/list nil)
                   (ch2/list 3)
                   (ch2/list 2)
                   (ch2/list 2 3)
                   (ch2/list 1)
                   (ch2/list 1 3)
                   (ch2/list 1 2)
                   (ch2/list 1 2 3))
         (ch2/subsets (ch2/list 1 2 3)))))

(deftest filter-test
  (is (= (ch2/list 1 3 5) (ch2/filter odd? (ch2/list 1 2 3 4 5)))))

(deftest accumulate-test
  (is (= 15 (ch2/accumulate + 0 (ch2/list 1 2 3 4 5))))

  (is (= 120 (ch2/accumulate * 1 (ch2/list 1 2 3 4 5))))

  (is (= (ch2/list 1 2 3 4 5)
         (ch2/accumulate ch2/cons nil (ch2/list 1 2 3 4 5)))))

(deftest enumerate-interval-test
  (is (= (ch2/list 2 3 4 5 6 7)
         (ch2/enumerate-interval 2 7))))

(deftest enumerate-tree-test
  (is (= (ch2/list 1 2 3 4 5)
         (ch2/enumerate-tree (ch2/list 1
                                       (ch2/list 2
                                                 (ch2/list 3 4))
                                       5)))))

(deftest list-fib-squares-test
  (is (= (ch2/list 0 1 1 4 9 25 64 169 441 1156 3025)
         (ch2/list-fib-squares 10))))

(deftest product-of-squares-of-odd-elements-test
  (is (= 225
         (ch2/product-of-squares-of-odd-elements
          (ch2/list 1 2 3 4 5)))))

(deftest ex33-test
  (testing 'sicp.ch2/map
    (is (= (ch2/list 1 4 9 16 25 36 49)
           (ch2/map square (ch2/enumerate-interval 1 7))))

    (is (= (ch2/list 10 200 3000)
           (ch2/map * (ch2/list 1 2 3) (ch2/list 10 100 1000))))

    (is (= (ch2/list 11 22 33)
           (ch2/map + (ch2/list 1 2 3) (ch2/list 10 20 30)))))

  (testing 'sicp.ch2/append
    (is (= (ch2/list 1 2 3 4 5 6)
           (ch2/append (ch2/list 1 2 3)
                       (ch2/list 4 5 6)))))

  (testing 'sicp.ch2/length
    (is (= 0 (ch2/length (ch2/list))))
    (is (= 3 (ch2/length (ch2/list 1 2 3))))
    (is (= 4 (ch2/length (ch2/list 1 2 3 4))))))

(deftest ex34-test
  (is (= (+ 1 (* 3 2) (* 5 8) 32)
         (ch2/horner-eval 2 (ch2/list 1 3 0 5 0 1)))))

(deftest ex35-test
  (is (= 0 (ch2/count-leaves (ch2/list))))

  (is (= 2 (ch2/count-leaves (ch2/list 1 2))))

  (is (= 4 (ch2/count-leaves (ch2/list (ch2/list 1 2)
                                       (ch2/list 3 4)))))

  (is (= 5 (ch2/count-leaves (ch2/list 0
                                       (ch2/list 1 2)
                                       (ch2/list 3 4)))))

  (is (= 7 (ch2/count-leaves (ch2/list 0
                                       (ch2/list 1
                                                 (ch2/list 2 3))
                                       (ch2/list 4
                                                 (ch2/list 5 6)))))))

(deftest ex36-test
  (is (= (ch2/list 22 26 30)
         (ch2/accumulate-n + 0 (ch2/list (ch2/list 1 2 3)
                                         (ch2/list 4 5 6)
                                         (ch2/list 7 8 9)
                                         (ch2/list 10 11 12))))))

(deftest ex37-test
  (let [m (ch2/list (ch2/list 1 2 3 4)
                    (ch2/list 4 5 6 6)
                    (ch2/list 6 7 8 9))]
    (testing 'sicp.ch2/dot-product
      (is (= 0
             (ch2/dot-product (ch2/list 1 2 3 4 5)
                              (ch2/list 0 0 0 0 0))))

      (is (= 10
             (ch2/dot-product (ch2/list 1 2 3)
                              (ch2/list 3 2 1)))))

    (testing 'sicp.ch2/matrix-*-vector
      (is (= (ch2/list 0 0 0)
             (ch2/matrix-*-vector m
                                  (ch2/list 0
                                            0
                                            0
                                            0))))

      (is (= (ch2/list 1 4 6)
             (ch2/matrix-*-vector m
                                  (ch2/list 1
                                            0
                                            0
                                            0))))

      (is (= (ch2/list 2 5 7)
             (ch2/matrix-*-vector m
                                  (ch2/list 0
                                            1
                                            0
                                            0))))

      (is (= (ch2/list 3 6 8)
             (ch2/matrix-*-vector m
                                  (ch2/list 0
                                            0
                                            1
                                            0))))

      (is (= (ch2/list 4 6 9)
             (ch2/matrix-*-vector m
                                  (ch2/list 0
                                            0
                                            0
                                            1))))

      (is (= (ch2/list 10 21 30)
             (ch2/matrix-*-vector m
                                  (ch2/list 1
                                            1
                                            1
                                            1)))))

    (testing 'sicp.ch2/transpose
      (is (= (ch2/list (ch2/list 1 10)
                       (ch2/list 2 20)
                       (ch2/list 3 30))
             (ch2/transpose
              (ch2/list (ch2/list 1 2 3)
                        (ch2/list 10 20 30))))))

    (testing 'sicp.ch2/matrix-*-matrix
      (is (= m
             (ch2/matrix-*-matrix m (ch2/list (ch2/list 1 0 0 0)
                                              (ch2/list 0 1 0 0)
                                              (ch2/list 0 0 1 0)
                                              (ch2/list 0 0 0 1)))))

      (is (= (ch2/list (ch2/list -1 0)
                       (ch2/list 0 -1))
             (ch2/matrix-*-matrix (ch2/list (ch2/list 0 1)
                                            (ch2/list -1 0))
                                  (ch2/list (ch2/list 0 1)
                                            (ch2/list -1 0)))))

      (is (= (ch2/list (ch2/list 1 0)
                       (ch2/list 0 1))
             (ch2/matrix-*-matrix (ch2/list (ch2/list -1 0)
                                            (ch2/list 0 -1))
                                  (ch2/list (ch2/list -1 0)
                                            (ch2/list 0 -1)))))

      (is (= (ch2/list (ch2/list 10 7 4 1)
                       (ch2/list 26 19 12 5)
                       (ch2/list 42 31 20 9))
             (ch2/matrix-*-matrix (ch2/list (ch2/list 1 2)
                                            (ch2/list 3 4)
                                            (ch2/list 5 6))
                                  (ch2/list (ch2/list 6 5 4 3)
                                            (ch2/list 2 1 0 -1))))))))

(deftest ex38-test
  (is (= 3/2 (ch2/fold-right / 1 (ch2/list 1 2 3))))

  (is (= 1/6 (ch2/fold-left / 1 (ch2/list 1 2 3))))

  (is (= (ch2/list 1 (ch2/list 2 (ch2/list 3 (ch2/list))))
         (ch2/fold-right ch2/list nil (ch2/list 1 2 3))))

  (is (= (ch2/list (ch2/list (ch2/list (ch2/list) 1) 2) 3)
         (ch2/fold-left ch2/list nil (ch2/list 1 2 3))))

  ;; For fold-right & fold-left to produce the same results, the
  ;; combining op should be associative.
  (is (= (+ 1 2 3 4 5 6 7 8 9 10)
         (ch2/fold-right + 0 (ch2/enumerate-interval 1 10))
         (ch2/fold-left + 0 (ch2/enumerate-interval 1 10))))

  (is (= (* 1 2 3 4 5 6 7 8 9 10)
         (ch2/fold-right * 1 (ch2/enumerate-interval 1 10))
         (ch2/fold-left * 1 (ch2/enumerate-interval 1 10)))))

(deftest ex39-test
  (is (= (ch2/list 5 4 3 2 1)
         (ch2/reverse-via-foldr (ch2/list 1 2 3 4 5))))

  (is (= (ch2/list 5 4 3 2 1)
         (ch2/reverse-via-foldl (ch2/list 1 2 3 4 5)))))

(deftest ex40-test
  (testing 'sicp.ch2/prime-sum-pairs
    (is (= (ch2/list (ch2/list 2 1 3))
           (ch2/prime-sum-pairs 2)))

    (is (= (ch2/list (ch2/list 2 1 3) (ch2/list 3 2 5))
           (ch2/prime-sum-pairs 3))))

  (testing 'sicp.ch2/unique-pairs
    (is (= (ch2/list (ch2/list 2 1))
           (ch2/unique-pairs 2)))

    (is (= (ch2/list (ch2/list 2 1)
                     (ch2/list 3 1) (ch2/list 3 2))
           (ch2/unique-pairs 3)))

    (is (= (ch2/list (ch2/list 2 1)
                     (ch2/list 3 1) (ch2/list 3 2)
                     (ch2/list 4 1) (ch2/list 4 2) (ch2/list 4 3))
           (ch2/unique-pairs 4)))))

(deftest ex41-test
  (testing 'sicp.ch2/ordered-triples
    (is (= (ch2/list (ch2/list 3 2 1))
           (ch2/ordered-triples 3)))

    (is (= (ch2/list (ch2/list 3 2 1)
                     (ch2/list 4 2 1) (ch2/list 4 3 1) (ch2/list 4 3 2))
           (ch2/ordered-triples 4))))

  (testing 'sicp.ch2/ordered-triples-summing-to
    (is (= (ch2/list (ch2/list 3 2 1 6))
           (ch2/ordered-triples-summing-to 6 3)))

    (is (= (ch2/list (ch2/list 4 3 1 8))
           (ch2/ordered-triples-summing-to 8 4)))

    (is (= (ch2/list (ch2/list 4 3 1 8)
                     (ch2/list 5 2 1 8))
           (ch2/ordered-triples-summing-to 8 5)))))

(deftest ex42-test
  (testing 'sicp.ch2/queens
    (is (= (ch2/list (ch2/list (ch2/list 1 1)))
           (ch2/queens 1)))

    (is (= (ch2/list) (ch2/queens 2)))

    (is (= (ch2/list) (ch2/queens 3)))

    (is (= (ch2/list (ch2/list (ch2/list 3 4)
                               (ch2/list 1 3)
                               (ch2/list 4 2)
                               (ch2/list 2 1))
                     (ch2/list (ch2/list 2 4)
                               (ch2/list 4 3)
                               (ch2/list 1 2)
                               (ch2/list 3 1)))
           (ch2/queens 4)))

    (is (some? (ch2/filter (fn [positions]
                             (= positions
                                (ch2/list (ch2/list 6 8)
                                          (ch2/list 4 7)
                                          (ch2/list 1 6)
                                          (ch2/list 5 5)
                                          (ch2/list 8 4)
                                          (ch2/list 2 3)
                                          (ch2/list 7 2)
                                          (ch2/list 3 1))))
                           (ch2/queens 8))))))


;;; ex 2.43
;;; The procedure in 2.42 generates one recursive call each invocation,
;;; whereas the version below produces T=board-size recursive calls each
;;; iteration. Thus, in the former case, queen-cols will be invoked
;;; T + 1 times, i.e. the procedure runs in O(T) steps, whereas in the
;;; latter case, queen-cols will be invoked O(T^T) times -- when T=6,
;;; this results in 46656 invocations, each generating lists to be
;;; mapped & filtered.
#_(flatmap
   (fn [new-row]
     (map (fn [rest-of-queens]
            (adjoin-position new-row k rest-of-queens))
          (queen-cols (dec k))))
   (enumerate-interval 1 board-size))

(deftest add-vect-test
  (is (= (ch2/make-vect 5 5)
         (ch2/add-vect (ch2/make-vect 2 3)
                       (ch2/make-vect 3 2))))

  (is (= (ch2/make-vect 0 0)
         (ch2/add-vect (ch2/make-vect 1 1)
                       (ch2/make-vect -1 -1)))))

(deftest scale-vect-test
  (is (= (ch2/make-vect 0 0)
         (ch2/scale-vect 0 (ch2/make-vect 1 1))))

  (is (= (ch2/make-vect 5 10)
         (ch2/scale-vect 5 (ch2/make-vect 1 2)))))

(deftest sub-vect-test
  (is (= (ch2/make-vect -1 1)
         (ch2/sub-vect (ch2/make-vect 2 3)
                       (ch2/make-vect 3 2))))

  (is (= (ch2/make-vect 2 2)
         (ch2/sub-vect (ch2/make-vect 1 1)
                       (ch2/make-vect -1 -1)))))

(deftest make-frame-a-test
  (is (= 0 (ch2/origin-frame-a (ch2/make-frame-a 0 1 2))))
  (is (= 1 (ch2/edge1-frame-a (ch2/make-frame-a 0 1 2))))
  (is (= 2 (ch2/edge2-frame-a (ch2/make-frame-a 0 1 2))))

  (is (= 3 (ch2/origin-frame-a (ch2/make-frame-a 3 4 5))))
  (is (= 4 (ch2/edge1-frame-a (ch2/make-frame-a 3 4 5))))
  (is (= 5 (ch2/edge2-frame-a (ch2/make-frame-a 3 4 5)))))

(deftest make-frame-b-test
  (is (= 0 (ch2/origin-frame-b (ch2/make-frame-b 0 1 2))))
  (is (= 1 (ch2/edge1-frame-b (ch2/make-frame-b 0 1 2))))
  (is (= 2 (ch2/edge2-frame-b (ch2/make-frame-b 0 1 2))))

  (is (= 3 (ch2/origin-frame-b (ch2/make-frame-b 3 4 5))))
  (is (= 4 (ch2/edge1-frame-b (ch2/make-frame-b 3 4 5))))
  (is (= 5 (ch2/edge2-frame-b (ch2/make-frame-b 3 4 5)))))

(deftest memq-test
  (is (false? (ch2/memq 'apple (ch2/scheme-quote (pear banana prune)))))
  (is (= (ch2/into-cons '(apple pear))
         (ch2/memq 'apple (ch2/scheme-quote (x (apple sauce) y apple pear))))))

(deftest scheme-equal?-test
  (is (ch2/scheme-equal? (ch2/scheme-quote (this is a list))
                         (ch2/scheme-quote (this is a list))))
  (is (not (ch2/scheme-equal? (ch2/scheme-quote (this is a list))
                              (ch2/scheme-quote (this (is a) list))))))

(deftest deriv-test
  (is (= 1
         (ch2/deriv (ch2/scheme-quote (+ x 3))
                    'x)))
  (is (= 'y
         (ch2/deriv (ch2/scheme-quote (* x y))
                    'x)))
  (is (= (ch2/scheme-quote (+ (* x y) (* y (+ x 3))))
         (ch2/deriv (ch2/scheme-quote (* x y (+ x 3)))
                    'x)))
  (is (zero? (ch2/deriv (ch2/scheme-quote (** x 0))
                        'x)))
  (is (= 1 (ch2/deriv (ch2/scheme-quote (** x 1))
                      'x)))
  (is (= (ch2/scheme-quote (* 2 x))
         (ch2/deriv (ch2/scheme-quote (** x 2))
                    'x)))
  (is (= (ch2/scheme-quote (* 3 (** x 2)))
         (ch2/deriv (ch2/scheme-quote (** x 3))
                    'x)))
  (is (= (ch2/scheme-quote (* n (** x (+ n -1))))
         (ch2/deriv (ch2/scheme-quote (** x n))
                    'x))))

(deftest exponentiation-test
  (is (ch2/exponentiation? (ch2/make-exponentiation 'a 'b)))
  (is (= 'a (ch2/base (ch2/make-exponentiation 'a 'b))))
  (is (= 'b (ch2/exponent (ch2/make-exponentiation 'a 'b)))))

(deftest infix-deriv-test
  (is (= 1
         (ch2/infix-deriv (ch2/scheme-quote (x + 3))
                          'x)))
  (is (= 'y
         (ch2/infix-deriv (ch2/scheme-quote (x * y))
                          'x)))
  (is (= (ch2/scheme-quote ((x * y) + (y * (x + 3))))
         (ch2/infix-deriv (ch2/scheme-quote ((x * y) * (x + 3)))
                          'x)))
  (is (zero? (ch2/infix-deriv (ch2/scheme-quote (x ** 0))
                              'x)))
  (is (= 1 (ch2/infix-deriv (ch2/scheme-quote (x ** 1))
                            'x)))
  (is (= (ch2/scheme-quote (2 * x))
         (ch2/infix-deriv (ch2/scheme-quote (x ** 2))
                          'x)))
  (is (= (ch2/scheme-quote (3 * (x ** 2)))
         (ch2/infix-deriv (ch2/scheme-quote (x ** 3))
                          'x)))
  (is (= (ch2/scheme-quote (n * (x ** (n + -1))))
         (ch2/infix-deriv (ch2/scheme-quote (x ** n))
                          'x)))
  (is (= 4
         (ch2/infix-deriv (ch2/scheme-quote (x + (3 * (x + (y + 2)))))
                          'x))))

(deftest lowest-precedence-op-in-test
  (is (= '+ (ch2/lowest-precendence-op-in
             (ch2/scheme-quote (x + 3 * (x + y + 2) ** 2)))))
  (is (= '* (ch2/lowest-precendence-op-in
             (ch2/scheme-quote (x ** 3 * (x + y + 2))))))
  (is (= '** (ch2/lowest-precendence-op-in
              (ch2/scheme-quote (x ** (x + y + 2)))))))

(deftest parenthesize-standard-infix-expression-test
  (is (= (ch2/scheme-quote (x + (3 * (x + y + 2) ** 2)))
         (ch2/parenthesize-standard-infix-expression
          (ch2/scheme-quote (x + 3 * (x + y + 2) ** 2)))))
  (is (= (ch2/scheme-quote (3 * ((x + y + 2) ** 2)))
         (ch2/parenthesize-standard-infix-expression
          (ch2/scheme-quote (3 * (x + y + 2) ** 2)))))
  (is (= (ch2/scheme-quote ((x + y + 2) ** (2 ** 3)))
         (ch2/parenthesize-standard-infix-expression
          (ch2/scheme-quote ((x + y + 2) ** 2 ** 3))))))

(deftest standard-infix-deriv-test
  (is (= 1
         (ch2/standard-infix-deriv
          (ch2/scheme-quote (x + 3))
          'x)))
  (is (= 'y
         (ch2/standard-infix-deriv
          (ch2/scheme-quote (x * y))
          'x)))
  (is (= (ch2/scheme-quote ((x * y) + (y * (x + 3))))
         (ch2/standard-infix-deriv
          (ch2/scheme-quote ((x * y) * (x + 3)))
          'x)))
  (is (zero? (ch2/standard-infix-deriv
              (ch2/scheme-quote (x ** 0))
              'x)))
  (is (= 1 (ch2/standard-infix-deriv
            (ch2/scheme-quote (x ** 1))
            'x)))
  (is (= (ch2/scheme-quote (2 * x))
         (ch2/standard-infix-deriv
          (ch2/scheme-quote (x ** 2))
          'x)))
  (is (= (ch2/scheme-quote (3 * (x ** 2)))
         (ch2/standard-infix-deriv
          (ch2/scheme-quote (x ** 3))
          'x)))
  (is (= (ch2/scheme-quote (n * (x ** (n + -1))))
         (ch2/standard-infix-deriv
          (ch2/scheme-quote (x ** n))
          'x)))
  (is (= 4
         (ch2/standard-infix-deriv
          (ch2/scheme-quote (x + (3 * (x + (y + 2)))))
          'x)))
  (is (= 4
         (ch2/standard-infix-deriv
          (ch2/scheme-quote (x + 3 * (x + y + 2)))
          'x)))
  (is (= (ch2/scheme-quote ((2 * x) + 3))
         (ch2/standard-infix-deriv
          (ch2/scheme-quote (x ** 2 + 3 * (x + y + 2)))
          'x))))
