#|
    Test suite for functions to work with lists and recrusion
    
    Gabriel Schlam
    Programming Languages
|#

(require rackunit)
(require rackunit/text-ui)

(define positives-test
    (test-suite "Testing 'positives' function"
        (check-equal? (positives '()) '() "Empty list")
        (check-equal? (positives '(0)) '(0) "Zero in the list")
        (check-equal? (positives '(1)) '(1) "One positive element")
        (check-equal? (positives '(-1)) '() "One negative element")
        (check-equal? (positives '(1 2)) '(1 2) "Two positive elements")
        (check-equal? (positives '(-1 -2)) '() "Two negative elements")
        (check-equal? (positives '(1 -2 3 -4 5)) '(1 3 5) "Mixed list")))

(define invert-pairs-test
    (test-suite "Function invert-pairs"
        (check-equal? (invert-pairs '()) '() "Empty list")
        (check-equal? (invert-pairs '((a 1) (b 2) (c 3))) '((1 a) (2 b) (3 c)) "Non-empty list")
        (check-equal? (invert-pairs '((1 January) (2 February) (3 March))) '((January 1) (February 2) (March 3)) "Non-empty list")))

(define swapper-test
    (test-suite "Function swapper"
        (check-equal? (swapper '() 'a 'b) '() "Empty list")
        (check-equal? (swapper '(c) 'a 'b) '(c) "Single element not swapped")
        (check-equal? (swapper '(a) 'a 'b) '(b) "Swap for the first element")
        (check-equal? (swapper '(b) 'a 'b) '(a) "Swap for the second element")
        (check-equal? (swapper '(a b) 'a 'b) '(b a) "Two element list")
        (check-equal? (swapper '(a b a b) 'a 'b) '(b a b a) "Four element list")
        (check-equal? (swapper '(a b c a b d) 'a 'b) '(b a c b a d) "Six element list")
        (check-equal? (swapper '(this is a test) 'is 'a) '(this a is test) "A sentence")))

; Execute the tests
(run-tests positives-test)
(run-tests invert-pairs-test)
(run-tests swapper-test)