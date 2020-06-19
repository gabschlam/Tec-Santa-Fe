#|
    Sending messages to threads, with semaphores  

    Gabriel Schlam
    Programming Languages
|#

#lang racket

;(define test-sem (make-semaphore 1))

(define (make-thread name test-sem)
    (thread (lambda ()
        ; The funciton prints 10 messages before finishing
        (let loop
            ([n 0])
            ; Actions in the loop
            (if (= n 100)
                (printf "Thread ~a finishing\n" name)
                (begin
                    (semaphore-wait test-sem)
                        (printf "Thread ~a got number ~a\n" name n)
                    (semaphore-post test-sem)
                    (loop (add1 n))))))))

(define (main number)
    (displayln "MAIN THREAD START")
    (define test-sem (make-semaphore 1))
    ; For making a number of threads instead with list of names
    ; With semaphore defined global
    ;(define threads (map make-thread (range number))) 
    ; With semaphore defined locally in main
    (define threads (map
                        (lambda (num) (make-thread num test-sem))
                        (range number)))  
    ; Wait until the threads finith their execution before continuing with the main thread
    (for-each thread-wait threads)
    
    (displayln "MAIN THREAD FINISH"))