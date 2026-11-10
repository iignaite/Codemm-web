// Small fetch wrapper shared by every feature's API file.
// Throws an Error whose message comes from the back end's ApiError body.

const BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080";

export async function request(path, { method = "GET", body } = {}) {
  const response = await fetch(`${BASE_URL}${path}`, {
    method,
    headers: body ? { "Content-Type": "application/json" } : undefined,
    body: body ? JSON.stringify(body) : undefined,
  });

  if (response.status === 204) {
    return null;
  }

  const data = await response.json().catch(() => null);
  if (!response.ok) {
    const fieldErrors = data?.fieldErrors
      ? Object.entries(data.fieldErrors).map(([field, message]) => `${field}: ${message}`).join(", ")
      : "";
    throw new Error(fieldErrors || data?.message || `Request failed (${response.status})`);
  }
  return data;
}
