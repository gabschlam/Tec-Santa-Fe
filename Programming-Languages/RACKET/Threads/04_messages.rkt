#|
    Sending messages to threads  

    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (make-thread name)
    (thread (lambda ()
        ; The funciton prints 10 messages before finishing
        (let loop
            ; No arguments to the loop
            ()
            ; Actions in the loop
            (define msg (thread-receive))
            (sleep (random 2))
            (cond
                [(equal? msg 'end)
                 (printf "Thread ~a finishing\n" name)]
                [(number? msg)
                 (printf "Thread ~a got number ~a\n" name msg)
                 (loop)]
                [(string? msg)
                 (printf "Thread ~a got string '~a'\n" name msg)
                 (loop)])))))

(define (main number)
    (displayln "MAIN THREAD START")
    ; List of message to send to the threads
    (define data '("hello" 1 2 3 56 "bye" 'end))
    ; For making a number of threads instead with list of names
    (define threads (map make-thread (range number)))    
    ; Send each item in the data to each thread
    (for ([item data])
        (for ([thread threads])
            (thread-send thread item)))
    ; Wait until the threads finith their execution before continuing with the main thread
    (for-each thread-wait threads)
    
    (displayln "MAIN THREAD FINISH"))