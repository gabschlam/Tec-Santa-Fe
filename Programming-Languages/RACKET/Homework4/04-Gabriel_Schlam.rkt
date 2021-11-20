#|
    Homework 04: Recursive Functions
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (sum-list data)
    (let loop
        ([data data]
         [result 0])
        (if (empty? data)
            result
            (loop
                ; data
                (cdr data) 
                ; result
                (+ result (car data))))))

(define (dot-product data1 data2)
    (let loop
        ([data1 data1]
         [data2 data2]
         [result 0])
        (if (empty? data1)
            result
            (loop
                ; data1
                (cdr data1)
                ; data2
                (cdr data2)
                ; result
                (+ result (* (car data1) (car data2)))))))

(define (len lst)
        (let loop
            ([lst lst]
            [count 0])
            (if (empty? lst)
                ; lst
                count
                ; count
                (loop (cdr lst) (+ count 1)))))

(define (mean data)
    (if (empty? data)
        0
        ; Call the function sum-list and len
        (/ (sum-list data) (len data))))

(define (standard-deviation data)
    (define (summation lst mean)
        (let loop
            ([lst lst]
            [result 0])
            (if (empty? lst)
                result
                (loop
                    ; lst
                    (cdr lst) 
                    ; result
                    (+ result (* (- (car lst) mean) (- (car lst) mean)))))))
    (if (empty? data)
        0
        ; Call the internal function summation, function mean and len
        (sqrt
        (/  (summation data (mean data)) (len data)))))