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
          <li>
             <Link href="/lessons">Lessons</Link>
           </li>
        <li>
          <Link href="/goals">Goals</Link>
        </li>
        <li>
          <Link href="/roadmaps">Roadmaps and roadmap items</Link>
        </li>
        <li>
          <Link href="/activities">Activities and test cases</Link>
        </li>
        <li>
          <Link href="/attempts">Attempts and test results</Link>
        </li>
        <li>
          <Link href="/tracks">Tracks</Link>
        </li>
      </ul>
    </main>
  );
}
