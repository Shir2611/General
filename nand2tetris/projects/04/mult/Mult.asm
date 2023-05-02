// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

    //set the result to be 0
    @R2
    M=0

    //if at least one number is 0 than the whole mult. is 0 as well
    @R0
    D=M
    @END
    D;JEQ

    @R1
    D=M
    @END
    D;JEQ

//now I want to add the first number to itself R1 times thus we use a loop
//we need to add R0 to R2 and every time we subtract 1 from R1
(LOOP)
    //we get the reasult
    @R2
    D=M
    //we add to the result R0
    @R0
    D=D+M
    //we write the outcome back to the result in R2
    @R2
    M=D
    //we subtract 1 from R1
    @R1
    D=M-1
    M=D
    //we check if R1 > 0 and we have more claculations to do
    //if yes goto LOOP
    //else goto END
    @R1
    @LOOP
    D;JGT
    //we stuck in an infinate loop
(END)
    @END
    0;JMP