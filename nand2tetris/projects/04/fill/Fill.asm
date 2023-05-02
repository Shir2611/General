// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

  //the location we are at, start at the top left corner
  @loc
  M=0

  //this is the main loop that checks the keyboard value and checks if
  //the value is 0 then goes to the white loop as any key was not presssed.
  //otherwise goes to the black loop as a randon key was pressed.
(MAINLOOP)
  //we loat the keyboard value
  @KBD
  D=M
  //if the KBD is 0 goto whiteloop
  @WHITELOOP
  D;JEQ 
  //if the KBD is 1 goto blackloop
  @BLACKLOOP
  0;JMP

(WHITELOOP)
  @loc
  D=M 
  //goto MAINLOOP if we are less than 0
  @MAINLOOP
  D;JLT
  @loc
  D=M
  @SCREEN
  A=A+D
  //fill with white
  M=0
  //we nome to the previous location
  @loc
  M=M-1
  @MAINLOOP
  0;JMP

(BLACKLOOP)
  @loc
  D=M
  //the max location we have
  @8192
  D=D-A
  //check whether the (loc - max location) is larger or equal to 0 s.t. we are not at the end
  @MAINLOOP
  D;JGE 
  @loc
  D=M
  @SCREEN
  A=A+D
  //fill with black
  M=-1
  //we move to the next location
  @loc
  M=M+1
  @MAINLOOP
  0;JMP

  //we stuck in an infinite loop
(END)
  @END
  0;JMP