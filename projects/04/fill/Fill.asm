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

(LOOP)
    @KBD
    D=M
    @BLACKEN
    D;JNE
    @CLEAR
    0;JMP
(BLACKEN)
    D=0
    D=!D
    @fillValue
    M=D
    @FILL
    0;JMP
(CLEAR)
    @fillValue
    M=0
    @FILL
    0;JMP
(FILL)
    @256
    D=A
    @i
    M=D
    @SCREEN
    D=A
    @ptr
    M=D
(FILL_LOOP)
    @i
    D=M
    @FILL_LOOP_END
    D;JEQ
    @32
    D=A
    @j
    M=D
(FILL_ROW)
    @j
    D=M
    @FILL_ROW_END
    D;JEQ
    @fillValue
    D=M
    @ptr
    A=M
    M=D
    @ptr
    M=M+1
    @j
    M=M-1
    @FILL_ROW
    0;JMP
(FILL_ROW_END)
    @i
    M=M-1
    @FILL_LOOP
    0;JMP
(FILL_LOOP_END)
    @LOOP
    0;JMP
