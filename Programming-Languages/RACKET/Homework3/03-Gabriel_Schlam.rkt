#|
    Homework 03: Next day
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

; FUNCTION FOR KNOWING IF A YEAR IS LEAP OR NOT
(define (is-leap? year)
    (if (and 
            (zero? (modulo year 4)) 
            (or 
                (not (zero? (modulo year 100))) 
                (zero? (modulo year 400))))
        #t
        #f
    )
)

; FUNCTION FOR KNOWING THE NUMBER OF DAYS IN A MONTH
(define (num-days month year)
    (case month
        [(1 3 5 7 8 10 12) 31]
        ; Check if year is leap to return the correct number of days in February
        [(2) 
            (if (equal? (is-leap? year) #t)
                29
                28    
            )]
        [(4 6 9 11) 30]
    )
)

(define (next-day date)
    (let
        ([day (first date)]
        [month (second date)]
        [year (third date)])
        ; Check if it is last day of year (December 31)
        (if (and (= day 31) (= month 12))
            ; TRUE
            ; Return (1 1 next_year)
            (list 1 1 (+ year 1))
            ; FALSE
            ; Check if last day of month
            (if (zero? (- (num-days month year) day))
                ; TRUE
                ; Return (1 next_month year)
                (list 1 (+ month 1) year)
                ; FALSE
                ; Return (next_day month year)
                (list (+ day 1) month year)
            )
        )   
    )
)
