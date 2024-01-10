# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main
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

# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# compute rhs for assignment
li $t1 3
# complete assignment by storing rhs in address
sw $t1 0($t0)

# get b offset from the stack pointer.
li $t0 -8
# load offset + sp to get the address of b
add $t0 $sp $t0
# compute rhs for assignment
li $t1 4
# complete assignment by storing rhs in address
sw $t1 0($t0)

# println
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# load the value of a
lw $t0 0($t0)
# get b offset from the stack pointer.
li $t1 -8
# load offset + sp to get the address of b
add $t1 $sp $t1
# load the value of b
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
li $v0 10
syscall
# All memory structures are placed after the
# .data assembler directive
.data

newline:	.asciiz "\n"
datalabel0:	.asciiz "This program prints the number 7"
