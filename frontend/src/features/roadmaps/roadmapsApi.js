import { request } from "@/lib/api";

const PATH = "/api/roadmaps";
const ITEMS = "/api/roadmap-items";

export const getRoadmaps = () => request(PATH);
export const createRoadmap = (roadmap) => request(PATH, { method: "POST", body: roadmap });
export const updateRoadmap = (id, roadmap) => request(`${PATH}/${id}`, { method: "PUT", body: roadmap });
export const deleteRoadmap = (id) => request(`${PATH}/${id}`, { method: "DELETE" });

export const getRoadmapItems = (roadmapId) => request(`${ITEMS}?roadmapId=${roadmapId}`);
export const createRoadmapItem = (item) => request(ITEMS, { method: "POST", body: item });
export const updateRoadmapItem = (id, item) => request(`${ITEMS}/${id}`, { method: "PUT", body: item });
export const deleteRoadmapItem = (id) => request(`${ITEMS}/${id}`, { method: "DELETE" });
