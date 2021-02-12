// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

export function downloadFile(name, content) {
  if(!content) return
  const link = document.createElement("a")
  link.style.display = "none"
  link.href = window.URL.createObjectURL(content)
  link.setAttribute("download", name)
}
