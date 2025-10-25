/**
 * Validate Korean name format
 * @param {string} name - Korean name to validate
 * @returns {boolean} true if valid, otherwise false
 */
export function isValidKoreanName(name) {
  return /^[가-힣]{2,10}$/.test(name);
}
