# Makefile to compile a complex program composed of many libraries
# fixed using https://stackoverflow.com/questions/43555410/enable-openmp-support-in-clang-in-mac-os-x-sierra-mojave

### Variables for this project ###
# These should be the only ones that need to be modified
# The files that must be compiled, with a .o extension
PROGRAMS = 000-basic 00-first_omp 00-sum_numbers 02-sum_numbers_threads 05-sum_numbers_omp 


### Variables for the compilation rules ###
# These should work for most projects, but can be modified when necessary
# The compiler program to use
CC = /usr/local/opt/llvm/bin/clang#gcc

CPPFLAGS = -I/usr/local/opt/llvm/include -fopenmp
LDFLAGS = -L/usr/local/opt/llvm/lib
# Options to use when compiling object files
CFLAGS = -Wall -std=c99 -pedantic -fopenmp # -O2
# Options to use for the final linking process
# This one links the math library
LDLIBS = -lm -fopenmp

### The rules ###
# These should work for most projects without change
# Special variable meanings:
#   $@  = The name of the rule
#   $^  = All the requirements for the rule
#   $<  = The first required file of the rule

# Default rule
all: $(PROGRAMS)

# Rule to make the executable
$(PROGRAM): $(PROGRAM).o $(OBJECTS)
	$(CC) $(CPPFLAGS) $^ -o $@ $(LDFLAGS) $(LDLIBS)

# Rule to make the object files
%.o: %.c $(DEPENDS)
	$(CC) $< -c -o $@ $(CFLAGS)

# Clear the compiled files
clean:
	rm -rf *.o $(PROGRAMS)
	
# Indicate the rules that do not refer to a file
.PHONY: clean all
