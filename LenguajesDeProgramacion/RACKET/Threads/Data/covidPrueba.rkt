#|
    Calclations from file

    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (countries->list in)
    ; Read the header
    (read-line in)
    (let loop
        ([result empty])
        (define line (read-line in))
        (cond
                [(eof-object? line)
                 (remove-duplicates result)]
                [else
                    (define data (string-split (string-trim line) "|"))
                    (loop (append result (list (cadddr data))))
                    ])))

; Create a channel to comunicate the threads
(define data-channel (make-channel))
(define out-channel (make-channel))

; Thread to print to the screen
(define printer
    (thread (lambda ()
        ; Open the output file
        (call-with-output-file "yearly_averages.csv" #:exists 'truncate
            (lambda (out)
            (let loop
                ; No arguments
                ()
                ; Loop actions
                (define msg (channel-get out-channel))
                (cond
                    ; Exit the funtion when receiving the message 'end
                    [(equal? msg 'end)
                     (printf "Printer thread finishing\n")]
                    ; Write the data on the channel to the file and repeat
                    [else 
                     (display msg out)
                     (loop)])))))))

(define (average data)
    (/ (foldl + 0.0 (map string->number data)) (length data)))

(define (make-thread name)
    (thread (lambda ()
        ; The funciton prints 10 messages before finishing
        (let loop
            ; No arguments to the loop
            ([n 0])
            ; Actions in the loop
            (define line (channel-get data-channel))
            (cond
                [(equal? line 'end)
                 (printf "Thread ~a finishing\n" name)]
                [else
                 (define data (string-split (string-trim line) ","))
                 ; Send a new string composed of the place name and the average
                 ;(if (equal? (cadr data) "Aberporth")
                   ; (begin
                       ; (printf "~a: ~a\n" n  (car ( cdr (cdr data))))
                       ; (channel-put out-channel (format "~a,~a\n" (cadddr data) (average (cdr (cdr (cdr (cdr (cdr data))))))))
                        (channel-put out-channel (format "~a,~a\n" n (car ( cdr (cdr data)))))
                        (loop (add1 n))
                    ;(loop))
                    ])))))

(define (main n-threads file-name)
    (call-with-input-file file-name 
        (lambda (in)
            ; Read the header
            (read-line in)
            ; Write the header in the output file
            (channel-put out-channel "Place,Average\n")
            ; Create the threads
            (define threads (map make-thread (range n-threads)))
            ; Iterate over the file
            (let loop
                ; No arguments to the loop
                ()
                ; Actions in the loop
                (define line (read-line in))
                (if (eof-object? line)
                    (printf "EOF reached\n")
                    ; Split the line into a list and send it to the threads
                    (begin
                        (channel-put data-channel line)
                        (loop))))
            ; Seand the end message to each thread
            (for ([i n-threads]) (channel-put data-channel 'end))
            ; Wait for threads
            (for-each thread-wait threads)))
    ; Let the printer thread finish
    (channel-put out-channel 'end)
    (printf "Done!\n"))

; (define (main in-file)
;     (define data (call-with-input-file in-file countries->list))
;     (displayln data))