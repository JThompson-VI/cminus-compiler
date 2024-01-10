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
# return
# Update the stack pointer
addi $sp $sp -0
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# compute rhs for assignment
li $t1 3
# complete assignment by storing rhs in address
sw $t1 0($t0)

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
# load the value of a
lw $t0 0($t0)
li $t1 4
slt $t0 $t0 $t1
beq $t0 $zero datalabel1
# Entering a new scope
# Symbols on the symbol table
# a
# println
# return
# Update the stack pointer
addi $sp $sp -4
# println
la $a0 datalabel3
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 4
j datalabel2
datalabel1:
datalabel2:
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# load the value of a
lw $t0 0($t0)
li $t1 4
slt $t0 $t1 $t0
beq $t0 $zero datalabel4
# Entering a new scope
# Symbols on the symbol table
# a
# println
# return
# Update the stack pointer
addi $sp $sp -4
# println
la $a0 datalabel6
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 4
j datalabel5
datalabel4:
datalabel5:
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# load the value of a
lw $t0 0($t0)
li $t1 4
slt $t0 $t1 $t0
beq $t0 $zero datalabel7
# Entering a new scope
# Symbols on the symbol table
# a
# println
# return
# Update the stack pointer
addi $sp $sp -4
# println
la $a0 datalabel9
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 4
j datalabel8
datalabel7:
# Entering a new scope
# Symbols on the symbol table
# a
# println
# return
# Update the stack pointer
addi $sp $sp -4
# println
la $a0 datalabel10
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 4
datalabel8:
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# load the value of a
lw $t0 0($t0)
li $t1 3
slt $t0 $t1 $t0
subi $t0 $t0 1
beq $t0 $zero datalabel11
# Entering a new scope
# Symbols on the symbol table
# a
# println
# return
# Update the stack pointer
addi $sp $sp -4
# println
la $a0 datalabel13
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 4
j datalabel12
datalabel11:
datalabel12:
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# load the value of a
lw $t0 0($t0)
li $t1 3
xor $t0 $t0 $t1
slti $t0 $t0 1
beq $t0 $zero datalabel14
# Entering a new scope
# Symbols on the symbol table
# a
# println
# return
# Update the stack pointer
addi $sp $sp -4
# println
la $a0 datalabel16
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 4
j datalabel15
datalabel14:
datalabel15:
# get a offset from the stack pointer.
li $t0 -4
# load offset + sp to get the address of a
add $t0 $sp $t0
# load the value of a
lw $t0 0($t0)
li $t1 4
slt $t0 $t0 $t1
subi $t0 $t0 1
beq $t0 $zero datalabel17
# Entering a new scope
# Symbols on the symbol table
# a
# println
# return
# Update the stack pointer
addi $sp $sp -4
# println
la $a0 datalabel19
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 4
j datalabel18
datalabel17:
# Entering a new scope
# Symbols on the symbol table
# a
# println
# return
# Update the stack pointer
addi $sp $sp -4
# println
la $a0 datalabel20
li $v0 4
syscall
la $a0 newline
li $v0 4
syscall

# exiting scope, restoring sp 
addi $sp $sp 4
datalabel18:
# exiting scope, restoring sp 
addi $sp $sp 0
li $v0 10
syscall
# All memory structures are placed after the
# .data assembler directive
.data

newline:	.asciiz "\n"
datalabel0:	.asciiz "This program prints [1..5] correct."
datalabel3:	.asciiz "1 correct"
datalabel6:	.asciiz "2 not correct"
datalabel9:	.asciiz "2 not correct"
datalabel10:	.asciiz "2 correct"
datalabel13:	.asciiz "3 correct"
datalabel16:	.asciiz "4 correct"
datalabel19:	.asciiz "5 not correct"
datalabel20:	.asciiz "5 correct"
