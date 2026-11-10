"use client";

import { useEffect, useState } from "react";

const EMPTY = { lessonId: "", position: 1, status: "LOCKED" };

export default function RoadmapItemForm({ editing, lessons, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing ? { lessonId: editing.lessonId, position: editing.position, status: editing.status } : EMPTY);
  }, [editing]);

  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit({
      lessonId: Number(form.lessonId),
      position: Number(form.position),
      status: form.status,
    });
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="form" onSubmit={submit}>
      <h3>{editing ? `Edit item #${editing.itemId}` : "Add item"}</h3>
      <label>
        Lesson
        <select name="lessonId" value={form.lessonId} onChange={change} required>
          <option value="">Select a lesson</option>
          {lessons.map((lesson) => (
            <option key={lesson.lessonId} value={lesson.lessonId}>{lesson.title}</option>
          ))}
        </select>
      </label>
      <label>
        Position
        <input name="position" type="number" min={1} value={form.position} onChange={change} required />
      </label>
      <label>
        Status
        <select name="status" value={form.status} onChange={change}>
          <option value="LOCKED">Locked</option>
          <option value="CURRENT">Current</option>
          <option value="DONE">Done</option>
        </select>
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save item" : "Add item"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
