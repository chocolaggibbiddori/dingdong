/**
 * Validate email format
 *
 * @param {string} email - 검사할 이메일 문자열
 * @returns {boolean} 유효하면 true, 아니면 false
 */
export function isValidEmail(email) {
  if (!email || typeof email !== 'string') {
    return false;
  }

  const emailRegex = /\S+@\S+\.\S+/;
  return emailRegex.test(email);
}
