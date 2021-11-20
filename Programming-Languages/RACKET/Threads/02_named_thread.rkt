#|
    Assign a name to a thread, in order to interact with it
   
    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (main)
    (displayln "MAIN THREAD START")
    ; Create a thread and give it a name
    (define counter1 (thread (lambda ()
                              ; The funciton prints 10 messages before finishing
                             (let loop
                                ([n 1])
                                (if (> n 10)
                                    (displayln "Thread 1 finished")
                                    (begin
                                        (printf "T1: Message #~a\n" n)
                                        (sleep (random 2))
                                        (loop (add1 n))))))))
    (define counter2(thread (lambda ()
                             (let loop
                                ([n 1])
                                (if (> n 10)
                                    (displayln "Thread 2 finished")
                                    (begin
                                        (printf "T2: Message #~a\n" n)
                                        (sleep (random 2))
                                        (loop (add1 n))))))))
    (sleep 2)
    ; Terminate a thread prematurely
    ;(kill-thread counter)
    ; Wait until the threads finith their execution before continuing with the main thread
    (thread-wait counter1)
    (thread-wait counter2)
    (displayln "MAIN THREAD FINISH"))