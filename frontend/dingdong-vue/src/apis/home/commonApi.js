export default {
  sendEmailCode(email) {
    // return apiClient.post('/email/verify', { email });
    return Promise.resolve({
      code: '3A2BF4'
    });
  },
  getRegionList() {
    // return apiClient.get('/codes?type=REGION');
    return Promise.resolve({
      codeList: [
        { value: 'INCHEON_BUPYEONG', text: '인천광역시 부평구' },
        { value: 'GYEONGGI_SIHEUNG', text: '경기도 시흥시' }
      ]
    });
  }
};
