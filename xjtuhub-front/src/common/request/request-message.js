/**
 * 请求提示接口
 * 作者：王坤 2022-03
 */

import { ElMessageBox } from 'element-plus'

export default {
  confirm({ title, content }) {
    return new Promise((resolve, reject) => {

      ElMessageBox.confirm(
        content,
        title,
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      )
        .then(() => resolve(true))
        .catch(() => reject(false));

    });

  }
}