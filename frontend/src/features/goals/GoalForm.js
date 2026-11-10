"use client";

import { useEffect, useState } from "react";

const EMPTY = { userId: "", trackId: "", description: "", priorKnowledge: "", depth: "STANDARD" };

export default function GoalForm({ editing, users, tracks, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing ? { ...editing, priorKnowledge: editing.priorKnowledge ?? "" } : EMPTY);
  }, [editing]);

  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit({
      userId: Number(form.userId),
      trackId: Number(form.trackId),
      description: form.description,
      priorKnowledge: form.priorKnowledge,
      depth: form.depth,
    });
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="card form" onSubmit={submit}>
      <h2>{editing ? `Edit goal #${editing.goalId}` : "New goal"}</h2>
      <label>
        Learner
        <select name="userId" value={form.userId} onChange={change} required>
          <option value="">Select a user</option>
          {users.map((user) => (
            <option key={user.userId} value={user.userId}>{user.name}</option>
          ))}
        </select>
      </label>
      <label>
        Track
        <select name="trackId" value={form.trackId} onChange={change} required>
          <option value="">Select a track</option>
          {tracks.map((track) => (
            <option key={track.trackId} value={track.trackId}>{track.title}</option>
          ))}
        </select>
      </label>
      <label>
        Goal
        <textarea name="description" value={form.description} onChange={change} rows={3} required />
      </label>
      <label>
        Prior knowledge
        <textarea name="priorKnowledge" value={form.priorKnowledge} onChange={change} rows={2} />
      </label>
      <label>
        Depth
        <select name="depth" value={form.depth} onChange={change}>
          <option value="OVERVIEW">Overview</option>
          <option value="STANDARD">Standard</option>
          <option value="IN_DEPTH">In depth</option>
        </select>
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save changes" : "Create goal"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
