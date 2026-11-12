import Link from "next/link";

// Add a link here when you add a page under src/app/.
const LINKS = [
  { href: "/", label: "Home" },
  { href: "/users", label: "Users" },
  { href: "/lessons", label: "Lessons" },
  { href: "/goals", label: "Goals" },
  { href: "/roadmaps", label: "Roadmaps" },
  { href: "/activities", label: "Activities" },
  { href: "/attempts", label: "Attempts" },
  { href: "/tracks", label: "Tracks" },
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
