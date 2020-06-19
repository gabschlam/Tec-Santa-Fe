#|
    Series of examples #4
    Advanced loops in Racket    
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

#|  COMENTED...
    (define (multi-list item n)
        (let loop
            ([n n]
            [result empty])
            (if (zero? n)
                result
                (loop
                    (sub1 n)
                    (cons item result)))))
|#

(define (replicate data times)
    ; External loop
    (let loop
        ([data data]
         [result empty])
        (if (empty? data)
            result
            (loop
                (cdr data)
                (append 
                    result
                    ; Internal loop -> equivalent to multi-list function
                    (let loop2
                        ([n times]
                         [result2 empty])
                        (if (zero? n)
                            result2
                            (loop2
                                (sub1 n)
                                (cons (car data) result2)))))))))

(define (expand data)
    ; External loop
    (let loop
        ([data data]
         [times 1]
         [result empty])
        (if (empty? data)
            result
            (loop
                (cdr data)
                (add1 times)
                (append 
                    result
                    ; Internal loop
                    (let loop2
                        ([n times]
                         [result2 empty])
                        (if (zero? n)
                            result2
                            (loop2
                                (sub1 n)
                                (cons (car data) result2)))))))))

(define (insert item data)
    (let loop
        ([data data]
         [result empty])
        (cond
            [(empty? data) 
                (append result (list item))]
            [(> item (car data)) 
                (loop 
                    (cdr data)
                    (append result (list (car data))))]
            ; [else
            [(<= item (car data))
                (append result (list item) data)])))