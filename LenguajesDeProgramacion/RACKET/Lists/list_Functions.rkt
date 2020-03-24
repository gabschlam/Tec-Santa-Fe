#|
    Basic functions on lists
    Recreation of the existing functions in Racket
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

; Regular recursion
; (define (len lst)
;     (if (empty? lst)
;         0
;         (+ 1 (len (cdr lst)))
;     )
; )

; Tail recursion
;(define (len lst)
;    (len-tail lst 0))
;
;(define (len-tail lst count)
;    (if (empty? lst)
;        count
;        (len-tail (cdr lst) (+ count 1))
;    )
;)

;(define (len lst)
;    ; Local function for tail recursion
;    (define (len-tail lst count)
;        (if (empty? lst)
;            count
;            (len-tail (cdr lst) (+ count 1))
;        )
;    )
;    ; Call the internal function
;    (len-tail lst 0))

(define (len lst)
    (let loop
        ([lst lst]
         [count 0])
        (if (empty? lst)
            count
            (loop (cdr lst) (+ count 1))
        )
    )
)

(define (sumList lst)
    (let loop
        ([lst lst]
         [count 0])
        (if (empty? lst)
            count
            (loop (cdr lst) (+ count (car lst)))
        )
    )
)