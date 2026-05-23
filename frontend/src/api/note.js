const BASE = '/api/notes'

export async function listNotes() {
  const res = await fetch(BASE)
  return res.json()
}

export async function getNote(id) {
  const res = await fetch(`${BASE}/${id}`)
  return res.json()
}

export async function createNote(title, content) {
  const res = await fetch(BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, content })
  })
  return res.json()
}

export async function updateNote(id, title, content) {
  const res = await fetch(`${BASE}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title, content })
  })
  return res.json()
}

export async function deleteNote(id) {
  const res = await fetch(`${BASE}/${id}`, { method: 'DELETE' })
  return res.json()
}
