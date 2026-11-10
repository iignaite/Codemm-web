"use client";

import { useEffect, useState } from "react";

const EMPTY = { userId: "", activityId: "", code: "", score: 0 };

export default function AttemptForm({ editing, users, activities, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing
      ? { userId: editing.userId, activityId: editing.activityId, code: editing.code, score: editing.score }
      : EMPTY);
  }, [editing]);

  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit({
      userId: Number(form.userId),
      activityId: Number(form.activityId),
      code: form.code,
      score: Number(form.score),
    });
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="card form" onSubmit={submit}>
      <h2>{editing ? `Edit attempt #${editing.attemptId}` : "New attempt"}</h2>
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
        Activity
        <select name="activityId" value={form.activityId} onChange={change} required>
          <option value="">Select an activity</option>
          {activities.map((activity) => (
            <option key={activity.activityId} value={activity.activityId}>
              #{activity.activityId} {activity.statement}
            </option>
          ))}
        </select>
      </label>
      <label>
        Attempt no. <small>(assigned by the server)</small>
        <input value={editing ? editing.attemptNo : "Assigned on save"} readOnly disabled />
      </label>
      <label>
        Code
        <textarea name="code" value={form.code} onChange={change} rows={6} spellCheck={false} required />
      </label>
      <label>
        Score (0–100)
        <input name="score" type="number" min={0} max={100} value={form.score} onChange={change} required />
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save changes" : "Create attempt"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
