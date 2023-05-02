#all the dictionaries that are needed for this project:
import sys
import os
comp={'0':'0101010','1':'0111111','-1':'0111010','D':'0001100',
      'A':'0110000','M':'1110000','!D':'0001101','!A':'0110001',
      '!M':'1110001','-D':'0001111','-A':'0110011','-M':'1110011',
      'D+1':'0011111','A+1':'0110111','M+1':'1110111','D-1':'0001110',
      'A-1':'0110010','M-1':'1110010','D+A':'0000010','D+M':'1000010',
      'D-A':'0010011','D-M':'1010011','A-D':'0000111','M-D':'1000111',
      'D&A':'00000000','D&M':'1000000','D|A':'0010101','D|M':'1010101'}

dest={'':'000','M=':'001','D=':'010','MD=':'011',
      'A=':'100','AM=':'101','AD=':'110','AMD=':'111'}

jump={'':'000',';JGT':'001',';JEQ':'010',';JGE':'011',
      ';JLT':'100',';JNE':'101',';JLE':'110',';JMP':'111'}

symbols={'SP':0,'LCL':1,'ARG':2,'THIS':3,'THAT':4,'SCREEN':16384,'KBD':24576,
         'R0':0,'R1':1,'R1':1,'R2':2,'R3':3,'R4':4,'R5':5,'R6':6,'R7':7,
         'R8':8,'R9':9,'R10':10,'R11':11,'R12':12,'R13':13,'R14':14,'R15':15}

commands=[]
asm=[]

def main_fun(file):
    # removing new lines and comments
    arg = count =0
    c =''
    location = 16
    asm_f = open(file, 'r')
    #removing new lines and comments
    for l in asm_f:
        if "//" in l:
            l = l[:l.index("//"):]
        if l != "\n":
            commands.append(l.replace("\n", ""))
    asm_f.close()
    #removing the brackets
    for command in commands:
        if "(" in command and ")" in command:
            symbol = command[1:-1]
            #if we don't have the symbol then we add it to the end of the current symbol map (statring from index 16)
            if symbol not in symbols:
                symbols[symbol] = count
        else:
            if command != '':
                count += 1
    for c in range(0, len(commands)):
        commands[c] = commands[c].strip()
    #append to the asmfile all the lines without brackets
    for l in commands:
        if "(" not in l and ")" not in l and l != '':
            asm.append(l)
    #check whether we have a symbol that is a command (like @i or @sum) 
    for command in asm:
        if '@' in command and not command[1::].isnumeric():
            symbol = command[1::]
            if symbols.get(symbol) or symbols.get(symbol) == 0:
                count = count
            else:
                symbols[symbol] = location
                location += 1
        else:
            symbol = ""
    #create a path for the hack file in the same directory as the current file in
    basename1 = os.path.basename(file)
    basename2 = basename1.replace(".asm", ".hack")
    file = file.replace(basename1, basename2)
    #open the file with extention hack, and add to the file all the instructions in binary code acoording to the dictionaries above
    hack_f = open(file, 'w')
    for command in asm:
    #check whether we have a symbol that is a command (like @i or @sum)
        if command[0] == '@':
            pos = 0
            if command[1:] in symbols:
                pos = symbols[command[1:]] + 32768
            else:
                pos = int(command[1:]) + 32768
            #we change the values to binary values
            hack_f.write('0' + bin(pos)[3:] + '\n')
        else:
            #we take the start of the expression (before the '=' sign)
            arg = command.find('=')
            d1 = command[0:arg + 1:1]
            d1 = d1.split()
            if d1:
                d = dest[str(d1[0])]
            else:
                d = dest['']
            #we take the last part of the expression (after the ';' sign)
            arg = command.find(';')
            if arg == -1:
                j1 = ''
            else:
                j1 = command[arg::]
            j1 = j1.split()
            if j1:
                j = jump[str(j1[0])]
            else:
                j = jump['']
            if command.find('=') != -1:
                c = comp[command[command.find('=') + 1::]]
            elif command.find(';') != -1:
                c = comp[command[:command.find(';'):]]
            else:
                c = ''
            #as we know from class the binary sequence always starts in '111'
            hack_f.write('111' + c + d + j + '\n')
    #closing the file once we are done
    hack_f.close()

if __name__ == '__main__':
    main_fun(sys.argv[1])
