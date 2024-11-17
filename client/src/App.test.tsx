import { render } from "@testing-library/react";
import App from "./App.tsx";

test("App loads", () => {
  const { container } = render(<App />);
  expect(container).toBeTruthy();
});
