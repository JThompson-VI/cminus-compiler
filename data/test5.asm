# All program code is placed after the
# .text assembler directive
.text

# Declare main as a global function
.globl	main

j main
#code for foo
foo:
# Entering a new scope
# Symbols on the symbol table
# println
# return
# Update the stack pointer
addi $sp $sp -0
# println
li $t0 7
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 0
jr $ra
#code for fum
fum:
# Entering a new scope
# Symbols on the symbol table
# a
# println
# b
# return
# Update the stack pointer
addi $sp $sp -0
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# compute rhs for assignment
li $t1 9
# complete assignment by storing rhs in address
sw $t1 0($t0)

# get b offset from the stack pointer.
li $t0 -8
# load offset + sp to get the address of b
add $t0 $sp $t0
# compute rhs for assignment
li $t1 12
# complete assignment by storing rhs in address
sw $t1 0($t0)

# println
# get b offset from the stack pointer.
li $t0 -8
# load offset + sp to get the address of b
add $t0 $sp $t0
# load the value of b
lw $t0 0($t0)
# get a offset from the stack pointer.
li $t1 -4
# load offset + sp to get the address of a
add $t1 $sp $t1
# load the value of a
lw $t1 0($t1)
sub $t0 $t0 $t1
li $t1 4
add $t0 $t0 $t1
move $a0 $t0
li $v0 1
syscall
la $a0 newline
li $v0 4
syscall

# calling function foo
# store ra
move $t0 $ra
# store t registers
sw $t0 -12($sp)
# Evaluate args and place on the stack
# update stack pointer
addi $sp $sp -12
# call the function
jal foo
# restore stack pointer
addi $sp $sp 12
# restore t regs
lw $t0 -12($sp)
# restore ra
move $ra $t0
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

# calling function foo
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
# update stack pointer
addi $sp $sp -4
# call the function
jal foo
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
# restore ra
move $ra $t0
# calling function fum
# store ra
move $t0 $ra
# store t registers
sw $t0 -4($sp)
# Evaluate args and place on the stack
# update stack pointer
addi $sp $sp -4
# call the function
jal fum
# restore stack pointer
addi $sp $sp 4
# restore t regs
lw $t0 -4($sp)
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
datalabel0:	.asciiz "This program prints 7 7 7"
