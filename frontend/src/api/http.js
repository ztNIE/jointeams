import axios from 'axios'
// import Cookies from 'js-cookie'
// import { ElMessage } from "element-plus";

let baseUrl = ''
axios.defaults.baseURL = baseUrl
axios.defaults.timeout = 50000
axios.defaults.withCredentials = true

// axios.interceptors.request.use(config => {
//   if (Cookies.get('token')) {
//     const token = Cookies.get('token')
//     token && (config.headers.Authorization = token)
//   } 
//   return config
// }, error => {
//     console.log(error.response)
//     ElMessage({
//         type: "error",
//         message: 'error.message',
//         offset: -3
//     })
//   return Promise.error(error)
// })

// axios.interceptors.response.use(response => {
//     return response
// }, error => {
//     let errorObj = error.response
//     console.log(errorObj)
//     if (errorObj.status !== 500) {
//         ElMessage({
//             type: "error",
//             message: error.message,
//             offset: -3
//         })
//     }
//     return Promise.reject(error)
// })

//get
export function get (url, data) {
  return axios.get(url, data)
}
// post
export function post (url, data) {
  return axios.post(url, data)
}
// put
export function put (url, data) {
  return axios.put(url, data)
}
// delete 
export function del (url, data) {
  return axios.delete(url, data)
}