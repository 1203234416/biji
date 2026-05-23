const BASE = '/wenote/api/notes'

function userId() {
  return localStorage.getItem('biji_user_id') || ''
}

export async function listNotes() {
  const res = await fetch(`${BASE}?userId=${userId()}`)
  return res.json()
}

export async function getNote(id) {
  const res = await fetch(`${BASE}/${id}?userId=${userId()}`)
  return res.json()
}

export async function createNote(title, content) {
  const res = await fetch(BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userId: userId(), title, content })
  })
  return res.json()
}

export async function updateNote(id, title, content) {
  const res = await fetch(`${BASE}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ userId: userId(), title, content })
  })
  return res.json()
}

export async function deleteNote(id) {
  const res = await fetch(`${BASE}/${id}?userId=${userId()}`, { method: 'DELETE' })
  return res.json()
}
