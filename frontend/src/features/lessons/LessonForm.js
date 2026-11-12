"use client";

import { useEffect, useState } from "react";

const EMPTY = { trackId: "", title: "", position: 1, content: "" };

export default function LessonForm({ editing, tracks, defaultTrackId, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing ? { ...editing, content: editing.content ?? "" } : { ...EMPTY, trackId: defaultTrackId ?? "" });
  }, [editing, defaultTrackId]);

  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit({
      trackId: Number(form.trackId),
      title: form.title,
      position: Number(form.position),
      content: form.content,
    });
    if (saved && !editing) {
      setForm({ ...EMPTY, trackId: defaultTrackId ?? "" });
    }
  };

  return (
    <form className="card form" onSubmit={submit}>
      <h2>{editing ? `Edit lesson #${editing.lessonId}` : "New lesson"}</h2>
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
        Title
        <input name="title" value={form.title} onChange={change} maxLength={150} required />
      </label>
      <label>
        Position
        <input name="position" type="number" min={1} value={form.position} onChange={change} required />
      </label>
      <label>
        Content
        <textarea name="content" value={form.content} onChange={change} rows={6} />
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save changes" : "Create lesson"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}