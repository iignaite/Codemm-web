"use client";

import { useCallback, useEffect, useState } from "react";
import AttemptForm from "@/features/attempts/AttemptForm";
import TestResults from "@/features/attempts/TestResults";
import { createAttempt, deleteAttempt, getAttempts, updateAttempt } from "@/features/attempts/attemptsApi";
import { getActivityOptions, getUserOptions, labels } from "@/lib/options";

export default function AttemptsPage() {
  const [attempts, setAttempts] = useState([]);
  const [users, setUsers] = useState([]);
  const [activities, setActivities] = useState([]);
  const [editing, setEditing] = useState(null);
  const [selected, setSelected] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const [attemptList, userList, activityList] = await Promise.all([
        getAttempts(),
        getUserOptions(),
        getActivityOptions(),
      ]);
      setAttempts(attemptList);
      setUsers(userList);
      setActivities(activityList);
      setError("");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load();
  }, [load]);

  const save = async (body) => {
    try {
      if (editing) {
        await updateAttempt(editing.attemptId, body);
        setEditing(null);
      } else {
        await createAttempt(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (attempt) => {
    if (!window.confirm(`Delete attempt #${attempt.attemptId} and its test results?`)) return;
    try {
      await deleteAttempt(attempt.attemptId);
      if (selected?.attemptId === attempt.attemptId) setSelected(null);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  const userNames = labels(users, "userId", "name");
  const statements = labels(activities, "activityId", "statement");

  return (
    <main className="page">
      <h1>Attempts</h1>
      {error && <p className="error">{error}</p>}
      <AttemptForm editing={editing} users={users} activities={activities} onSubmit={save}
        onCancel={() => setEditing(null)} />
      <section className="card">
        {loading ? (
          <p>Loading…</p>
        ) : attempts.length === 0 ? (
          <p>No attempts yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Learner</th>
                <th>Activity</th>
                <th>Attempt no.</th>
                <th>Score</th>
                <th>Submitted</th>
                <th aria-label="Actions" />
              </tr>
            </thead>
            <tbody>
              {attempts.map((attempt) => (
                <tr key={attempt.attemptId}>
                  <td>{attempt.attemptId}</td>
                  <td>{userNames[attempt.userId] ?? attempt.userId}</td>
                  <td>#{attempt.activityId} {statements[attempt.activityId]}</td>
                  <td>{attempt.attemptNo}</td>
                  <td>{attempt.score}</td>
                  <td>{new Date(attempt.submittedAt).toLocaleString()}</td>
                  <td className="actions">
                    <button onClick={() => setSelected(attempt)}>Results</button>
                    <button className="secondary" onClick={() => setEditing(attempt)}>Edit</button>
                    <button className="danger" onClick={() => remove(attempt)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
      {selected && <TestResults key={selected.attemptId} attempt={selected} />}
    </main>
  );
}
