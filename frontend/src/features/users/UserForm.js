"use client";

import { useEffect, useState } from "react";

const EMPTY = { name: "", email: "", password: "", role: "LEARNER" };

export default function UserForm({ editing, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing ? { ...editing, password: "" } : EMPTY);
  }, [editing]);

  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit(form);
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="card form" onSubmit={submit}>
      <h2>{editing ? `Edit user #${editing.userId}` : "New user"}</h2>
      <label>
        Name
        <input name="name" value={form.name} onChange={change} required />
      </label>
      <label>
        Email
        <input name="email" type="email" value={form.email} onChange={change} required />
      </label>
      <label>
        Password {editing && <small>(leave blank to keep the current one)</small>}
        <input name="password" type="password" value={form.password} onChange={change}
          minLength={8} required={!editing} />
      </label>
      <label>
        Role
        <select name="role" value={form.role} onChange={change}>
          <option value="LEARNER">Learner</option>
          <option value="INSTRUCTOR">Instructor-consultant</option>
          <option value="ADMIN">Administrator</option>
        </select>
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save changes" : "Create user"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
