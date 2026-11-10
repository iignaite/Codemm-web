import Link from "next/link";

// Add a link here when you add a page under src/app/.
const LINKS = [
  { href: "/", label: "Home" },
  { href: "/users", label: "Users" },
];

export default function NavBar() {
  return (
    <nav className="nav">
      <strong>Codemm</strong>
      {LINKS.map((link) => (
        <Link key={link.href} href={link.href}>
          {link.label}
        </Link>
      ))}
    </nav>
  );
}
