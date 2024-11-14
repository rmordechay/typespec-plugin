namespace One {
  model A {}
}

namespace Two {
  using <caret>One;
  alias B = A;
}