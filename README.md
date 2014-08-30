CS3213 Assignment 1: Kwic-Kwac-Kwoc
==

## TL;DR
1. __Make__   
`make`

2. __Ignored words__  
`cat ignore.txt`  

3. __Usage__  
`java -jar assignment1.jar -h`

4. __Run__  
`java -jar assignment1.jar`  
`java -jar assignment1.jar -i ignore.txt -f data/movies-10.txt`  
`java -jar assignment1.jar -i ignore.txt < data/movies-10.txt`

## File mode vs STDIN
Not specifying the -f switch makes the program read from the STDIN. The
program will keep reading from STDIN, with each line representing a title,
until either EOF or when there is a blank line input.

## Ignore words file
Using the -i switch makes the program read from the ignore words file, which
should be a list of keywords to be ignored sepearted by a newline. See sample
ignore.txt for the expected format.

## Compiled JAR
I've included the final jar file in the `bin/` folder
