#| 
    Basic example of using threads

    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (main)
    ; Main thread of my program
    (displayln "This is the main process")
    ; Launch new threads. Each defines the action to take in a lambda function
    (thread (lambda () (printf "Thread 1: ~a\n" (current-thread))))
    (thread (lambda () (printf "Thread 2: ~a\n" (current-thread))))
    (thread (lambda () (printf "Thread 3: ~a\n" (current-thread))))
    (thread (lambda () (printf "Thread 4: ~a\n" (current-thread))))
    ; This code belongs to the main thread
    (sleep 1)
    (displayln "Finishing main process"))