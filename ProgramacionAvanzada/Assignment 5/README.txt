
CONTENTS
--------

 * How to use the client program
 * Protocol used


HOW TO USE THE CLIENT PROGRAM
-----------------------------

1. To run the Client's program, you need run it like this: "./client {server_address} {port_number}"

2. Also, if you run it just by entering: "./client", the program by itself will print the following,
   so you should know how to run it properly:
   ____________________________________________________
  |                                                    |
  | === CLIENT PROGRAM ===                             |
  | Usage:                                             |
  | ./client {server_address} {port_number}            |
  |____________________________________________________|

3. After you run it, you need to enter the amount of money you have right now to play. You would see 
   this output:
   ____________________________________________________
  |                                                    |
  | === CLIENT PROGRAM ===                             |
  | Welcome to the Blackjack game!                     |
  | Enter the maximum amount of money you want to bet: |
  |____________________________________________________|

4. Next, you will see a message saying that the game is ready, followed by a request to enter your bet:
   ____________________________________________________
  |                                                    |
  | Game ready!                                        |
  | Enter your bet:                                    |
  |____________________________________________________|

5. Then your turn will start, and the first two cards drawn and the total hand will be printed. After 
   that you will be asked if you want another hit or not (meaning that you want to stay)

   Here is an example:
   ____________________________________________________
  |                                                    |
  | === YOUR TURN ===                                  |
  | Card: K                                            |
  | Card: 9                                            |
  | Hand: 19                                           |
  | Do you want another card? (y/n)                    |
  |____________________________________________________|

6. If you choose another card, there are two scenarios that can happen. The first one is that the hand 
   will add to 21 or lower.

   Or the second one is that you get busted.

   First scenario:
   ____________________________________________________
  |                                                    |
  | Do you want another card? (y/n) y                  |
  | Card: 2                                            |
  | Hand: 21                                           |
  | Do you want another card? (y/n)                    |
  |____________________________________________________|

   Second scenario:
   ____________________________________________________
  |                                                    |
  | Do you want another card? (y/n) y                  |
  | Card: 5                                            |
  | Hand: 24                                           |
  | Busted at 24                                       |
  |____________________________________________________|

7. If you choose to stay, or you get busted, the dealer's turn will start, and its cards will be printed.

   Let's take the second scenario in point 6 as an example:
   ____________________________________________________
  |                                                    |
  | === DEALER'S TURN ===                              |
  | Card: 6                                            |
  | Card: J                                            |
  | Card: 3                                            |
  | Dealer's hand: 19                                  |
  |____________________________________________________|

8. Immediately after, the result will be printed. There are 3 possibilities: 
   * Tie, if the dealer and the player have the same total, or if both got busted.
   * The dealer wins, if the dealer got higher total, or if the player got busted and the dealer didn't. 
   * And finally, the player wins, if the player got higher total or if the dealer got busted and the 
   player didn't.

9. After that, the money left will be shown. If it was a tie, you stay even. If the dealer wins, you 
   lose the bet. If you win, you win the bet.
   In the same example, as the player got busted and the dealer didn't, the dealer wins, and the bet 
   made (in this case $10, from $100 in the beginning) is lost.

10. Finally, you will be asked if you want to play another hand with the remaining money or not. If 
    you choose yes, you will be asked again fot a bet, and so on.
   ____________________________________________________
  |                                                    |
  | === RESULT: ===                                    |
  | The dealer wins!                                   |
  | You have $90 left.                                 |
  | Do you want to play another hand? (y/n)            |
  |____________________________________________________|


PROTOCOL USED
-------------

The protocol used is somewhat simple. It is made up of a system that sends and receives one thing at 
a time.

By this I mean that the following information is not sent if another is not received before.

Here is how it goes:
1. The Client initiates the communication, by sending the word Start, followed by the amount of money
him has to play. For example: "START:100".

2. If the Server recieves the right information, it means that it got connection from the right Client.
The Server answers this with "READY", and saves the money sent. If it didn't received the correct 
information, it ignores the request.

3. The Client makes the last connection validation, as he checks if the word received was the correct 
one ("READY"). If it was correct, he sends the word Play, followed by the bet made from the player.
For example: "PLAY:10". 
If not, he ignores the Server.

4. The Server receives the bet and saves it. After that, it starts generating the first 2 cards of 
the player, and sends them, with the total hand, following the protocol:
  * Server sends first card.
  * Client recevies card and sends an ACK (Acknowledgment).
  * Server receives ACK and sends second card.
  * Client sends ACK.
  * Server sends total hand.
  * Client sends ACK.

5. The player decides if he wants another card or he wants to stay. Depending on his decision, the 
Client sends "HIT" or "STAY" to the Server. 
With HIT, we go to step 6, with STAY we go directly to step 7.

6. If the Server receives HIT, basically we follow the same as in step 4, but instead of sending 2 
cards, the Server sends only one card and waits if the player wants to hit again or stay.

7. If the Server receives STAY, it starts generating all the dealer's cards, and also it sends it one 
by one, like in step 4: Server sends card and waits for ACK.
After the Server sends all of the cards, and receives the last ACK, it sends the total hand.

8. Then, the Server receives an ACK of the Client meaning that it received the cards and the total hand.
After this, the Server calculates the result, and it sends the outcome, followed by the remaining money: 
"TIE:MONEY"; "DEALER:MONEY"; "PLAYER:MONEY".

9. Finally, the Client asks the player if he wants another hand or to exit the game. And depending on the 
decision, it sends the Server "HAND" or "EXIT".

10. If the Server receives "HAND", it repeats the protocol from the step 3 to the end. If it receives 
"EXIT", the Server sends "BYE" to finish the communication.
















