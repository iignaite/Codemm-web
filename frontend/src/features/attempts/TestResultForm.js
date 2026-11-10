"use client";

import { useEffect, useState } from "react";

const EMPTY = { testId: "", passed: false, actualOutput: "" };

export default function TestResultForm({ editing, testCases, onSubmit, onCancel }) {
  const [form, setForm] = useState(EMPTY);

  useEffect(() => {
    setForm(editing
      ? { testId: editing.testId, passed: editing.passed, actualOutput: editing.actualOutput ?? "" }
      : EMPTY);
  }, [editing]);

  const change = (event) => {
    const { name, type, checked, value } = event.target;
    setForm({ ...form, [name]: type === "checkbox" ? checked : value });
  };

  const submit = async (event) => {
    event.preventDefault();
    const saved = await onSubmit({ ...form, testId: Number(form.testId) });
    if (saved && !editing) {
      setForm(EMPTY);
    }
  };

  return (
    <form className="form" onSubmit={submit}>
      <h3>{editing ? `Edit result #${editing.resultId}` : "Add test result"}</h3>
      <label>
        Test case
        <select name="testId" value={form.testId} onChange={change} required>
          <option value="">Select a test case</option>
          {testCases.map((testCase) => (
            <option key={testCase.testId} value={testCase.testId}>
              #{testCase.testId} expects {testCase.expectedOutput}
            </option>
          ))}
        </select>
      </label>
      <label>
        Actual output
        <textarea name="actualOutput" value={form.actualOutput} onChange={change} rows={2} spellCheck={false} />
      </label>
      <label>
        <span>
          <input name="passed" type="checkbox" checked={form.passed} onChange={change} /> Passed
        </span>
      </label>
      <div className="actions">
        <button type="submit">{editing ? "Save result" : "Add result"}</button>
        {editing && <button type="button" className="secondary" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
