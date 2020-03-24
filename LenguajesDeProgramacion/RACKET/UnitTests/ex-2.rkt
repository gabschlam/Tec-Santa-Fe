#|
    Functions to work with lists and recrusion
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

;(define (positives data)
;    "Filter the negative numbers out of the list"
;    (if (empty? data)
;        empty
;        (if (or (positive? (car data)) (zero? (car data)))
;            (cons (car data) (positives (cdr data)))
;            (positives (cdr data))
;        )
;    )
;)

; Tail recursion
(define (positives data)
    " Filter the negative numbers out of the list"
    (let loop
        ([data data]
         [result empty])
        (if (empty? data)
            result
            (if (or (positive? (car data)) (zero? (car data)))
                (loop 
                    (cdr data) 
                    (append result (list (car data))))
                (loop 
                    (cdr data) 
                    result)
            )            
        )  
    )
)

(define (invert-pairs data)
    (let loop
        ([data data]
         [result empty])
        (if (empty? data)
            result
            (loop
                (cdr data)
                (append result (list (list (cadar data) (caar data))))
            )
        )
    )
)
