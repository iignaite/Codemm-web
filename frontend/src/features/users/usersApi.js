import { request } from "@/lib/api";

const PATH = "/api/users";

export const getUsers = () => request(PATH);
export const getUser = (id) => request(`${PATH}/${id}`);
export const createUser = (user) => request(PATH, { method: "POST", body: user });
export const updateUser = (id, user) => request(`${PATH}/${id}`, { method: "PUT", body: user });
export const deleteUser = (id) => request(`${PATH}/${id}`, { method: "DELETE" });
