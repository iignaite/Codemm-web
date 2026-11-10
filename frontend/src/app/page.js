import Link from "next/link";

export default function Home() {
  return (
    <main className="page">
      <h1>Codemm</h1>
      <p>Software Increment 1: create, read, update and delete the core entities.</p>
      <ul>
        <li>
          <Link href="/users">Users</Link>
        </li>
      </ul>
    </main>
  );
}
