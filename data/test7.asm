# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main
#code for identity
identity:
# Entering a new scope
# Symbols on the symbol table
# println
# x
# return
# Update the stack pointer
addi $sp $sp -0
# get x offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of x
add $t0 $sp $t0
# load the value of x
lw $t0 0($t0)
# store the return value on the stack
sw $t0 -8($sp)
jr $ra
# exiting scope, restoring sp 
addi $sp $sp 0
jr $ra
#code for add
add:
# Entering a new scope
# Symbols on the symbol table
# println
# x
# y
# return
# Update the stack pointer
addi $sp $sp -0
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
# store the return value on the stack
sw $t0 -12($sp)
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
la $a0 datalabel0
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# println
# calling function identity
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
jal identity
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
# calling function add
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
li $t1 3
sw $t1 -8($sp)
li $t1 4
sw $t1 -12($sp)
# update stack pointer
addi $sp $sp -4
# call the function
jal add
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# get return value off the stack
lw $t0 -16($sp)
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
datalabel0:	.asciiz "This program prints 7 7"
