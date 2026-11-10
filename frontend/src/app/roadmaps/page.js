"use client";

import { useCallback, useEffect, useState } from "react";
import RoadmapForm from "@/features/roadmaps/RoadmapForm";
import RoadmapItems from "@/features/roadmaps/RoadmapItems";
import { createRoadmap, deleteRoadmap, getRoadmaps, updateRoadmap } from "@/features/roadmaps/roadmapsApi";
import { getGoalOptions, getLessonOptions, labels } from "@/lib/options";

export default function RoadmapsPage() {
  const [roadmaps, setRoadmaps] = useState([]);
  const [goals, setGoals] = useState([]);
  const [lessons, setLessons] = useState([]);
  const [editing, setEditing] = useState(null);
  const [selectedId, setSelectedId] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const [roadmapList, goalList, lessonList] = await Promise.all([
        getRoadmaps(),
        getGoalOptions(),
        getLessonOptions(),
      ]);
      setRoadmaps(roadmapList);
      setGoals(goalList);
      setLessons(lessonList);
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
        await updateRoadmap(editing.roadmapId, body);
        setEditing(null);
      } else {
        await createRoadmap(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (roadmap) => {
    if (!window.confirm(`Delete roadmap #${roadmap.roadmapId} and its items?`)) return;
    try {
      await deleteRoadmap(roadmap.roadmapId);
      if (selectedId === roadmap.roadmapId) setSelectedId(null);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  const goalDescriptions = labels(goals, "goalId", "description");

  return (
    <main className="page">
      <h1>Roadmaps</h1>
      {error && <p className="error">{error}</p>}
      <RoadmapForm editing={editing} goals={goals} onSubmit={save} onCancel={() => setEditing(null)} />
      <section className="card">
        {loading ? (
          <p>Loading…</p>
        ) : roadmaps.length === 0 ? (
          <p>No roadmaps yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Goal</th>
                <th>Status</th>
                <th>Created</th>
                <th aria-label="Actions" />
              </tr>
            </thead>
            <tbody>
              {roadmaps.map((roadmap) => (
                <tr key={roadmap.roadmapId}>
                  <td>{roadmap.roadmapId}</td>
                  <td>#{roadmap.goalId} {goalDescriptions[roadmap.goalId]}</td>
                  <td>{roadmap.status}</td>
                  <td>{new Date(roadmap.createdAt).toLocaleString()}</td>
                  <td className="actions">
                    <button onClick={() => setSelectedId(roadmap.roadmapId)}>Items</button>
                    <button className="secondary" onClick={() => setEditing(roadmap)}>Edit</button>
                    <button className="danger" onClick={() => remove(roadmap)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
      {selectedId && <RoadmapItems roadmapId={selectedId} lessons={lessons} />}
    </main>
  );
}
