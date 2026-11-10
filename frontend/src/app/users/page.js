"use client";

// Reference CRUD page. Copy this layout (page + feature folder) for every other entity.

import { useCallback, useEffect, useState } from "react";
import UserForm from "@/features/users/UserForm";
import { createUser, deleteUser, getUsers, updateUser } from "@/features/users/usersApi";

export default function UsersPage() {
  const [users, setUsers] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      setUsers(await getUsers());
      setError("");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  const save = async (form) => {
    try {
      const body = { ...form, password: form.password || null };
      if (editing) {
        await updateUser(editing.userId, body);
        setEditing(null);
      } else {
        await createUser(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (user) => {
    if (!window.confirm(`Delete ${user.name}?`)) return;
    try {
      await deleteUser(user.userId);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <main className="page">
      <h1>Users</h1>
      {error && <p className="error">{error}</p>}
      <UserForm editing={editing} onSubmit={save} onCancel={() => setEditing(null)} />
      <section className="card">
        {loading ? (
          <p>Loading…</p>
        ) : users.length === 0 ? (
          <p>No users yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th aria-label="Actions" />
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.userId}>
                  <td>{user.userId}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.role}</td>
                  <td className="actions">
                    <button className="secondary" onClick={() => setEditing(user)}>Edit</button>
                    <button className="danger" onClick={() => remove(user)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
    </main>
  );
}
