"use client";

import { useCallback, useEffect, useState } from "react";
import TestResultForm from "@/features/attempts/TestResultForm";
import {
  createTestResult,
  deleteTestResult,
  getTestResults,
  updateTestResult,
} from "@/features/attempts/attemptsApi";
import { getTestCases } from "@/features/activities/activitiesApi";

// Test results of one attempt. The test case dropdown lists the attempt's activity's test cases.
export default function TestResults({ attempt }) {
  const { attemptId, activityId } = attempt;
  const [results, setResults] = useState([]);
  const [testCases, setTestCases] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");

  const load = useCallback(async () => {
    try {
      const [resultList, testCaseList] = await Promise.all([
        getTestResults(attemptId),
        getTestCases(activityId),
      ]);
      setResults(resultList);
      setTestCases(testCaseList);
      setError("");
    } catch (err) {
      setError(err.message);
    }
  }, [attemptId, activityId]);

  useEffect(() => {
    setEditing(null);
    load();
  }, [load]);

  const save = async (form) => {
    try {
      const body = { ...form, attemptId };
      if (editing) {
        await updateTestResult(editing.resultId, body);
        setEditing(null);
      } else {
        await createTestResult(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (result) => {
    if (!window.confirm(`Delete result #${result.resultId}?`)) return;
    try {
      await deleteTestResult(result.resultId);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <section className="card">
      <h2>Test results of attempt #{attemptId}</h2>
      {error && <p className="error">{error}</p>}
      <TestResultForm editing={editing} testCases={testCases} onSubmit={save} onCancel={() => setEditing(null)} />
      {results.length === 0 ? (
        <p>No test results yet.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Test case</th>
              <th>Passed</th>
              <th>Actual output</th>
              <th aria-label="Actions" />
            </tr>
          </thead>
          <tbody>
            {results.map((result) => (
              <tr key={result.resultId}>
                <td>{result.resultId}</td>
                <td>#{result.testId}</td>
                <td>{result.passed ? "Yes" : "No"}</td>
                <td><code>{result.actualOutput}</code></td>
                <td className="actions">
                  <button className="secondary" onClick={() => setEditing(result)}>Edit</button>
                  <button className="danger" onClick={() => remove(result)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
