#|
    Examples of list comprehensions
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (get-perimeters circles)
    ;(map (λ (r) (* pi r 2)) circles))
    ;(map (lambda (r) (* pi r 2)) circles))
    ; Using list comprehension instead of map
    (for/list ([r circles]
        ; Condition to fileter only the positive values
        #:when (> r 0))
        (* pi r 2)))

; Another example of list comprehensions
(define (num-days)
    (for/list ([n (in-naturals)]
               [day '(Monday Tuesday Wednesday Thursday)])
        (cons (add1 n) day)))