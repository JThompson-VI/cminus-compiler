# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main
#code for fib
fib:
# Entering a new scope
# Symbols on the symbol table
# println
# i
# return
# Update the stack pointer
addi $sp $sp -0
# get i offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of i
add $t0 $sp $t0
# load the value of i
lw $t0 0($t0)
li $t1 0
xor $t0 $t0 $t1
slti $t0 $t0 1
beq $t0 $zero datalabel0
li $t1 1
# store the return value on the stack
sw $t1 -8($sp)
jr $ra
j datalabel1
datalabel0:
datalabel1:
# get i offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of i
add $t0 $sp $t0
# load the value of i
lw $t0 0($t0)
li $t1 1
xor $t0 $t0 $t1
slti $t0 $t0 1
beq $t0 $zero datalabel2
li $t1 1
# store the return value on the stack
sw $t1 -8($sp)
jr $ra
j datalabel3
datalabel2:
datalabel3:
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -12($sp)
# Evaluate args and place on the stack
# get i offset from the stack pointer.
li $t1 -4
# load offset + sp to get the address of i
add $t1 $sp $t1
# load the value of i
lw $t1 0($t1)
li $t2 1
sub $t1 $t1 $t2
sw $t1 -16($sp)
# update stack pointer
addi $sp $sp -12
# call the function
jal fib
# restore stack pointer
addi $sp $sp 12
# restore t regs
lw $t0 -12($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -20($sp)
# calling function fib
# store ra
move $t1 $ra
# store t registers
sw $t0 -12($sp)
sw $t1 -16($sp)
# Evaluate args and place on the stack
# get i offset from the stack pointer.
li $t2 -4
# load offset + sp to get the address of i
add $t2 $sp $t2
# load the value of i
lw $t2 0($t2)
li $t3 2
sub $t2 $t2 $t3
sw $t2 -20($sp)
# update stack pointer
addi $sp $sp -16
# call the function
jal fib
# restore stack pointer
addi $sp $sp 16
# restore t regs
lw $t0 -12($sp)
lw $t1 -16($sp)
# restore ra
move $ra $t1
# get return value off the stack
lw $t1 -24($sp)
add $t0 $t0 $t1
# store the return value on the stack
sw $t0 -8($sp)
jr $ra
# exiting scope, restoring sp 
addi $sp $sp 0
jr $ra
#code for main
main:
# Entering a new scope
# Symbols on the symbol table
# println
# return
# Update the stack pointer
addi $sp $sp -0
# println
la $a0 datalabel4
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 0
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 1
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 2
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 3
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 4
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 5
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 6
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 7
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 8
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 9
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function fib
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 10
sw $t1 -8($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal fib
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -12($sp)
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 0
li $v0 10
syscall
# All memory structures are placed after the
# .data assembler directive
.data

newline:	.asciiz "\n"
datalabel4:	.asciiz "This program prints the first 11 numbers of the Fibonacci sequence"
