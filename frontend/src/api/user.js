const BASE = '/wenote/api/users'

export async function listUsers() {
  const res = await fetch(BASE)
  return res.json()
}

export async function login(username) {
  const res = await fetch(`${BASE}/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username })
  })
  return res.json()
}
