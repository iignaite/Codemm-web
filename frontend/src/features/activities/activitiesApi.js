import { request } from "@/lib/api";

const PATH = "/api/activities";
const TESTS = "/api/test-cases";

export const getActivities = () => request(PATH);
export const createActivity = (activity) => request(PATH, { method: "POST", body: activity });
export const updateActivity = (id, activity) => request(`${PATH}/${id}`, { method: "PUT", body: activity });
export const deleteActivity = (id) => request(`${PATH}/${id}`, { method: "DELETE" });

export const getTestCases = (activityId) => request(`${TESTS}?activityId=${activityId}`);
export const createTestCase = (testCase) => request(TESTS, { method: "POST", body: testCase });
export const updateTestCase = (id, testCase) => request(`${TESTS}/${id}`, { method: "PUT", body: testCase });
export const deleteTestCase = (id) => request(`${TESTS}/${id}`, { method: "DELETE" });
