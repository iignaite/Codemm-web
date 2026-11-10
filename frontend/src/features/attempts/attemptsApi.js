import { request } from "@/lib/api";

const PATH = "/api/attempts";
const RESULTS = "/api/test-results";

export const getAttempts = () => request(PATH);
export const createAttempt = (attempt) => request(PATH, { method: "POST", body: attempt });
export const updateAttempt = (id, attempt) => request(`${PATH}/${id}`, { method: "PUT", body: attempt });
export const deleteAttempt = (id) => request(`${PATH}/${id}`, { method: "DELETE" });

export const getTestResults = (attemptId) => request(`${RESULTS}?attemptId=${attemptId}`);
export const createTestResult = (result) => request(RESULTS, { method: "POST", body: result });
export const updateTestResult = (id, result) => request(`${RESULTS}/${id}`, { method: "PUT", body: result });
export const deleteTestResult = (id) => request(`${RESULTS}/${id}`, { method: "DELETE" });
