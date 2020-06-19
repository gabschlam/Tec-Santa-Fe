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
                    ;data
                    (cdr data)
                    ;result
                    (append result (list (car data))))
                (loop 
                    ;data
                    (cdr data) 
                    ;result
                    result)))))

(define (invert-pairs data)
    "Invert the order of the elements in the internal lists"
    (let loop
        ([data data]
         [result empty])
        (if (empty? data)
            result
            (loop
                ;data
                (cdr data)
                ;result
                (append result (list (list (cadar data) (caar data))))))))

(define (swapper data item1 item2)
    "Change every appearance of item1 in data for item2, and vice versa"
    (let loop
        ([data data]
         [result empty])
        (if (empty? data)
            result
            (loop
                ; data
                (cdr data)
                ; result
                (cond 
                    [(equal? (car data) item1)
                     (append result (list item2))]
                    [(equal? (car data) item2)
                     (append result (list item1))]
                    [else
                     (append result (list (car data)))])))))
