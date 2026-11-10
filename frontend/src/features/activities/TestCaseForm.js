"use client";

import { useEffect, useState } from "react";

const EMPTY = { input: "", expectedOutput: "", hidden: false };

export default function TestCaseForm({ editing, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing
      ? { input: editing.input ?? "", expectedOutput: editing.expectedOutput, hidden: editing.hidden }
      : EMPTY);
  }, [editing]);

  const change = (event) => {
    const { name, type, checked, value } = event.target;
    setForm({ ...form, [name]: type === "checkbox" ? checked : value });
  };

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit(form);
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="form" onSubmit={submit}>
      <h3>{editing ? `Edit test case #${editing.testId}` : "Add test case"}</h3>
      <label>
        Input
        <textarea name="input" value={form.input} onChange={change} rows={2} spellCheck={false} />
      </label>
      <label>
        Expected output
        <textarea name="expectedOutput" value={form.expectedOutput} onChange={change} rows={2}
          spellCheck={false} required />
      </label>
      <label>
        <span>
          <input name="hidden" type="checkbox" checked={form.hidden} onChange={change} /> Hidden (runs only on Check)
        </span>
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save test case" : "Add test case"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
