"use client";

import { useCallback, useEffect, useState } from "react";
import TestCaseForm from "@/features/activities/TestCaseForm";
import { createTestCase, deleteTestCase, getTestCases, updateTestCase } from "@/features/activities/activitiesApi";

// Test cases of one activity: list, add, edit and delete.
export default function TestCases({ activityId }) {
  const [testCases, setTestCases] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");

  const load = useCallback(async () => {
    try {
      setTestCases(await getTestCases(activityId));
      setError("");
    } catch (err) {
      setError(err.message);
    }
  }, [activityId]);

  useEffect(() => {
    setEditing(null);
    load();
  }, [load]);

  const save = async (form) => {
    try {
      const body = { ...form, activityId };
      if (editing) {
        await updateTestCase(editing.testId, body);
        setEditing(null);
      } else {
        await createTestCase(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (testCase) => {
    if (!window.confirm(`Delete test case #${testCase.testId}?`)) return;
    try {
      await deleteTestCase(testCase.testId);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <section className="card">
      <h2>Test cases of activity #{activityId}</h2>
      {error && <p className="error">{error}</p>}
      <TestCaseForm editing={editing} onSubmit={save} onCancel={() => setEditing(null)} />
      {testCases.length === 0 ? (
        <p>No test cases yet.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Input</th>
              <th>Expected output</th>
              <th>Hidden</th>
              <th aria-label="Actions" />
            </tr>
          </thead>
          <tbody>
            {testCases.map((testCase) => (
              <tr key={testCase.testId}>
                <td>{testCase.testId}</td>
                <td><code>{testCase.input}</code></td>
                <td><code>{testCase.expectedOutput}</code></td>
                <td>{testCase.hidden ? "Yes" : "No"}</td>
                <td className="actions">
                  <button className="secondary" onClick={() => setEditing(testCase)}>Edit</button>
                  <button className="danger" onClick={() => remove(testCase)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
