Jokatu
======

A webserver and client for playing boardgames.  The server uses the [Spring Framework](http://projects.spring.io/spring-framework/) and the client uses homebrewed JavaScript.  They communicate mainly using [STOMP](https://stomp.github.io/) over websockets.

Games
-----
Jokatu has a few games implemented; they exist mainly as an excuse to build up some simple components for planned games.  As you might be able to tell, they're not particularly exciting. 

### Preëxisting games
- Rock/Paper/Scissors
- Noughts and Crosses
- [Sevens](https://en.wikipedia.org/wiki/Sevens_(card_game))

### Other games
- Uzta
    - Played on a graph produced from a random [Delaunay triangulation](https://en.wikipedia.org/wiki/Delaunay_triangulation) of the unit square.
    - Nodes of the graph produce resources when a D12 lands on one of their values.
        - Nodes have 1–3 values, so some nodes produce more frequently than others.
        - There are three types of resources, which are denoted by the shapes □, ○ and ◇.
    - Resources can be harvested from a node by owning an incoming edge to that node.
        - Owning multiple edges to the same node adds to the yield.
    - Players start with two free edges each and can purchase more edges with the resources they gain.
        - Edges cost one of each of the resources plus one resource per value and type of the two end nodes.
    - The game is played in turns in which the following happens:
        1. The die is rolled and resources are distributed.
        2. Players can purchase edges with their resources.
        3. When the player wishes, they can pass the turn to the next player.
    - Between turns, players can trade with other players or the supply.
        1. Players can trade resources with other players at a mutually agreed rate.
        2. They can exchange their resources for others with the supply at a ratio of 3:1.
    - Players earn one point per edge they own.
    - TODO:
        - The game needs to have an end condition.
        - Players should earn more points for cyclic paths and surrounding a node.

### Other ‘games’
- A rudimentary chat client called Echo
    - The players' ‘moves’ are to send messages to the board
- The Game of Games
    - The players' ‘moves’ are to create other games

Prerequisites
-------------
### A message broker for STOMP
#### Option 1: RabbitMQ
- Install RabbitMQ
- Enable the STOMP plugin `rabbitmq-plugins enable rabbitmq_stomp`
