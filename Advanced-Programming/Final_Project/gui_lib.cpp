/************************************************************
Gabriel Schlam Huber
Simon Metta Grego
Isaac Harari Masri

GUI Library
This libraru creates different windows depending on what does the 
client need to show
************************************************************/

//include the header
#include "gui_lib.h"

//libraries for displaying the diferent windows, handling graphics and the system
#include <SFML/Window.hpp>
#include <SFML/Graphics.hpp>
#include <SFML/OpenGL.hpp>
//standard libraries needed
#include <iostream>
#include <string>

//namespaces for SFML and iostream
using namespace sf;
using namespace std;

//creates a window where the user can write the IP address of the server they want to connect
string GUI::select_server_window()
{
	//creating window
	RenderWindow window;
	window.create(VideoMode(1500, 1000), "Select Server");
	window.setFramerateLimit(30);

	//variable to store the IP address
	string server_ip;

	//setting texture and sprote for the game logo
	Texture logo_texture;
	if(!logo_texture.loadFromFile("Assets/MLogoTransp.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite logo_sprite;
	logo_sprite.setTexture(logo_texture);
	logo_sprite.setScale(.5, .5);

	//setting texture and sprite for the next button
	Texture next_texture;
	if(!next_texture.loadFromFile("Assets/nextButton.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite next_sprite;
	next_sprite.setTexture(next_texture);
	next_sprite.setScale(.5, .5);

	//preparing arial font to use on texts
	Font font;
	if (!font.loadFromFile("Assets/arial.ttf"))
	{
		printf("Error while loading font\n");
	}

	//creating text to diplay
	Text displaytext;
	displaytext.setFont(font);
	displaytext.setCharacterSize(60);
	displaytext.setFillColor(Color::Black);
	displaytext.setString("Please Write de Server's IP Address");

	//creating text to display user input of IP address
	Text nametext;
	nametext.setFont(font);
	nametext.setCharacterSize(60);
	nametext.setFillColor(Color::Black);
	nametext.setString("IP: ");

	//loop to update the window
	while(window.isOpen())
	{
		Event event;

		//loop while there is any kind of event
		while(window.pollEvent(event))
		{
			//if user types input it will store it as the IP address
			if(event.type == Event::TextEntered)
			{
				server_ip = server_ip + static_cast<char>(event.text.unicode);
				nametext.setString("IP: " + server_ip);
			}

			//if user closes the window close safely
			if(event.type == Event::Closed)
			{
				window.close();
			}

			//next button, if it detects that the mouse is over the button sprite and detects a click, send the IP
			if(isSpriteHover(next_sprite.getGlobalBounds(), Vector2f(event.mouseButton.x, event.mouseButton.y)))
			{
				if((event.type == Event::MouseButtonReleased) && (event.mouseButton.button == Mouse::Left))
				{
					return server_ip;
				}
			}
		}

		//creating and displaying the window with all the sprites and texts positios
		window.clear(Color::White);
		logo_sprite.setPosition(Vector2f(200, 100));
		displaytext.setPosition(Vector2f(250, 550));
		nametext.setPosition(Vector2f(600, 650));
		next_sprite.setPosition(Vector2f(600, 800));

		//putting everything in the window
		window.draw(logo_sprite);
		window.draw(displaytext);
		window.draw(nametext);
		window.draw(next_sprite);
		window.display();
	}

	//if the user closes the window, send what has been stored
	return server_ip;
}

//function almost equal to select_server_window() but sends the players name instead
string GUI::set_name_window()
{
	//creating the window
	RenderWindow window;
	window.create(VideoMode(1500, 1000), "Enter you name");
	window.setFramerateLimit(30);
	
	//variable to store the players name
	string your_name;

	//loading logo...
	Texture logo_texture;
	if(!logo_texture.loadFromFile("Assets/MLogoTransp.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite logo_sprite;
	logo_sprite.setTexture(logo_texture);
	logo_sprite.setScale(.5, .5);

	//loading next button...
	Texture next_texture;
	if(!next_texture.loadFromFile("Assets/nextButton.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite next_sprite;
	next_sprite.setTexture(next_texture);
	next_sprite.setScale(.5, .5);

	//loading font
	Font font;
	if (!font.loadFromFile("Assets/arial.ttf"))
	{
		printf("Error while loading font\n");
	}

	//text to diplay
	Text displaytext;
	displaytext.setFont(font);
	displaytext.setCharacterSize(60);
	displaytext.setFillColor(Color::Black);
	displaytext.setString("Please Enter Your Name");

	//text to show user input
	Text nametext;
	nametext.setFont(font);
	nametext.setCharacterSize(60);
	nametext.setFillColor(Color::Black);
	nametext.setString("Name: ");

	//while the window is open, update
	while(window.isOpen())
	{
		Event event;

		//if any event is detected...
		while(window.pollEvent(event))
		{
			//store input as player's name
			if(event.type == Event::TextEntered)
			{
				your_name = your_name + static_cast<char>(event.text.unicode);
				nametext.setString("Name: " + your_name);
			}

			//close safely when user closes window
			if(event.type == Event::Closed)
			{
				window.close();
			}

			//if detects next button press, send players name
			if(isSpriteHover(next_sprite.getGlobalBounds(), Vector2f(event.mouseButton.x, event.mouseButton.y)))
			{
				if((event.type == Event::MouseButtonReleased) && (event.mouseButton.button == Mouse::Left))
				{
					return your_name;
				}
			}
		}
		//text and sprites positions
		window.clear(Color::White);
		logo_sprite.setPosition(Vector2f(200, 100));
		displaytext.setPosition(Vector2f(400, 550));
		nametext.setPosition(Vector2f(530, 650));
		next_sprite.setPosition(Vector2f(600, 800));

		//creating window
		window.draw(logo_sprite);
		window.draw(displaytext);
		window.draw(nametext);
		window.draw(next_sprite);
		window.display();
	}

	//if user closes window send what has been stored
	return your_name;
}

//Function to detect if the mouse is over an area for button presses
//needs area of the button as rectangle and mouse coordinations
bool GUI::isSpriteHover(FloatRect sprite, Vector2f mp)
{
	//if the mouse is over the rectangle send true;
	if(sprite.contains(mp))
	{
		return true;
	}
	else
	{
		return false;
	}
}

//Displays a succesfull connection window to let the player know a connection has been settled
void GUI::successfull_connection()
{
	//preparing the window
	RenderWindow window;
	window.create(VideoMode(500, 500), "Conection Succesfull");
	window.setFramerateLimit(30);

	//loading checkmark...
	Texture check_texture;
	if(!check_texture.loadFromFile("Assets/check.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite check_sprite;
	check_sprite.setTexture(check_texture);
	
	//loading font
	Font font;
	if (!font.loadFromFile("Assets/arial.ttf"))
	{
		printf("Error while loading font\n");
	}

	//text to show a succesfull connection
	Text displaytext;
	displaytext.setFont(font);
	displaytext.setCharacterSize(40);
	displaytext.setFillColor(Color::Black);
	displaytext.setString("Connection Succesfull");

	//starting a clock to close window automaticalli
	Clock clock;
	clock.restart();

	//setting a timer equal to the clock to use as int
	Time timer = clock.getElapsedTime();

	//updating window loop
	while(window.isOpen())
	{
		//if user closes window, close safely
		Event event;
		while(window.pollEvent(event))
		{
			if(event.type == Event::Closed)
			{
				window.close();
			}
		}

		//if 5 seconds have passed, close window
		if(timer.asSeconds() > 5)
		{
			window.close();
		}

		//window positioning...
		window.clear(Color::White);
		check_sprite.setPosition(Vector2f(125, 50));
		displaytext.setPosition(Vector2f(50, 350));

		//creating window
		window.draw(check_sprite);
		window.draw(displaytext);
		window.display();

		//updating the timer as how much timr has passed
		timer = clock.getElapsedTime();
	}
}

//almost equal to succesfull_connection() but for a failed one, to let player know a conection
//has been refused
void GUI::failed_connection()
{
	//preparing the window
	RenderWindow window;
	window.create(VideoMode(500, 500), "Failed Connection");
	window.setFramerateLimit(30);

	//loading cross
	Texture cross_texture;
	if(!cross_texture.loadFromFile("Assets/cross.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite cross_sprite;
	cross_sprite.setTexture(cross_texture);
	
	//loading font
	Font font;
	if (!font.loadFromFile("Assets/arial.ttf"))
	{
		printf("Error while loading font\n");
	}

	//text to let the user know the connection has failed
	Text displaytext;
	displaytext.setFont(font);
	displaytext.setCharacterSize(40);
	displaytext.setFillColor(Color::Black);
	displaytext.setString("Failed Connection");

	//starting a clock
	Clock clock;
	clock.restart();

	//creating timer equal to clock, display time as int
	Time timer = clock.getElapsedTime();

	//window update loop
	while(window.isOpen())
	{
		//if user closes window close safely
		Event event;
		while(window.pollEvent(event))
		{
			if(event.type == Event::Closed)
			{
				window.close();
			}
		}

		//if 5 seconds gave passed close the window automatically
		if(timer.asSeconds() > 5)
		{
			window.close();
		}

		//preparing window positions
		window.clear(Color::White);
		cross_sprite.setPosition(Vector2f(125, 50));
		displaytext.setPosition(Vector2f(100, 350));

		//creating window
		window.draw(cross_sprite);
		window.draw(displaytext);
		window.display();

		//updating the timer as how much timr has passed
		timer = clock.getElapsedTime();
	}
}

//window to display a question to be answer, with a time limit
//function recieves the question and for possible answers, also the playername for display
string GUI::ask_question(string Q, string A, string B, string C, string D, string playername)
{
	//preparing the window
	RenderWindow window;
	window.create(VideoMode(1500, 1100), "Have Fun!");
	window.setFramerateLimit(30);

	//loading logo...
	Texture logo_texture;
	if(!logo_texture.loadFromFile("Assets/MLogoTransp.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite logo_sprite;
	logo_sprite.setTexture(logo_texture);
	logo_sprite.setScale(.5, .5);

	//loading font...
	Font font;
	if (!font.loadFromFile("Assets/arial.ttf"))
	{
		printf("Error while loading font\n");
	}

	//text to display question
	Text question;
	question.setFont(font);
	question.setCharacterSize(45);
	question.setFillColor(Color::Black);
	question.setString(Q);

	//text to display option A
	Text optA;
	optA.setFont(font);
	optA.setCharacterSize(60);
	optA.setFillColor(Color::Black);
	optA.setString(A);

	//text to display option B
	Text optB;
	optB.setFont(font);
	optB.setCharacterSize(60);
	optB.setFillColor(Color::Black);
	optB.setString(B);

	//text to display option C
	Text optC;
	optC.setFont(font);
	optC.setCharacterSize(60);
	optC.setFillColor(Color::Black);
	optC.setString(C);

	//text to display option D
	Text optD;
	optD.setFont(font);
	optD.setCharacterSize(60);
	optD.setFillColor(Color::Black);
	optD.setString(D);

	//text to display the player's name
	Text player;
	player.setFont(font);
	player.setCharacterSize(60);
	player.setFillColor(Color::Black);
	player.setString(playername);

	//creating rectangle to contain answer A
	RectangleShape rectA;
	rectA.setSize(Vector2f(1400, 80));
	rectA.setFillColor(Color::Red);

	//creating rectangle to contain answer B
	RectangleShape rectB;
	rectB.setSize(Vector2f(1400, 80));
	rectB.setFillColor(Color::Blue);

	//creating rectangle to contain answer C
	RectangleShape rectC;
	rectC.setSize(Vector2f(1400, 80));
	rectC.setFillColor(Color::Green);

	//creating rectangle to contain answer D
	RectangleShape rectD;
	rectD.setSize(Vector2f(1400, 80));
	rectD.setFillColor(Color::Yellow);

	//starting a clock
	Clock clock;
	clock.restart();

	//set a timer to count down and show player how much time he has left
	Time timer = clock.getElapsedTime();
	Text timertext;
	timertext.setFont(font);
	timertext.setCharacterSize(80);
	timertext.setFillColor(Color::Black);
	timertext.setString(to_string((int)(timer.asSeconds() - 30) * -1));

	//window update loop;
	while(window.isOpen())
	{
		Event event;
		while(window.pollEvent(event))
		{
			//if player closes window send E, as it is not a possible answer
			if(event.type == Event::Closed)
			{
				window.close();
				return "E";
			}

			//send option A when rectabgle for the option is pressed
			if(isSpriteHover(rectA.getGlobalBounds(), Vector2f(event.mouseButton.x, event.mouseButton.y)))
			{
				if((event.type == Event::MouseButtonReleased) && (event.mouseButton.button == Mouse::Left))
				{
					window.close();
					return "A";
				}
			}

			//send option B when rectabgle for the option is pressed
			if(isSpriteHover(rectB.getGlobalBounds(), Vector2f(event.mouseButton.x, event.mouseButton.y)))
			{
				if((event.type == Event::MouseButtonReleased) && (event.mouseButton.button == Mouse::Left))
				{
					window.close();
					return "B";
				}
			}

			//send option C when rectabgle for the option is pressed
			if(isSpriteHover(rectC.getGlobalBounds(), Vector2f(event.mouseButton.x, event.mouseButton.y)))
			{
				if((event.type == Event::MouseButtonReleased) && (event.mouseButton.button == Mouse::Left))
				{
					(window.close());
					return "C";
				}
			}

			//send option D when rectabgle for the option is pressed
			if(isSpriteHover(rectD.getGlobalBounds(), Vector2f(event.mouseButton.x, event.mouseButton.y)))
			{
				if((event.type == Event::MouseButtonReleased) && (event.mouseButton.button == Mouse::Left))
				{
					window.close();
					return "D";
				}
			}
		}

		//if timer ends send E as it is not a possible option
		if(timer.asSeconds() > 30)
		{
			window.close();
			return "E";
		}

		//preparing the window text and sprotes postions
		window.clear(Color::White);
		logo_sprite.setPosition(Vector2f(200, 100));
		question.setPosition(Vector2f(50, 550));
		rectA.setPosition(Vector2f(50, 650));
		rectB.setPosition(Vector2f(50, 750));
		rectC.setPosition(Vector2f(50, 850));
		rectD.setPosition(Vector2f(50, 950));
		optA.setPosition(Vector2f(80, 650));
		optB.setPosition(Vector2f(80, 750));
		optC.setPosition(Vector2f(80, 850));
		optD.setPosition(Vector2f(80, 950));
		timertext.setPosition(Vector2f(1300, 50));
		player.setPosition(Vector2f(20, 20));

		//creating the window
		window.draw(logo_sprite);
		window.draw(question);
		window.draw(rectA);
		window.draw(rectB);
		window.draw(rectC);
		window.draw(rectD);
		window.draw(optA);
		window.draw(optB);
		window.draw(optC);
		window.draw(optD);
		window.draw(timertext);
		window.draw(player);
		window.display();

		//Update timer
		timer = clock.getElapsedTime();
		timertext.setString(to_string((int)(timer.asSeconds() - 30) * -1));
	}

	//send E as it is not a possible option
	return "E";
}

//display if the player got the answer right and his current points
//recieves the nu,ber of points to display
void GUI::correct_answer(int points)
{
	//preparing the window
	RenderWindow window;
	window.create(VideoMode(1500, 800), "Correct");
	window.setFramerateLimit(30);

	//loading logo...
	Texture logo_texture;
	if(!logo_texture.loadFromFile("Assets/MLogoTransp.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite logo_sprite;
	logo_sprite.setTexture(logo_texture);
	logo_sprite.setScale(.5, .5);

	//loading checkmark...
	Texture check_texture;
	if(!check_texture.loadFromFile("Assets/check.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite check_sprite;
	check_sprite.setTexture(check_texture);
	
	//loading font...
	Font font;
	if (!font.loadFromFile("Assets/arial.ttf"))
	{
		printf("Error while loading font\n");
	}

	//text to diplay total points so far
	Text points_earned;
	points_earned.setFont(font);
	points_earned.setCharacterSize(100);
	points_earned.setFillColor(Color::Black);
	points_earned.setString("Points: " + to_string(points));

	//starting a clock
	Clock clock;
	clock.restart();

	//setting the timer
	Time timer = clock.getElapsedTime();

	//window update loop
	while(window.isOpen())
	{
		//if user closes, close window safely
		Event event;
		while(window.pollEvent(event))
		{
			if(event.type == Event::Closed)
			{
				window.close();
			}
		}

		//if 5 seconds have passed close window automatically
		if(timer.asSeconds() > 3)
		{
			window.close();
		}

		//preparing window positions
		window.clear(Color::White);
		logo_sprite.setPosition(Vector2f(200, 100));
		check_sprite.setPosition(Vector2f(300, 500));
		points_earned.setPosition(Vector2f(700, 550));

		//creating window
		window.draw(logo_sprite);
		window.draw(check_sprite);
		window.draw(points_earned);
		window.display();

		//updating timer
		timer = clock.getElapsedTime();
	}
}

//almost equal to correct_answers() but diplayes if the player is wrong and total points so far
void GUI::incorrect_answer(int points)
{
	//preparing window
	RenderWindow window;
	window.create(VideoMode(1500, 800), "Incorrect");
	window.setFramerateLimit(30);

	//loading logo...
	Texture logo_texture;
	if(!logo_texture.loadFromFile("Assets/MLogoTransp.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite logo_sprite;
	logo_sprite.setTexture(logo_texture);
	logo_sprite.setScale(.5, .5);

	//loading crossmark...
	Texture cross_texture;
	if(!cross_texture.loadFromFile("Assets/cross.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite cross_sprite;
	cross_sprite.setTexture(cross_texture);
	
	//loading font...
	Font font;
	if (!font.loadFromFile("Assets/arial.ttf"))
	{
		printf("Error while loading font\n");
	}

	//text to diplay total points so far
	Text points_earned;
	points_earned.setFont(font);
	points_earned.setCharacterSize(100);
	points_earned.setFillColor(Color::Black);
	points_earned.setString("Points: " + to_string(points));

	//starting a clock
	Clock clock;
	clock.restart();

	//setting the timer
	Time timer = clock.getElapsedTime();

	//update window loop
	while(window.isOpen())
	{
		//if player closes, close window safelly
		Event event;
		while(window.pollEvent(event))
		{
			if(event.type == Event::Closed)
			{
				window.close();
			}
		}

		//if 5 seconds have passed close the window automatically
		if(timer.asSeconds() > 3)
		{
			window.close();
		}

		//preparing positions in window
		window.clear(Color::White);
		logo_sprite.setPosition(Vector2f(200, 100));
		cross_sprite.setPosition(Vector2f(300, 500));
		points_earned.setPosition(Vector2f(700, 550));

		//creating the window
		window.draw(logo_sprite);
		window.draw(cross_sprite);
		window.draw(points_earned);
		window.display();

		//updating the timer
		timer = clock.getElapsedTime();
	}
}

//displaying the winner of the game and the amount of points he got, also displaying
//the user and the amount of points he got
//the function recieves the winners name and points and the playes name and points
void GUI::show_winner(string win_name, int win_points, string us_name, int us_points)
{
	//preparing the window
	RenderWindow window;
	window.create(VideoMode(1500, 1200), "Results");
	window.setFramerateLimit(30);

	//loading logo...
	Texture logo_texture;
	if(!logo_texture.loadFromFile("Assets/MLogoTransp.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite logo_sprite;
	logo_sprite.setTexture(logo_texture);
	logo_sprite.setScale(.5, .5);

	//loading cup sprite...
	Texture cup_texture;
	if(!cup_texture.loadFromFile("Assets/winner.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite cup_sprite;
	cup_sprite.setTexture(cup_texture);

	//loading user sprite...
	Texture usr_texture;
	if(!usr_texture.loadFromFile("Assets/user.jpg"))
	{
		printf("Error while loading image\n");
	}
	Sprite usr_sprite;
	usr_sprite.setTexture(usr_texture);

	//loading exit button...
	Texture exit_texture;
	if(!exit_texture.loadFromFile("Assets/exit.png"))
	{
		printf("Error while loading image\n");
	}
	Sprite exit_sprite;
	exit_sprite.setTexture(exit_texture);
	exit_sprite.setScale(.3, .3);

	//loading font
	Font font;
	if (!font.loadFromFile("Assets/arial.ttf"))
	{
		printf("Error while loading font\n");
	}

	//text to display winners name
	Text win_name_text;
	win_name_text.setFont(font);
	win_name_text.setCharacterSize(60);
	win_name_text.setFillColor(Color::Black);
	win_name_text.setString(win_name);

	//text to display winners points
	Text win_points_text;
	win_points_text.setFont(font);
	win_points_text.setCharacterSize(60);
	win_points_text.setFillColor(Color::Black);
	win_points_text.setString("Points: " + to_string(win_points));

	//text to display users name
	Text us_name_text;
	us_name_text.setFont(font);
	us_name_text.setCharacterSize(60);
	us_name_text.setFillColor(Color::Black);
	us_name_text.setString(us_name);

	//text to display users points
	Text us_points_text;
	us_points_text.setFont(font);
	us_points_text.setCharacterSize(60);
	us_points_text.setFillColor(Color::Black);
	us_points_text.setString("Points: " + to_string(us_points));

	//window update loops
	while(window.isOpen())
	{
		Event event;
		while(window.pollEvent(event))
		{
			//if player closes window, close safelly
			if(event.type == Event::Closed)
			{
				window.close();
			}
			
			//if the exit button is pressed close de window
			if(isSpriteHover(exit_sprite.getGlobalBounds(), Vector2f(event.mouseButton.x, event.mouseButton.y)))
			{
				if((event.type == Event::MouseButtonReleased) && (event.mouseButton.button == Mouse::Left))
				{
					window.close();
				}
			}
		}

		//preparing positions in window
		window.clear(Color::White);
		logo_sprite.setPosition(Vector2f(200, 100));
		cup_sprite.setPosition(Vector2f(200, 500));
		usr_sprite.setPosition(Vector2f(900, 500));
		win_name_text.setPosition(Vector2f(200, 800));
		us_name_text.setPosition(Vector2f(900, 800));
		win_points_text.setPosition(Vector2f(200, 900));
		us_points_text.setPosition(Vector2f(900, 900));
		exit_sprite.setPosition(600, 1000);

		//creating window
		window.draw(logo_sprite);
		window.draw(cup_sprite);
		window.draw(usr_sprite);
		window.draw(win_name_text);
		window.draw(us_name_text);
		window.draw(win_points_text);
		window.draw(us_points_text);
		window.draw(exit_sprite);
		window.display();
	}
}
