import axios from 'axios'
// import Cookies from 'js-cookie'
// import { ElMessage } from "element-plus";

let baseUrl = 'http://localhost:8080/'
axios.defaults.baseURL = baseUrl
axios.defaults.timeout = 50000
axios.defaults.withCredentials = true

// axios.interceptors.request.use(config => {
//   if (Cookies.get('jointeams')) {
//     const token = Cookies.get('jointeams')
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
//

axios.interceptors.response.use(response => {
    return response
}, error => {
    let errorMsg = error.message
    if (error.response && error.response.data && error.response.data.msg) {
        errorMsg = error.response.data.msg
    }
    console.log(`Status: ${error.response.status}, Msg: ${errorMsg}`)
    console.log(error)
    return Promise.reject(new Error(errorMsg))
})

//get
export function get(url, params) {
    return axios.get(url, params)
}

// post
export function post(url, data) {
    return axios.post(url, data)
}

// put
export function put(url, data) {
    return axios.put(url, data)
}

// delete
export function del(url, params) {
    return axios.delete(url, params)
}