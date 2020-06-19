#|
    Examples of using lambda functions
    
    Gabriel Schlam
    Programming Languages
|#

#lang racket

(define (get-perimeters circles)
    ;(map (λ (r) (* pi r 2)) circles))
    (map (lambda (r) (* pi r 2)) circles))

;;; (define (next-day date)
;;;     (if (equal? (car date) ((lambda (m) (
;;;                              (case month
;;;                                 [(1 3 5 7 8 10 12) 31]
;;;                                 [(4 6 9 11) 30]
;;;                                 [(2) (if (year? y) 29 28)] )) (cadr date
;;; )))))
;;;     #t
;;;     #f)