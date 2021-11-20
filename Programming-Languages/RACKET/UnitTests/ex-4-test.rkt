#|
    Tests for funcitions using nested loops
    Gilberto Echeverria
    21/03/2020
|#

(require rackunit)
(require rackunit/text-ui)

(define replicate-test
    (test-suite "Function replicate"
        (check-equal? (replicate '() 5) '() "Empty list")
        (check-equal? (replicate '(1 2 3) 0) '() "Zero replications")
        (check-equal? (replicate '(1 2 3) 1) '(1 2 3) "One replications")
        (check-equal? (replicate '(a) 3) '(a a a) "Multiple replications on a single element list")
        (check-equal? (replicate '(a x) 4) '(a a a a x x x x) "Multiple replications")))

(define expand-test
    (test-suite "Function expand"
        (check-equal? (expand '()) '() "Empty list")
        (check-equal? (expand '(a)) '(a) "Single element list")
        (check-equal? (expand '(a b)) '(a b b) "Two element list")
        (check-equal? (expand '(a b c)) '(a b b c c c) "Three element list")
        (check-equal? (expand '(a b c d)) '(a b b c c c d d d d) "Four element list")))

(define insert-test
    (test-suite "Function insert"
        (check-equal? (insert 4 '()) '(4) "Empty list")
        (check-equal? (insert 4 '(8)) '(4 8) "Single element list, insert at beginning")
        (check-equal? (insert 4 '(2)) '(2 4) "Single element list, insert at end")
        (check-equal? (insert 4 '(2 8)) '(2 4 8) "Two element list, insert at middle")
        (check-equal? (insert 2 '(5 7)) '(2 5 7) "Two element list, insert at start")
        (check-equal? (insert 9 '(5 7)) '(5 7 9) "Two element list, insert at end")
        (check-equal? (insert 8 '(2 6 7 9)) '(2 6 7 8 9) "Four element list, insert at middle")))

(define my-sort-test
    (test-suite "Function my-sort"
        (check-equal? (my-sort '()) '() "Empty list")
        (check-equal? (my-sort '(4)) '(4) "single element list")
        (check-equal? (my-sort '(3 7)) '(3 7) "Two element list, already sorted")
        (check-equal? (my-sort '(7 3)) '(3 7) "Two element list, inverted order")
        (check-equal? (my-sort '(2 5 7)) '(2 5 7) "Three element list, already sorted")
        (check-equal? (my-sort '(7 5 2)) '(2 5 7) "Three element list, inverted order")
        (check-equal? (my-sort '(5 7 2)) '(2 5 7) "Three element list, random order")
        (check-equal? (my-sort '(5 5 2 2)) '(2 2 5 5) "Four element list, repeated numbers")
        (check-equal? (my-sort '(8 5 3 6 2 4)) '(2 3 4 5 6 8) "Six element list")))

(define rotate-list-test
    (test-suite "Function rotate-list"
        (check-equal? (rotate-list 1 '()) '() "Empty list")
        (check-equal? (rotate-list 1 '(a)) '(a) "Single element list")
        (check-equal? (rotate-list 1 '(a b)) '(b a) "Two element list rotate 1")
        (check-equal? (rotate-list 0 '(a b c)) '(a b c) "Three element list rotate 0")
        (check-equal? (rotate-list 1 '(a b c)) '(b c a) "Three element list rotate 1")
        (check-equal? (rotate-list 2 '(a b c d)) '(c d a b) "Four element list rotate 2")
        (check-equal? (rotate-list -1 '(a b c d)) '(d a b c) "Four element list rotate -1")
        (check-equal? (rotate-list 4 '(a b c d)) '(a b c d) "Four element list rotate 4")
        (check-equal? (rotate-list 8 '(a b c d)) '(a b c d) "Four element list rotate 8")
        (check-equal? (rotate-list 7 '(a b c d)) '(d a b c) "Four element list rotate 7")
        (check-equal? (rotate-list -3 '(a b c d)) '(b c d a) "Four element list rotate -3")
        (check-equal? (rotate-list -7 '(a b c d)) '(b c d a) "Four element list rotate -7")))

(define decimal->binary-test
    (test-suite "Funciton decimal->binary"
        (check-equal? (decimal->binary 0) '() "Zero")
        (check-equal? (decimal->binary 1) '(1) "One")
        (check-equal? (decimal->binary 2) '(1 0) "Two")
        (check-equal? (decimal->binary 3) '(1 1) "Three")
        (check-equal? (decimal->binary 4) '(1 0 0) "Four")
        (check-equal? (decimal->binary 7) '(1 1 1) "Seven")
        (check-equal? (decimal->binary 25) '(1 1 0 0 1) "Twenty five")
        (check-equal? (decimal->binary 173) '(1 0 1 0 1 1 0 1) "173")))

(define deep-reverse-test
    (test-suite "Funciton deep-reverse"
        (check-equal? (deep-reverse '()) '() "Empty list")
        (check-equal? (deep-reverse '(a b)) '(b a) "Two element list")
        (check-equal? (deep-reverse '(a b c)) '(c b a) "Three element list")
        (check-equal? (deep-reverse '(a (b c d) 3)) '(3 (d c b) a) "Nested lists")
        (check-equal? (deep-reverse '((1 2) 3 (4 (5 6)))) '(((6 5) 4) 3 (2 1)) "Double nested lists")))

; Execute the tests
(displayln "Testing: replicate")
(run-tests replicate-test)
(displayln "Testing: expand")
(run-tests expand-test)
(displayln "Testing: insert")
(run-tests insert-test)
(displayln "Testing: my-sort")
(run-tests my-sort-test)
(displayln "Testing: rotate-list")
(run-tests rotate-list-test)
(displayln "Testing: decimal->binary")
(run-tests decimal->binary-test)
(displayln "Testing: deep-reverse")
(run-tests deep-reverse-test)