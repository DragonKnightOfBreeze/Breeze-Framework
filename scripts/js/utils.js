export function downloadFile(name, content) {
  if(!content) return
  const link = document.createElement("a")
  link.style.display = "none"
  link.href = window.URL.createObjectURL(content)
  link.setAttribute("download", name)
}
