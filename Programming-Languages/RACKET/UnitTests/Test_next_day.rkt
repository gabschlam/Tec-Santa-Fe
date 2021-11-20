#|
    Tests for the function 'next-day'

    Gabriel Schlam
    Programming Languages
|#

; Import the required modules
(require rackunit)
(require rackunit/text-ui)

; NORMAL TESTS
;(test-begin
;    (check-equal? (next-day '(1 1 1999)) '(2 1 1999) "First day of the year")
;    (check-equal? (next-day '(28 2 2000)) '(29 2 2000) "Leap year")
;    (check-equal? (next-day '(28 2 2100)) '(1 3 2100) "Not Leap year")
;    (check-equal? (next-day '(31 12 1999)) '(1 1 2000) "Last day of year")
;)

; Define a new test suite
(define next-day-test
    (test-suite "Next day function"
        (check-equal? (next-day '(1 1 1999)) '(2 1 1999) "First day of the year")
        (check-equal? (next-day '(28 2 2000)) '(29 2 2000) "Leap year")
        (check-equal? (next-day '(28 2 2100)) '(1 3 2100) "Non Leap year % 100")
        (check-equal? (next-day '(31 12 1999)) '(1 1 2000) "Last day of the year")))

; Call the suite
(run-tests next-day-test)