"use client";

import { useCallback, useEffect, useState } from "react";
import LessonForm from "@/features/lessons/LessonForm";
import { createLesson, deleteLesson, getLessons, updateLesson } from "@/features/lessons/lessonsApi";
import { getTrackOptions, labels } from "@/lib/options";

export default function LessonsPage() {
  const [lessons, setLessons] = useState([]);
  const [tracks, setTracks] = useState([]);
  const [trackFilter, setTrackFilter] = useState("");
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const [lessonList, trackList] = await Promise.all([getLessons(trackFilter), getTrackOptions()]);
      setLessons(lessonList);
      setTracks(trackList);
      setError("");
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [trackFilter]);

  useEffect(() => {
    load();
  }, [load]);

  const save = async (body) => {
    try {
      if (editing) {
        await updateLesson(editing.lessonId, body);
        setEditing(null);
      } else {
        await createLesson(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (lesson) => {
    if (!window.confirm(`Delete lesson "${lesson.title}"? Its activities and roadmap items are deleted too.`)) return;
    try {
      await deleteLesson(lesson.lessonId);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  const trackTitles = labels(tracks, "trackId", "title");
  const sorted = [...lessons].sort((a, b) => a.trackId - b.trackId || a.position - b.position);

  return (
    <main className="page">
      <h1>Lessons</h1>
      {error && <p className="error">{error}</p>}
      <LessonForm editing={editing} tracks={tracks} defaultTrackId={trackFilter} onSubmit={save}
        onCancel={() => setEditing(null)} />
      <section className="card form">
        <label>
          Show lessons for
          <select value={trackFilter} onChange={(event) => setTrackFilter(event.target.value)}>
            <option value="">All tracks</option>
            {tracks.map((track) => (
              <option key={track.trackId} value={track.trackId}>{track.title}</option>
            ))}
          </select>
        </label>
      </section>
      <section className="card">
        {loading ? (
          <p>Loading…</p>
        ) : sorted.length === 0 ? (
          <p>No lessons yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Track</th>
                <th>Position</th>
                <th>Title</th>
                <th aria-label="Actions" />
              </tr>
            </thead>
            <tbody>
              {sorted.map((lesson) => (
                <tr key={lesson.lessonId}>
                  <td>{lesson.lessonId}</td>
                  <td>{trackTitles[lesson.trackId] ?? lesson.trackId}</td>
                  <td>{lesson.position}</td>
                  <td>{lesson.title}</td>
                  <td className="actions">
                    <button className="secondary" onClick={() => setEditing(lesson)}>Edit</button>
                    <button className="danger" onClick={() => remove(lesson)}>Delete</button>
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