"use client";

import { useEffect, useState } from "react";

const EMPTY = {
  lessonId: "",
  statement: "",
  starterCode: "",
  referenceSolution: "",
  difficulty: "EASY",
  status: "DRAFT",
};

export default function ActivityForm({ editing, lessons, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing
      ? {
        ...editing,
        starterCode: editing.starterCode ?? "",
        referenceSolution: editing.referenceSolution ?? "",
      }
      : EMPTY);
  }, [editing]);

  const change = (event) => setForm({ ...form, [event.target.name]: event.target.value });

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit({
      lessonId: Number(form.lessonId),
      statement: form.statement,
      starterCode: form.starterCode,
      referenceSolution: form.referenceSolution,
      difficulty: form.difficulty,
      status: form.status,
    });
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="card form" onSubmit={submit}>
      <h2>{editing ? `Edit activity #${editing.activityId}` : "New activity"}</h2>
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
        Problem statement
        <textarea name="statement" value={form.statement} onChange={change} rows={3} required />
      </label>
      <label>
        Starter code
        <textarea name="starterCode" value={form.starterCode} onChange={change} rows={4} spellCheck={false} />
      </label>
      <label>
        Reference solution
        <textarea name="referenceSolution" value={form.referenceSolution} onChange={change} rows={4}
          spellCheck={false} />
      </label>
      <label>
        Difficulty
        <select name="difficulty" value={form.difficulty} onChange={change}>
          <option value="EASY">Easy</option>
          <option value="MEDIUM">Medium</option>
          <option value="HARD">Hard</option>
        </select>
      </label>
      <label>
        Status
        <select name="status" value={form.status} onChange={change}>
          <option value="DRAFT">Draft</option>
          <option value="VALIDATED">Validated</option>
          <option value="REJECTED">Rejected</option>
        </select>
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save changes" : "Create activity"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
