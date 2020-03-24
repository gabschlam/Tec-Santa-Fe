#|
    Defining new functions
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (get-pi)
    (* 4 (atan 1)))

(define (add-numbers a b) 
    (+ a b))

(define (C-F temp)
    (+ (* temp 9/5) 32))

(define (F-C temp)
    (* (- temp 32.0) 5/9))

; Functions to get the area of triangle
; s = (a + b + c) / 2
; area = sqrt( s (s-a) (s-b) (s-c) )
(define (get-s a b c)
    (/ (+ a b c) 2))

(define (area a b c)
    (sqrt (* (get-s a b c) 
             (- (get-s a b c) a) 
             (- (get-s a b c) b) 
             (- (get-s a b c) c))))