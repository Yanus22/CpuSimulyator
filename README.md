    CompilerSyntax: This class handles syntax checking for the input code. It checks if the provided code adheres to specific syntax rules, such as valid instructions, operands, and labels. It also removes whitespace and stores instructions in clean form.

    ALU (Arithmetic Logic Unit): This class is responsible for executing arithmetic and logical instructions, such as addition, subtraction, multiplication, division, and more. It also handles operations like increment, decrement, comparison, and printing values.

    Cpu: This class represents the central processing unit of the simulated CPU. It initializes registers, performs syntax analysis, and prepares the code for execution. It also has a Run method to start executing the code.

    CU (Control Unit): This class acts as the control unit of the CPU. It fetches instructions one by one and decodes them, passing them to the ALU for execution. It handles control flow instructions like jumps.  


        CompilerSyntax Class:
CompilerSyntax Constructor

    Parameters:
        mapForLine: A map containing line numbers as keys and raw code lines as values.
        mapForLabel: A map for storing label names and their corresponding line numbers.
        mapForRegister: A map for storing register values.
        mapForLineClean: A map for storing code lines without labels.
        path: The path to the input code file.

    Description:
        Initializes the CompilerSyntax class with maps and reads the input code from the specified file.
        Calls the Label() method to identify labels in the code.

SyntaxAnalys Method

    Return Type: boolean
    Description:
        Performs syntax analysis on the code lines stored in mapForLineClean.
        Checks if each line adheres to the specified syntax rules, such as valid instructions and operands.
        Returns true if the code passes syntax analysis; otherwise, returns false.

OperandIsRegisterOrNO Method

    Parameters:
        line: A string representing an operand.

    Return Type: boolean

    Description:
        Checks if the provided operand is a valid register (e.g., "r0," "r1," etc.) or not.
        Returns true if the operand is a valid register; otherwise, returns false.

OperandIsNumberOrNo Method

    Parameters:
        line: A string representing an operand.

    Return Type: boolean

    Description:
        Checks if the provided operand is a valid number or not.
        Returns true if the operand is a valid number; otherwise, returns false.

In3CharInstruction and In4CharInstruction Methods

    Parameters:
        line: A string representing an instruction.

    Return Type: boolean

    Description:
        Checks if the provided instruction is a valid 3-character or 4-character instruction, respectively.
        Returns true if the instruction is valid; otherwise, returns false.

ReadFromFile Method

    Parameters:
        path: The path to the input code file.

    Description:
        Reads the code from the specified file, removes whitespace, and stores it in mapForLine and mapForLineClean.
        Identifies labels and stores them in mapForLabel.

ALU Class:
Arithmetic and Logical Operation Methods

    These methods (e.g., Mov, Add, Sub, Mul, Div, And, Orr, Xor, Increment, Decrement, Compear, Print) execute various arithmetic and logical operations based on the provided instructions and operands.
    They update register values as necessary.

OperandIsRegisterOrNO Method

    Parameters:
        line: A string representing an operand.

    Return Type: boolean

    Description:
        Checks if the provided operand is a valid register or not.
        Returns true if the operand is a valid register; otherwise, returns false.

OperandIsNumberOrNo Method

    Parameters:
        line: A string representing an operand.

    Return Type: boolean

    Description:
        Checks if the provided operand is a valid number or not.
        Returns true if the operand is a valid number; otherwise, returns false.

Cpu Class:
Cpu Constructor

    Parameters:
        path: The path to the input code file.

    Description:
        Initializes the CPU with registers and performs syntax analysis using the CompilerSyntax class.
        Checks if the code passes syntax analysis; if not, it raises an exception.

Run Method

    Description:
        Initiates the execution of the code by starting with the first line.
        Calls the appropriate methods in the CU class to execute instructions.

CU Class:
CU Constructor

    Parameters:
        mapForLine: A map containing line numbers as keys and raw code lines as values.
        mapForLabel: A map for storing label names and their corresponding line numbers.
        mapForRegister: A map for storing register values.
        mapForLineClean: A map for storing code lines without labels.
        path: The path to the input code file.
        compiler: An instance of the CompilerSyntax class.

    Description:
        Initializes the CU class with maps and an instance of the CompilerSyntax class.
        Creates an ALU instance for executing instructions.

Instruction Method

    Parameters:
        line: A string representing an instruction to execute.
        index: The index of the current line in mapForLineClean.

    Description:
        Executes the provided instruction, updates registers, and handles control flow based on conditional jumps.
        Recursively calls itself for the next instruction until the end of the code is reached.


