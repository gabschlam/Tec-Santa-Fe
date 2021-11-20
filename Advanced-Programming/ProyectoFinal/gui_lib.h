/************************************************************
Gabriel Schlam Huber
Simon Metta Grego
Isaac Harari Masri

GUI Header
Header file for the GUI library
************************************************************/

#include <SFML/Window.hpp>
#include <SFML/Graphics.hpp>
#include <SFML/OpenGL.hpp>
#include <iostream>

using namespace sf;
using namespace std;

class GUI
{
public:
	string select_server_window();
	string set_name_window();
	bool isSpriteHover(FloatRect sprite, Vector2f mp);
	void successfull_connection();
	void failed_connection();
	string ask_question(string Q, string A, string B, string C, string D, string playername);
	void correct_answer(int points);
	void incorrect_answer(int points);
	void show_winner(string win_name, int win_points, string us_name, int us_points);
};
