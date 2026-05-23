const BASE = '/wenote/api/admin'

export async function getStats(password) {
  const res = await fetch(`${BASE}/stats`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ password })
  })
  return res.json()
}
