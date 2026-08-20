import { render, screen } from "@testing-library/react";
import App from "./App";

jest.mock("./Routes/Navbar", () => () => (
  <div data-testid="navbar">Navbar</div>
));

jest.mock("./Routes/Router", () => () => (
  <div data-testid="router">Application Routes</div>
));

jest.mock("./Routes/Footer", () => () => (
  <div data-testid="footer">Footer</div>
));

test("renders the BookMyShow application structure", () => {
  render(<App />);

  expect(screen.getByTestId("navbar")).toBeInTheDocument();
  expect(screen.getByTestId("router")).toBeInTheDocument();
  expect(screen.getByTestId("footer")).toBeInTheDocument();
});
