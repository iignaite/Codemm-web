"use client";

import { useEffect, useState } from "react";

const EMPTY = { goalId: "", status: "ACTIVE" };

export default function RoadmapForm({ editing, goals, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing ? { goalId: editing.goalId, status: editing.status } : EMPTY);
  }, [editing]);

  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit({ goalId: Number(form.goalId), status: form.status });
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="card form" onSubmit={submit}>
      <h2>{editing ? `Edit roadmap #${editing.roadmapId}` : "New roadmap"}</h2>
      <label>
        Goal
        <select name="goalId" value={form.goalId} onChange={change} required>
          <option value="">Select a goal</option>
          {goals.map((goal) => (
            <option key={goal.goalId} value={goal.goalId}>#{goal.goalId} {goal.description}</option>
          ))}
        </select>
      </label>
      <label>
        Status
        <select name="status" value={form.status} onChange={change}>
          <option value="ACTIVE">Active</option>
          <option value="ARCHIVED">Archived</option>
        </select>
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save changes" : "Create roadmap"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
