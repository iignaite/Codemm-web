import { request } from "@/lib/api";

const PATH = "/api/goals";

export const getGoals = () => request(PATH);
export const createGoal = (goal) => request(PATH, { method: "POST", body: goal });
export const updateGoal = (id, goal) => request(`${PATH}/${id}`, { method: "PUT", body: goal });
export const deleteGoal = (id) => request(`${PATH}/${id}`, { method: "DELETE" });
