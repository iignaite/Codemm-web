import { request } from "@/lib/api";

// Lists used to fill foreign-key dropdowns on the CRUD pages.
export const getUserOptions = () => request("/api/users");
export const getTrackOptions = () => request("/api/tracks");
export const getLessonOptions = () => request("/api/lessons");
export const getGoalOptions = () => request("/api/goals");
export const getActivityOptions = () => request("/api/activities");
export const getTestCaseOptions = (activityId) => request(`/api/test-cases?activityId=${activityId}`);

// Builds an id -> label lookup, e.g. labels(users, "userId", "name").
export const labels = (items, idField, labelField) =>
  Object.fromEntries(items.map((item) => [item[idField], item[labelField]]));
