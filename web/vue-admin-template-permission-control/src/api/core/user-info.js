import request from "@/utils/request";

export default {
  getPageList(page, limit, searchObj) {
    return request({
      url: `/admin/core/userInfo/list/${page}/${limit}`,
      method: "get",
      params: searchObj
    });
  },

  getuserLoginRecordTop50(userId) {
    return request({
      url: `/admin/core/userLoginRecord/listTop50/${userId}`,
      method: "get"
    });
  },

  // 根据id查询会员日志记录
  showLoginRecord(id) {
    //打开对话框
    this.dialogTableVisible = true;
    //加载数据列表
    userInfoApi.getuserLoginRecordTop50(id).then(response => {
      this.loginRecordList = response.data.list;
    });
  },

  lock(id, status) {
    return request({
      url: `/admin/core/userInfo/lock/${id}/${status}`,
      method: "put"
    });
  }
};
