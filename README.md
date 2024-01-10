## Frontend

To give you a feel for the cababilities of this front end here is an implementation of recursive Fibonacci:

```c
int fib(int i) {
  if (i == 0) return 1;
  if (i == 1) return 1;
  return fib(i-1) + fib(i-2);
}

void main() {
  println("This program prints the first 11 numbers of the Fibonacci sequence");
  println(fib(0)); // 1
  println(fib(1)); // 1
  println(fib(2)); // 2
  println(fib(3)); // 3
  println(fib(4)); // 5
  println(fib(5)); // 8
  println(fib(6)); // 13
  println(fib(7)); // 21
  println(fib(8)); // 34
  println(fib(9)); // 55
  println(fib(10)); // 89
}
```

Check the data directory `test10.asm` to see the compiler output for this program.

### grammar generation

This compiler uses ANTLR to generate the lexical analyzer and parser.

## Backend

This compiler currently only supports generation of mips assembly it has very few optimizations. The backend, for simplicity, uses the minimal number of registers.
