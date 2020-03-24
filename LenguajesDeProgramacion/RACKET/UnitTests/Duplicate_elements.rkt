#|
    Function to duplicate all elements in a list

    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (duplicate data)
    (if (empty? data)
        empty
        (append
            (list (car data) (car data))
            (duplicate (cdr data))
        )
    )
)