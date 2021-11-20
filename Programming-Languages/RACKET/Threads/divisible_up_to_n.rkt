#|
    Find a number that is divisible by every positive integer from 1 to n
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

; Find the smallest integer divisible by all numbers from 1 t n
(define (divisible-up-to n)
    (let loop
        ([n n]
        ; First candidate is n * (n - 1)
        ; This gives the result immediately for n < 5
        [test (* n (- n 1))])
        (if (divisible? n test)
            test
            ; Recursive call, increment the candidate by n,
            ;  since it must be a multiple of it
            (loop n (+ test n)))))


; Check that a test number is divisible by each integer from n to 1
(define (divisible? n test)
    (if (= n 1)
        #t
        (and (= 0 (remainder test n)) (divisible? (- n 1) test))))