#|
    Tests for other formulas using lists
    Since these tests compare numerical values,
    the test 'check-=' is used, specifying a tolerance

    Gilberto Echeverria
    20/03/2020
|#

(require rackunit)
(require rackunit/text-ui)

(define sum-list-test
    (test-suite "Function sum-list"
        (check-= (sum-list '()) 0 0 "Empty list")
        (check-= (sum-list '(3)) 3 0 "Single int")
        (check-= (sum-list '(3 7)) 10 0 "Two ints")
        (check-= (sum-list '(6 9 2)) 17 0 "Three ints")
        (check-= (sum-list '(8.1 5.6 9.3 7.4)) 30.4 0 "Four floats")))

(define dot-product-test
    (test-suite "Function dot-product"
        (check-= (dot-product '() '()) 0 0 "Empty vectors")
        (check-= (dot-product '(9) '(4)) 36 0 "Vectors size 1")
        (check-= (dot-product '(2 5) '(6 3)) 27 0 "Vectors size 2")
        (check-= (dot-product '(1 2 3) '(4 5 6)) 32 0 "Vectors size 3")
        (check-= (dot-product '(-37.2 41.5 22.3 12.5) '(14.8 -25.7 46.1 -62.3)) -1367.83 0.005 "Vectors size 4")))

(define mean-test
    (test-suite "Function average"
        (check-= (mean '()) 0 0 "Empty list")
        (check-= (mean '(6)) 6 0 "Single int")
        (check-= (mean '(8 3)) 5.5 0 "Two ints")
        (check-= (mean '(4 7 9 2 4 6 8)) 5.714285714285714 0.0005 "Multiple ints")
        (check-= (mean '(3.5 7.2 9.1 5.3 6.2 1.7 9.9)) 6.128571428571429 0.0005 "Multiple floats")))

(define standard-deviation-test
    (test-suite "Function standard-deviation"
        (check-= (standard-deviation '()) 0 0 "Empty list")
        (check-= (standard-deviation '(4 4)) 0.0 0 "Two equal values")
        (check-= (standard-deviation '(2 4)) 1.0 0.005 "Two ints")
        (check-= (standard-deviation '(2 9 4)) 2.943920288775949 0.005 "Three ints")
        (check-= (standard-deviation '(4 7 9 2 4 6 8)) 2.3123448651769496 0.005 "Multiple ints")
        (check-= (standard-deviation '(3.5 7.2 9.1 5.3 6.2 1.7 9.9)) 2.710147220942743 0.0005 "Multiple floats")))

(run-tests sum-list-test)
(run-tests dot-product-test)
(run-tests mean-test)
(run-tests standard-deviation-test)
