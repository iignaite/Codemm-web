"use client";

import { useCallback, useEffect, useState } from "react";
import TrackForm from "@/features/tracks/TrackForm";
import { createTrack, deleteTrack, getTracks, updateTrack } from "@/features/tracks/tracksApi";

export default function TracksPage() {
  const [tracks, setTracks] = useState([]);
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      setTracks(await getTracks());
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
        await updateTrack(editing.trackId, body);
        setEditing(null);
      } else {
        await createTrack(body);
      }
      await load();
      return true;
    } catch (err) {
      setError(err.message);
      return false;
    }
  };

  const remove = async (track) => {
    if (!window.confirm(`Delete ${track.title}? Its lessons and goals are deleted too.`)) return;
    try {
      await deleteTrack(track.trackId);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <main className="page">
      <h1>Tracks</h1>
      {error && <p className="error">{error}</p>}
      <TrackForm editing={editing} onSubmit={save} onCancel={() => setEditing(null)} />
      <section className="card">
        {loading ? (
          <p>Loading…</p>
        ) : tracks.length === 0 ? (
          <p>No tracks yet.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Language</th>
                <th>Description</th>
                <th aria-label="Actions" />
              </tr>
            </thead>
            <tbody>
              {tracks.map((track) => (
                <tr key={track.trackId}>
                  <td>{track.trackId}</td>
                  <td>{track.title}</td>
                  <td>{track.language}</td>
                  <td>{track.description}</td>
                  <td className="actions">
                    <button className="secondary" onClick={() => setEditing(track)}>Edit</button>
                    <button className="danger" onClick={() => remove(track)}>Delete</button>
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