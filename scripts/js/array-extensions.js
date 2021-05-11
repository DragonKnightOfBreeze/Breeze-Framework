// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

/**
 *
 * @param element
 * @returns {boolean}
 */
Array.prototype.contains = function(element) {
  return this.includes(element)
}

/**
 *
 * @returns {string}
 */
Array.prototype.serializeJson = function() {
  return JSON.stringify(this)
}
