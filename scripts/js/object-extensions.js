// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

/**
 *
 * @param key
 * @returns {boolean}
 */
Object.prototype.containsKey = function(key) {
  for(const k of Object.keys(this)) {
    if(k === key) return true
  }
  return false
}

/**
 *
 * @param value
 * @returns {boolean}
 */
Object.prototype.containsValue = function(value) {
  for(const k of Object.values(this)) {
    if(k === value) return true
  }
  return false
}

/**
 *
 * @returns {string}
 */
Object.prototype.serializeJson = function() {
  return JSON.stringify(this)
}
