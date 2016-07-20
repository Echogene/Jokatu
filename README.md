Jokatu
======

A webserver and client for playing boardgames.  The server uses the [Spring Framework](http://projects.spring.io/spring-framework/) and the client uses homebrewed JavaScript.  They communicate mainly using [STOMP](https://stomp.github.io/) over websockets.

Games
-----
Jokatu has a few games implemented; they exist mainly as an excuse to build up some simple components for planned games.  As you might be able to tell, they're not particularly exciting. 

###Preëxisting games
- Rock/Paper/Scissors
- Noughts and Crosses
- [Sevens](https://en.wikipedia.org/wiki/Sevens_(card_game))

###Other ‘games’
- A rudimentary chat client called Echo
  - The players' ‘moves’ are to send messages to the board
- The Game of Games
  - The players' ‘moves’ are to create other games
