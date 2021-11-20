#|
    Programming Languages
    What about the pandemic?

    Daniel Roa - A01021960
    Gabriel Schlam - A01024122
    Sebastian Vives - A01025211
|#  
#lang racket

; Library used to read CSV files
(require 2htdp/batch-io)

; Function to print to the new file
(define (printer data out-file)
  (cond
    ; If no result received because of an error
    [(equal? data #f)
      (display "")]
    ; If no data found
    [(equal? 1 (length data))
      (display "No data found")]
    [else 
      ; Open the output directory and file
      (make-directory* "../Results")
      (call-with-output-file (format "../Results/covidResults_~a.csv" out-file) #:exists 'truncate
        (lambda (out)
          ; Outter loop for data
          (let loop
          ; Arguments
          ([data data])
          ; Loop actions
          (cond
            ; Exit the funtion when all data was printed to file
            [(empty? data)
              (printf "Printer finished\n")]
            ; Write the data to the file and repeat
            [else
              ; Actual line from data to write
              (define line (car data))
              ; Inner loop for line
              (let loop2
                ([row line])
                (if (empty? row)
                  (loop
                    (cdr data)) 
                    (begin
                      ; If string (usually country) has a comma, print it with " "
                      (if (and (string? (car row)) (string-contains? (car row) ","))
                        (display (format "\"~a\"" (car row)) out)
                        (display (car row) out))
                      ; If it is last element, print new line instead of comma
                      (if (equal? 1 (length row))
                        (display "\n" out)
                        (display "," out))
                    (loop2
                      (cdr row)))))]))))]))

; Functions that creates a list, based on the countries' name, by using the extracted data from the CSV
(define (countries->list allData)
    (define lst (cdr allData))
    (let loop
        ([result empty]
         [data lst])
        (cond
          ; When data is empty, get unique values
          [(empty? data)
           (remove-duplicates result)]
          [else
            (define line (car data))
            (loop 
              ; Get country from file
              (append result (list (cadddr line)))
              (cdr data))])))

; Function to detect if the index exists in the CSV file
(define (getIndex data element)
  (let loop 
    ([data (car data)]
     [index 0])
    (cond 
      [(empty? data) 
        #f]
      [(equal? (car data) element) 
        index]
      [else 
        (loop 
          (cdr data) 
          (add1 index))])))

; User inputs a country's name into the function and gets the information based on that country
(define (calcFuncCountry data country)
  (define lst (cdr data))
  (let loop 
    ([data lst]
     ; Write the header in the output list
     [result (list (car data))])
    (if (empty? data) 
        result
        (begin 
          (cond
            ; Checks if the inputted country appears on the data from file
            [(equal? country (car (cdr (cdr (cdr (car data))))))
              (loop 
                (cdr data)
                (append result (list (car data))))]
            [else 
              (loop 
                (cdr data)
                result)])))))

; User inputs a date into the function and gets the information based on the inputted date
; Function meant to handle the search of a specific date and prints the indexes' information
; alongside the country
(define (calcFuncDate data date)
  (define lst (cdr data))
  (let loop 
    ([data lst]
     ; Write the header in the output list
     [result (list (car data))])
    (if (empty? data) 
        result
        (begin 
          (cond
            ; Checks if the date exists in the list
            ; This same function is in charge of only reading the date and ignoring the time (string-split)
            [(equal? date (car (string-split (car (cdr (car data))) " "))) 
              (loop 
                (cdr data)
                (append result (list (car data))))]
            [else 
              (loop 
                (cdr data)
                result)])))))

; User inputs a date and a country's name into the function and gets the information based on the inputted data
; Function made to get the information for the specific date and country
(define (calcFuncDateCountry data date country)
  (define lst (cdr data))
  (let loop 
    ([data lst]
     ; Write the header in the output list
     [result (list (car data))])
    (if (empty? data) 
        result
        (begin 
          (cond
            ; Checks if the date exists in the list
            ; This same function is in charge of only reading the date and ignoring the time (string-split)
            [(equal? date (car (string-split (car (cdr (car data))) " "))) 
              (loop 
                (cdr data)
                ; IF to verify the country's existence in the CSV data
                (if (equal? country (car (cdr (cdr (cdr(car data))))))
                    (append result (list (car data)))
                    result))]
            [else 
              (loop 
                (cdr data)
                result)])))))

; User inputs a type of data into the function and gets the information based on the inputted data
; Function made to calculate the mean of the desired index
(define (calcFuncMean data type)
  ; Get list of countries from the data in file
  (define countries (countries->list data))
  ; Get index of the column from the type inputted
  (define index (getIndex data (string-titlecase type)))
  (cond 
    [(equal? index #f)
      ; Check if type is valid
      (displayln "Type invalid")
      ; Return false for not causing error in printer
      #f]
    [else
      (define lst (cdr data))
      (let loop
        ([countries countries]
          ; Write the header in the output list
         [result (list (list "Country" (string-titlecase type)))])
        (if (empty? countries) 
            result
            (loop
             (cdr countries)
             (append result
              (let loop2
                ([data lst]
                  [cont 0]
                  [res 0]) 
                (cond
                  ; If empty data, get the average and put it, with the country, in the list
                  [(empty? data)
                    (list (list (car countries) (/ res cont)))]
                  ; If actual country is the one reading in data
                  [(equal? (car countries) (car (cdr (cdr (cdr(car data))))))
                    (loop2 
                      (cdr data)
                      (+ cont 1.0)
                      (+ res (string->number (list-ref (car data) index))))]
                  [else 
                    (loop2 
                      (cdr data)
                      cont
                      res)]))))))]))

; User inputs a type of data and a country into the function and gets the information based on the inputted data
(define (calcFuncMeanCountry data type country)
  ; Get index of the column from the type inputted
  (define index (getIndex data (string-titlecase type)))
  (cond 
    [(equal? index #f)
      ; Check if type is valid
      (displayln "Type invalid")
      ; Return false for not causing error in printer
      #f]
    [else
      (define lst (cdr data))
      (let loop 
        ([data lst]
         ; Write the header in the output list
         [result (list (list "Country" (string-titlecase type)))]
         [cont 0]
         [res 0])
        (cond
          [(empty? data)
            (append result (list (list country (/ res cont))))]
          ; If country is equal to the one reading in data
          [(equal? country (car (cdr (cdr (cdr (car data))))))
            (loop
              (cdr data)
              result
              (+ cont 1.0)
              (+ res (string->number (list-ref (car data) index))))]
          [else 
            (loop 
              (cdr data)
              result
              cont
              res)]))]))

; Min-MAX function
; User inputs a type of data (string), wether he wants a minimum ("min") or maximum ("max") of the type of data and the amount (x)
(define (calcFuncMinMax data type min-max x)
  (define lst (cdr data))
  ; Get index of the column from the type inputted
  (define index (getIndex data (string-titlecase type)))
  (cond 
        [(equal? index #f)
        ; Check if type is valid
        (displayln "Type invalid")
        ; Return false for not causing error in printer
        #f]
        ; Check if type is valid
        [(or (equal? min-max "min") (equal? min-max "max"))
            (let loop 
                ([data lst]
                ; Write the header in the output list
                [result (list (car data))])
                (if (empty? data) 
                    result
                    (loop
                        (cdr data)
                        ;Check if user input was "min"
                        (if (equal? min-max "min");If yes, we only append results from the specified data type if they are higher than x.
                            (if (>= (string->number (list-ref (car data) index)) x)
                                (append result (list (car data)))
                                result)
                            ;If not min, it must be "max", so we only append results from the specified data type if they are lower than x.
                            (if (<= (string->number (list-ref (car data) index)) x)
                                (append result (list (car data)))
                                result)))))]
        ; Else, type not valid, because it is not "min" or "max"
        [else
          (displayln "Type invalid")
          ; Return false for not causing error in printer
          #f]))

; Min-MAX Country function
; User inputs a type of data (string), the country wether he wants a minimum ("min") or maximum ("max") of the type of data and the amount (x)
(define (calcFuncMinMaxCountry data type country min-max x)
  (define lst (cdr data))
  ; Get index of the column from the type inputted
  (define index (getIndex data (string-titlecase type)))
  (cond 
      [(equal? index #f)
      ; Check if type is valid
      (displayln "Type invalid")
      ; Return false for not causing error in printer
      #f]
      ; Check if type is valid
      [(or (equal? min-max "min") (equal? min-max "max"))
          (let loop 
              ([data lst]
              ; Write the header in the output list
              [result (list (car data))])
              (if (empty? data) 
                  result
                  (loop
                      (cdr data)
                       ; Check if minimum or maximum is valid
                      (if (equal? min-max "min")
                          (if (>= (string->number (list-ref (car data) index)) x);If yes, we only append results from the specified data type if they are higher than x.
                              (if (equal? country (car (cdr (cdr (cdr(car data)))))) ;Here we check if the country from the data coincides with the country from the user input, if it does, we append the data to our result
                                  (append result (list (car data)))
                                  result)
                              result)
                          ;If not min, it must be "max", so we only append results from the specified data type if they are lower than x.
                          (if (<= (string->number (list-ref (car data) index)) x)
                              (if (equal? country (car (cdr (cdr (cdr(car data))))));Here we check if the country from the data coincides with the country from the user input, if it does, we append the data to our result
                                  (append result (list (car data)))
                                  result)
                              result)))))]
      ; Else, type not valid, because it is not "min" or "max"
      [else
        (displayln "Type invalid")
        ; Return false for not causing error in printer
        #f]))

;Date range function
(define (calcFuncDateRange data start end)
  (cond 
    ; Check if the range given is valid
    [(string>=? start end)
     (displayln "Invalid range, first date is higher or equal than second")
     ; Return false for not causing error in printer
     #f]
    [else 
        (define lst (cdr data))
        (let loop 
          ([data lst]
            ; Write the header in the output list
            [result (list (car data))]
            [p 0]); Our boolean variable, if it 1 it should append data to our list, if its 0 it shouldnt append it.
            (if (empty? data) 
                result
                (begin 
                (cond
                    ; Checks if the date exists in the list
                    ; This same function is in charge of only reading the date and ignoring the time
                    [(equal? start (car (string-split (car (cdr (car data))) " "))) ;We check if the start date the user input coincides with the data,
                    ;if yes, we append it to our list
                    (loop 
                        (cdr data)
                        (append result (list (car data)))
                        1)]
                    [(equal? end (car (string-split (car (cdr (car data))) " "))) ;We check if the end date the user input coincides with the data,
                    ;if yes, we append it to our list
                    (loop 
                        (cdr data)
                        (append result (list (car data)))
                        0)]
                    [else ; If start nor end dates match with the data, we can check for the boolean variable
                    (if (equal? p 1) ;If it is 1, we know we are in the range of the 2 dates, so we append the data to our list
                    (loop 
                        (cdr data)
                        (append result (list (car data)))
                        p)
                    ;Else... the boolean is 0, which means we are no longe rin the range of dates, so we dont append anything.
                    (loop 
                        (cdr data)
                        result
                        p))]))))]))

;Date range function country
(define (calcFuncDateRangeCountry data start end country)
  (cond 
    ; Check if the range given is valid
    [(string>=? start end)
     (displayln "Invalid range, first date is higher than second")
     ; Return false for not causing error in printer
     #f]
    [else 
      (define lst (cdr data))
      (let loop 
        ([data lst]
        ; Write the header in the output list
        [result (list (car data))])
        (if (empty? data) 
          result
          (begin 
            (cond
              ; Checks if the date exists in the list, by checking the range at once,
              ; comparing the strings start and end with the current date from file.
              ; This same function is in charge of only reading the date and ignoring the time
              [(and (string<=? start (car (string-split (car (cdr (car data))) " "))) (string>=? end (car (string-split (car (cdr (car data))) " "))));We check if the start date the user input coincides with the data,
              (if (equal? country (car (cdr (cdr (cdr(car data)))))) ;Here we check if the country from the data coincides with the country from the user input, if it does, we append the data to our result
                (loop 
                  (cdr data)
                  (append result (list (car data))))
                ;Else...
                (loop 
                  (cdr data)
                  result))]
              [else
                (loop 
                  (cdr data)
                  result)]))))]))

; Function for reading CSV file
(define (readFile in-file)
  (read-csv-file in-file))

; Functions meant to call the operation and, afterwards, print the outputs to their respective CSV files
(define (funcCountry in-file country)
  (define result (calcFuncCountry (readFile in-file) country))
  (printer result (format "Country_~a" country)))

(define (funcDate in-file date)
  (define result (calcFuncDate (readFile in-file) date))
  ; string-replace for printing in out-file the date
  (printer result (format "Date_~a" (string-replace date "/" "-"))))

(define (funcDateCountry in-file date country)
  (define result (calcFuncDateCountry (readFile in-file) date country))
  ; string-replace for printing in out-file the date
  (printer result (format "Date_~a_~a" (string-replace date "/" "-") country)))

(define (funcMean in-file type)
  (define result (calcFuncMean (readFile in-file) type))
  (printer result (format "Mean_~a" type)))

(define (funcMeanCountry in-file type country)
  (define result (calcFuncMeanCountry (readFile in-file) type country))
  (printer result (format "Mean_~a_~a" type country)))

(define (funcMinMax in-file type min-max x)
  (define result (calcFuncMinMax (readFile in-file) type min-max x))
  (printer result(format "~a_~a_~a" (string-titlecase min-max) x type)))

(define (funcMinMaxCountry in-file type country min-max x)
  (define result (calcFuncMinMaxCountry (readFile in-file) type country min-max x))
  (printer result(format "~a_~a_~a_~a" (string-titlecase min-max) x type country)))

(define (funcDateRange in-file start end)
  (define result (calcFuncDateRange (readFile in-file) start end))
  ; string-replace for printing in out-file the date
  (printer result (format "Date_~a_~a" (string-replace start "/" "-") (string-replace end "/" "-"))))

(define (funcDateRangeCountry in-file start end country)
  (define result (calcFuncDateRangeCountry (readFile in-file) start end country))
  ; string-replace for printing in out-file the date
  (printer result (format "Date_~a_~a_~a" (string-replace start "/" "-") (string-replace end "/" "-") country)))

