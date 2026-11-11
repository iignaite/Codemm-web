import { request } from "@/lib/api";

const PATH = "/api/lessons";


export const getLessons = (trackId) => request(trackId ? `${PATH}?trackId=${trackId}` : PATH);
export const getLesson = (id) => request(`${PATH}/${id}`);
export const createLesson = (lesson) => request(PATH, { method: "POST", body: lesson });
export const updateLesson = (id, lesson) => request(`${PATH}/${id}`, { method: "PUT", body: lesson });
export const deleteLesson = (id) => request(`${PATH}/${id}`, { method: "DELETE" });