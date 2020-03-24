#|
    Testing the conditionals in Racket
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (compare a b)
    (if (< a b)
        ; true
        (printf "~a is smaller than ~a" a b)
        ; false
        (printf "~a is larger or equal than ~a" a b)))
        ; BEGIN, y las demas instrucciones si quieres hacer mas de una instruccion en el if

(define (average a b c)
    (/ (+ a b c) 3))

(define (calif c1 c2 c3)
    (if (< (average c1 c2 c3) 70)
        ;true
        (display "Reprobado")
        ;false
        (display "Aprobado")))

; Determining if a year is leap
; Using nested if's
(define (leap? year)
    (if (zero? (modulo year 4))
        (if (zero? (modulo year 100))
            (if (zero? (modulo year 400))
                #t  ; Divisible by 400
                #f) ; Divisible by 100
            #t) ; Divisible by 4
        #f))    ; Not divisible by 4

; A single if with logical operators
(define (is-leap? year)
    (if (and 
            (zero? (modulo year 4)) 
            (or 
                (not (zero? (modulo year 100))) 
                (zero? (modulo year 400))))
        #t
        #f))

; Create a list from a series of items
(define (create-list a b)
    (list a b))
    ;(list (+ a b) (- a b)))

; Function to find the largest of 3 numbers
(define (largest-of-3 n1 n2 n3)
    (if (>= n1 n2)
        (if (>= n1 n3)
            n1
            n3)
        (if (>= n2 n3)
            n2
            n3)))

; CASE
(define (opera operation a b)
    (case operation
        [("suma" "add") (+ a b)]
        [("resta" "substract") (- a b)]
        [("multiplica" "multiply") (* a b)]
        [("divide" "divide") (/ a b)]
        [else (begin 
                (display "Invalid operation\n")
                0)]
    ))

; Mes dias
(define (mes-dias mes)
    (case mes
        [("enero" "marzo" "mayo" "julio" "agosto" "octubre" "diciembre") 31]
        [("febrero") 28]
        [("abril" "junio" "septiembre" "noviembre") 30]
        [else (display "Invalid operation\n")]
    ))

; COND
(define (func x)
    (cond
        [(< x -1) (+ x 2)]
        [(and (>= x -1) (< x 0)) 1]
        [(>= x 0) (+ (- (sqrt x) ) 1)]
    ))

; LET - VARIABLES TEMPORALES
(define (quadratic a b c)
    (if (zero? a)
        ; true
        (list (- (/ c b)))
        ; false
        (let*
            ([d (- (sqrt b) (* 4 a c))]
            [2a (* a 2)]
            [root-d (sqrt d)])
            (list (/ (+ (- b) root-d) 2a) (/ (- (- b) root-d) 2a)))
        ))