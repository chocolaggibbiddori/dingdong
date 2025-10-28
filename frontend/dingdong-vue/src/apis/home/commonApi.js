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
        { title: '인천광역시 부평구', value: 'INCHEON_BUPYEONG' },
        { title: '경기도 시흥시', value: 'GYEONGGI_SIHEUNG' }
      ]
    });
  }
};
