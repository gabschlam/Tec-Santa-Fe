#|
    Simple function to read and write from files
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

; Write 10 lines of number into a file
(define (print-lines out)
    (let loop
        ([n 1])
        (if (> n 10)
            (displayln "Finished writing")
            (begin
                (displayln n out)
                (loop (add1 n))))))

; Read entire file
(define (multi-read in)
    (define line (read-line in))
    (if (eof-object? line)
        (display "Finished reading")
        (begin
            (displayln (string-split line ","))
            (multi-read in))))

#| COMENTED, ONE WAY OF DOING IT
    (define out (open-output-file "test.txt" #:exists 'truncate))
    ; #:exists 'truncate -> overwrites file
    ; #:exists 'append -> leaves content in file
    ; (displayln "Text that goes in the file" out)
    (print-lines out)
    (close-output-port out)

    (define in (open-input-file "test.txt"))
    ;(display (read-line in))
    (multi-read in)
    (close-input-port in)
|#

;(call-with-output-file "test.txt" #:exists 'truncate print-lines)

(call-with-input-file "../Threads/Data/GB_solardata_1569338152.csv" multi-read)