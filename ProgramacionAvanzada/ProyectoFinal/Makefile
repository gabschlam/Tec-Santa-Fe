#####################################################
# Gabriel Schlam Huber
# Simon Metta Grego
# Isaac Harari Masri
#
# Makefile
#####################################################

normal: client server

# Server Program: Assigning libraries for Network and System, making executable
server: questionMaker.o
	g++ questionMaker.o -o server -lsfml-system -lsfml-network

# What the Server needs to compile
questionMaker.o: questionMaker.cpp
	g++ -c questionMaker.cpp -Wall

# Client Program: Assignin libraries for Graphics Windows and System, making executable
# the Client program needs itself and the GUI Library to work correctly
client: client.o gui_lib.o
	g++ client.o gui_lib.o -o client -lsfml-graphics -lsfml-window -lsfml-system -lsfml-network

# What the client needs to compile
client.o: client.cpp
	g++ -c client.cpp -Wall

# What the GUI needs to compile
gui_lib: gui_lib.cpp
	g++ -c gui_lib.cpp -Wall

# When cleaning remove .o files and executables
clean:
	rm *.o server client
	