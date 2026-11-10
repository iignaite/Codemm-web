"use client";

import { useCallback, useEffect, useState } from "react";
import RoadmapItemForm from "@/features/roadmaps/RoadmapItemForm";
import {
  createRoadmapItem,
  deleteRoadmapItem,
  getRoadmapItems,
  updateRoadmapItem,
} from "@/features/roadmaps/roadmapsApi";
import { labels } from "@/lib/options";

// Items of one roadmap: list, add, edit and delete.
export default function RoadmapItems({ roadmapId, lessons }) {
  const [items, setItems] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");

  const load = useCallback(async () => {
    try {
      setItems(await getRoadmapItems(roadmapId));
      setError("");
    } catch (err) {
      setError(err.message);
    }
  }, [roadmapId]);

  useEffect(() => {
    setEditing(null);
    load();
  }, [load]);

  const save = async (form) => {
    try {
      const body = { ...form, roadmapId };
      if (editing) {
        await updateRoadmapItem(editing.itemId, body);
        setEditing(null);
      } else {
        await createRoadmapItem(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (item) => {
    if (!window.confirm(`Delete item #${item.itemId}?`)) return;
    try {
      await deleteRoadmapItem(item.itemId);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  const lessonTitles = labels(lessons, "lessonId", "title");

  return (
    <section className="card">
      <h2>Items of roadmap #{roadmapId}</h2>
      {error && <p className="error">{error}</p>}
      <RoadmapItemForm editing={editing} lessons={lessons} onSubmit={save} onCancel={() => setEditing(null)} />
      {items.length === 0 ? (
        <p>No items yet.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Position</th>
              <th>Lesson</th>
              <th>Status</th>
              <th aria-label="Actions" />
            </tr>
          </thead>
          <tbody>
            {items.map((item) => (
              <tr key={item.itemId}>
                <td>{item.itemId}</td>
                <td>{item.position}</td>
                <td>{lessonTitles[item.lessonId] ?? item.lessonId}</td>
                <td>{item.status}</td>
                <td className="actions">
                  <button className="secondary" onClick={() => setEditing(item)}>Edit</button>
                  <button className="danger" onClick={() => remove(item)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
}
