#|
    Calculate number that is divisible by every positive integer from 1 to n WITH THREADS AND CHANNELS

    Gabriel Schlam
    Programming Languages
|#

#lang racket

; Create a channel to comunicate the threads
(define in-channel (make-channel))
(define out-channel (make-channel))

; Thread to print to the screen
(define printer
    (thread (lambda ()
        (let loop
            ; Loop arguments
            ()
            ; Loop actions
            (define msg (channel-get out-channel))
            (printf msg)
            (loop)))))

(define (make-thread name)
    (thread (lambda ()
        ; The funciton prints 10 messages before finishing
        (let loop
            ; No arguments to the loop
            ()
            ; Actions in the loop
            (define msg (channel-get in-channel))
            (cond
                [(equal? msg 'end)
                 (printf "Thread ~a finishing\n" name)]
                [(number? msg)
                 (channel-put out-channel (format "Thread ~a got number ~a \t| result ~a\n" name msg (divisible-up-to msg)))
                 (loop)])))))

; Create a list of random numbers terminated with as many 'end as number of threads
(define (make-list n-threads n-items limit)
    (let loop
        ([n n-items]
        [result empty])
        (if (zero? n)
        ; Inner loop to add the 'end to the list
            (let loop2
                ([t n-threads]
                [final result])
                (if (zero? t)
                    final
                    (loop2
                        (sub1 t)
                        (append final '(end)))))
            (loop
                (sub1 n)
                (cons (add1 (random limit)) result)))))

(define (main n-threads n-items)
    (displayln "MAIN THREAD START")
    ; List of message to send to the threads
    (define data (make-list n-threads n-items 25))
    (displayln data)
    ; For making a number of threads instead with list of names
    (define threads (map make-thread (range n-threads)))    
    ; Send each item in the data to each thread
    (for ([item data])
         (channel-put in-channel item))
    ; Wait until the threads finith their execution before continuing with the main thread
    (for-each thread-wait threads)
    
    (displayln "MAIN THREAD FINISH"))


; Find the smallest integer divisible by all numbers from 1 t n
(define (divisible-up-to n)
    (let loop
        ([n n]
        ; First candidate is n * (n - 1)
        ; This gives the result immediately for n < 5
        [test (* n (- n 1))])
        (if (divisible? n test)
            test
            ; Recursive call, increment the candidate by n,
            ;  since it must be a multiple of it
            (loop n (+ test n)))))


; Check that a test number is divisible by each integer from n to 1
(define (divisible? n test)
    (if (= n 1)
        #t
        (and (= 0 (remainder test n)) (divisible? (- n 1) test))))