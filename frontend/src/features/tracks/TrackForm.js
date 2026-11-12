"use client";

import { useEffect, useState } from "react";

const EMPTY = { title: "", language: "java", description: "" };

export default function TrackForm({ editing, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing ? { ...editing, description: editing.description ?? "" } : EMPTY);
  }, [editing]);

  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit({
      title: form.title,
      language: form.language,
      description: form.description,
    });
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="card form" onSubmit={submit}>
      <h2>{editing ? `Edit track #${editing.trackId}` : "New track"}</h2>
      <label>
        Title
        <input name="title" value={form.title} onChange={change} maxLength={100} required />
      </label>
      <label>
        Language
        <select name="language" value={form.language} onChange={change}>
          <option value="java">Java</option>
          <option value="python">Python</option>
          <option value="javascript">JavaScript</option>
        </select>
      </label>
      <label>
        Description
        <textarea name="description" value={form.description} onChange={change} rows={3} />
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save changes" : "Create track"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}