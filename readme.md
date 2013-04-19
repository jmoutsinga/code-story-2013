Code Story 2013 : jajascript spaceshift rental optimizer
========================================================

A maven module implementation proposal
--------------------------------------

This maven module can hold your code story 2013 challenge implementation.

It is a simple way to resolve the challenge and get focused on your implementation.
The module provide the test cases that will validate your implementation.

All test cases are already written to send requests as proposed (I should write imposed :-)

Check the website of code story here :
http://code-story.net/2013/02/02/jajascript.html

To run tests on a custom server, use system variable 'server' and 'port'.

The maven goal :
'mvn test -Dserver=example.server.com -Dport=4242'

Note that using the goal :
'mvn test'
All requests will be played on a server @ localhost, port 8080