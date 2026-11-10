"use client";

import { useCallback, useEffect, useState } from "react";
import GoalForm from "@/features/goals/GoalForm";
import { createGoal, deleteGoal, getGoals, updateGoal } from "@/features/goals/goalsApi";
import { getTrackOptions, getUserOptions, labels } from "@/lib/options";

export default function GoalsPage() {
  const [goals, setGoals] = useState([]);
  const [users, setUsers] = useState([]);
  const [tracks, setTracks] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const [goalList, userList, trackList] = await Promise.all([getGoals(), getUserOptions(), getTrackOptions()]);
      setGoals(goalList);
      setUsers(userList);
      setTracks(trackList);
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
        await updateGoal(editing.goalId, body);
        setEditing(null);
      } else {
        await createGoal(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (goal) => {
    if (!window.confirm(`Delete goal #${goal.goalId}? Its roadmaps are deleted too.`)) return;
    try {
      await deleteGoal(goal.goalId);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  const userNames = labels(users, "userId", "name");
  const trackTitles = labels(tracks, "trackId", "title");

  return (
    <main className="page">
      <h1>Goals</h1>
      {error && <p className="error">{error}</p>}
      <GoalForm editing={editing} users={users} tracks={tracks} onSubmit={save}
        onCancel={() => setEditing(null)} />
      <section className="card">
        {loading ? (
          <p>Loading…</p>
        ) : goals.length === 0 ? (
          <p>No goals yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Learner</th>
                <th>Track</th>
                <th>Goal</th>
                <th>Depth</th>
                <th aria-label="Actions" />
              </tr>
            </thead>
            <tbody>
              {goals.map((goal) => (
                <tr key={goal.goalId}>
                  <td>{goal.goalId}</td>
                  <td>{userNames[goal.userId] ?? goal.userId}</td>
                  <td>{trackTitles[goal.trackId] ?? goal.trackId}</td>
                  <td>{goal.description}</td>
                  <td>{goal.depth}</td>
                  <td className="actions">
                    <button className="secondary" onClick={() => setEditing(goal)}>Edit</button>
                    <button className="danger" onClick={() => remove(goal)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
    </main>
  );
}
