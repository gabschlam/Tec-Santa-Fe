#|
    Example of using recursion to iterate
    Functions to mutiply, compute a power, get the factorial and the fibonacci number    
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

;-------------------------------
; Multiplication using only sums
;-------------------------------

; Regular recursion
(define (multiply a b)
    (if (not (zero? b))
        (+ a (multiply a (- b 1)))
        0))

; Entry point for tail recursion
(define (mult a b)
    (mult-tail a b 0))

; Tail recursive multiplication
(define (mult-tail a b result)
    (if (not (zero? b))
        (mult-tail a (sub1 b) (+ result a))
        result))

;-------------------------------
; Factorial function
;-------------------------------

; Regular recursion
(define (! n)
    (if (zero? n)
        1
        (* n (! (sub1 n)))))

; Entry point for tail recursion
(define (!-2 n)
    (!-tail n 1))

; Tail recursive factorial
(define (!-tail n res)
    (if (zero? n)
        res
        (!-tail (sub1 n) (* n res))))

;-------------------------------
; Fibonacci function
;-------------------------------

; Not possible to do tail recursion
(define (fibo n)
    (cond
        [(= n 0) 0]
        [(= n 1) 1]
        [(> n 1) (+ (fibo (- n 1)) (fibo (- n 2)))]))