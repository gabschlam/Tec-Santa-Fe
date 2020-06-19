#|
    Tests for funcitions using nested loops
    Gilberto Echeverria
    21/03/2020
|#

(require rackunit)
(require rackunit/text-ui)

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
(displayln "Testing: rotate-list")
(run-tests rotate-list-test)
(displayln "Testing: decimal->binary")
(run-tests decimal->binary-test)
(displayln "Testing: deep-reverse")
(run-tests deep-reverse-test)