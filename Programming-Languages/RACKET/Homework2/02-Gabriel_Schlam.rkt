#|
    Homework 02: Quadratic function
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

; Function for getting the square root
(define (raiz a b c)
    (sqrt (- (* b b) (* 4.0 a c))))

; Function for getting the quadratic function
(define (quadratic a b c)
    (if (= a 0) ; If a = 0
        ; true
        (/ (- c) b)
        ; false
        (list 
            (/ (+ (- b) (raiz a b c))(* 2.0 a)) 
            (/ (- (- b) (raiz a b c))(* 2.0 a))
        )
    )
)