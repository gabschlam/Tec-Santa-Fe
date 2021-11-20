#|
    Creation of multiple threads using a random generator
   
    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (make-thread name)
    (thread (lambda ()
        ; The funciton prints 10 messages before finishing
        (let loop
            ([n 1]
             [wait-sec (random 2)])
            (if (> n 10)
                (printf "Thread ~a finished\n" (+ name 1))
                (begin
                    (printf "Thread ~a: Message #~a, waiting ~a\n" (+ name 1) n wait-sec)
                    (sleep wait-sec)
                    (loop (add1 n) (random 2))))))))

;(define (main names)
(define (main number)
    (displayln "MAIN THREAD START")
    ; Create several thread, with names, in one go, storing them in a list
    ;(define threads (map make-thread names))
    
    ; For making a number of threads instead with list of names
    (define threads (map make-thread (range number)))    
    ; Wait until the threads finith their execution before continuing with the main thread
    (for-each thread-wait threads)
    (displayln "MAIN THREAD FINISH"))