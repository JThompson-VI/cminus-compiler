# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main
#code for add
add:
# Entering a new scope
# Symbols on the symbol table
# println
# x
# y
# i
# return
# Update the stack pointer
addi $sp $sp -0
# println
# get x offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of x
add $t0 $sp $t0
# load the value of x
lw $t0 0($t0)
# get y offset from the stack pointer.
li $t1 -8
# load offset + sp to get the address of y
add $t1 $sp $t1
# load the value of y
lw $t1 0($t1)
add $t0 $t0 $t1
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 0
jr $ra
#code for main
main:
# Entering a new scope
# Symbols on the symbol table
# a
# println
# b
# return
# Update the stack pointer
addi $sp $sp -0
# println
la $a0 datalabel0
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# calling function add
# store ra
move $t0 $ra
# store t registers
sw $t0 -12($sp)
# Evaluate args and place on the stack
li $t1 3
sw $t1 -16($sp)
li $t1 4
sw $t1 -20($sp)
# update stack pointer
addi $sp $sp -12
# call the function
jal add
# restore stack pointer
addi $sp $sp 12
# restore t regs
lw $t0 -12($sp)
# restore ra
move $ra $t0
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# compute rhs for assignment
li $t1 5
# complete assignment by storing rhs in address
sw $t1 0($t0)

# get b offset from the stack pointer.
li $t0 -8
# load offset + sp to get the address of b
add $t0 $sp $t0
# compute rhs for assignment
li $t1 2
# complete assignment by storing rhs in address
sw $t1 0($t0)

# calling function add
# store ra
move $t0 $ra
# store t registers
sw $t0 -12($sp)
# Evaluate args and place on the stack
# get a offset from the stack pointer.
li $t1 -4
# load offset + sp to get the address of a
add $t1 $sp $t1
# load the value of a
lw $t1 0($t1)
sw $t1 -16($sp)
# get b offset from the stack pointer.
li $t1 -8
# load offset + sp to get the address of b
add $t1 $sp $t1
# load the value of b
lw $t1 0($t1)
sw $t1 -20($sp)
# update stack pointer
addi $sp $sp -12
# call the function
jal add
# restore stack pointer
addi $sp $sp 12
# restore t regs
lw $t0 -12($sp)
# restore ra
move $ra $t0
# exiting scope, restoring sp 
addi $sp $sp 0
li $v0 10
syscall
# All memory structures are placed after the
# .data assembler directive
.data

newline:	.asciiz "\n"
datalabel0:	.asciiz "This program prints 7 7"
