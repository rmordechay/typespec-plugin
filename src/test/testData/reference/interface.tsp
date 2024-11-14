interface A {
  a(): string;
}

interface C extends <caret>A, B {
  c(): string;
}