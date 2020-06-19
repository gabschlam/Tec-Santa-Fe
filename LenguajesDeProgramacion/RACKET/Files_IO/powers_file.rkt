#|
    Read a text file and write a different one with the contents processed
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (lines->list in)
    (let loop
        ([result empty])
        (define line (read-line in))
        (if (eof-object? line)
            result
            (loop (append result (list (string->number line)))))))

(define (write-3-powers n out)
    (display n out)
    (display " " out)
    (display (* n n) out)
    (display " " out)
    (displayln (* n n n) out))

(define (make-powers-file in-file out-file)
    (define data (call-with-input-file in-file lines->list))
    (displayln data)
    (call-with-output-file out-file #:exists 'truncate
        (lambda (out)
            (let loop
                ([data data])
                (if (empty? data)
                    (display "Finished")
                    (begin
                        (write-3-powers (car data) out)
                        (loop (cdr data)))))))
)