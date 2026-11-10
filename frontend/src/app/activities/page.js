"use client";

import { useCallback, useEffect, useState } from "react";
import ActivityForm from "@/features/activities/ActivityForm";
import TestCases from "@/features/activities/TestCases";
import {
  createActivity,
  deleteActivity,
  getActivities,
  updateActivity,
} from "@/features/activities/activitiesApi";
import { getLessonOptions, labels } from "@/lib/options";

export default function ActivitiesPage() {
  const [activities, setActivities] = useState([]);
  const [lessons, setLessons] = useState([]);
  const [editing, setEditing] = useState(null);
  const [selectedId, setSelectedId] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const [activityList, lessonList] = await Promise.all([getActivities(), getLessonOptions()]);
      setActivities(activityList);
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
        await updateActivity(editing.activityId, body);
        setEditing(null);
      } else {
        await createActivity(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (activity) => {
    if (!window.confirm(`Delete activity #${activity.activityId}? Its test cases and attempts are deleted too.`)) {
      return;
    }
    try {
      await deleteActivity(activity.activityId);
      if (selectedId === activity.activityId) setSelectedId(null);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  const lessonTitles = labels(lessons, "lessonId", "title");

  return (
    <main className="page">
      <h1>Activities</h1>
      {error && <p className="error">{error}</p>}
      <ActivityForm editing={editing} lessons={lessons} onSubmit={save} onCancel={() => setEditing(null)} />
      <section className="card">
        {loading ? (
          <p>Loading…</p>
        ) : activities.length === 0 ? (
          <p>No activities yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Lesson</th>
                <th>Statement</th>
                <th>Difficulty</th>
                <th>Status</th>
                <th aria-label="Actions" />
              </tr>
            </thead>
            <tbody>
              {activities.map((activity) => (
                <tr key={activity.activityId}>
                  <td>{activity.activityId}</td>
                  <td>{lessonTitles[activity.lessonId] ?? activity.lessonId}</td>
                  <td>{activity.statement}</td>
                  <td>{activity.difficulty}</td>
                  <td>{activity.status}</td>
                  <td className="actions">
                    <button onClick={() => setSelectedId(activity.activityId)}>Tests</button>
                    <button className="secondary" onClick={() => setEditing(activity)}>Edit</button>
                    <button className="danger" onClick={() => remove(activity)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </section>
      {selectedId && <TestCases activityId={selectedId} />}
    </main>
  );
}
