/**
 *
 * @returns {boolean}
 */
String.prototype.isEmpty = function() {
  return !!this || this.length === 0
}

/**
 *
 * @returns {boolean}
 */
String.prototype.isNotEmpty = function() {
  return !!this && this.length > 0
}

/**
 *
 * @param str {string}
 * @returns {boolean}
 */
String.prototype.contains = function(str) {
  return !!this && this.indexOf(str) !== -1
}

/**
 *
 * @returns
 */
String.prototype.deserializeJson = function() {
  return JSON.parse(this)
}
