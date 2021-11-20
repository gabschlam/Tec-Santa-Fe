#|
    Homework 05: More Recursion
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

; Function to rotate list with number of positions
(define (rotate-list n data)
    (let loop
        ([n n]
         [result data])
        (cond
            ; If data is empty, return result
            [(empty? data) 
                result]
            ; If n = 0, return result
            [(= n 0) 
                result]
            ; else
            [else
                (loop
                    ; If n is positive, n must be substracted 1, else, 1 must be substracted n
                    (if (positive? n)
                        (- n 1)
                        (- 1 n))
                    (append (cdr result) (list (car result))))])))

; Function to convert decimal into binary
(define (decimal->binary number)
    (let loop
        ([number number]
         [result empty])
        ; If the number is cero, return result
        (if (zero? number)
            result
            (loop
                ; number: integer number bewteen number divided by 2 (functionality of quotient)
                (quotient number 2)
                ; result
                (cons (modulo number 2) result)))))

; Function to reverse list, and every nested list
(define (deep-reverse data)
    (let loop
        ([data data]
         [result empty])
        (cond
            ; If data is empty, return result
            [(empty? data)
                result]
            ; If data is a list, continue loop
            [(list? data)
                (loop
                    (cdr data)
                    (cons (deep-reverse (car data)) result))]
            [else 
                data])))