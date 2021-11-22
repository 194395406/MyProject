import { Message } from 'element-ui'
import cookie from 'js-cookie'

export default function({ $axios, redirect }) {
  $axios.onRequest((config) => {
    // 添加请求头：token
    let userInfo = cookie.get('userInfo')
    if (userInfo) {
      console.log('添加header')
      userInfo = JSON.parse(userInfo)
      config.headers['token'] = userInfo.token
    }
    console.log('Making request to ' + config.url)
  })

  $axios.onRequestError((error) => {
    console.log('onRequestError', error) // for debug
  })

  $axios.onResponse((response) => {
    console.log('Reciving resposne', response)
    if (response.data.code === 0) {
      return response
    } else if (response.data.code === -211) {
      console.log('token校验失败')
      cookie.set('userInfo', '')
      //debugger
      //跳转到登录页面
      window.location.href = '/login'
    } else {
      Message({
        message: response.data.message,
        type: 'error',
        duration: 5 * 1000,
      })
      return Promise.reject(response)
    }
  })

  //通信失败
  $axios.onResponseError((error) => {
    console.log('onResponseError', error) // for debug
  })
}
