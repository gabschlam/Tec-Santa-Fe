#|
    Test for a function to duplicate all elements in a list

    Gabriel Schlam
    Programming Languages
|#

; Import the required modules
(require rackunit)
(require rackunit/text-ui)

;Define a new test suite
(define duplicate-test
    (test-suite "List item duplication function"
        (check-equal? (duplicate '()) '() "Empty list")
        (check-equal? (duplicate '(2)) '(2 2) "Simple element list")
        (check-equal? (duplicate '(2 3)) '(2 2 3 3) "Two element list")
        (check-equal? (duplicate '(2 3 4 5)) '(2 2 3 3 4 4 5 5) "Multiple element list")
    )
)

; Call the suite
(run-tests duplicate-test)