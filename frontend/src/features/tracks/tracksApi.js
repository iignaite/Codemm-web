import { request } from "@/lib/api";

const PATH = "/api/tracks";

export const getTracks = () => request(PATH);
export const getTrack = (id) => request(`${PATH}/${id}`);
export const createTrack = (track) => request(PATH, { method: "POST", body: track });
export const updateTrack = (id, track) => request(`${PATH}/${id}`, { method: "PUT", body: track });
export const deleteTrack = (id) => request(`${PATH}/${id}`, { method: "DELETE" });