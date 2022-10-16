import axios from 'axios'

let baseUrl = 'http://localhost:8080/'
axios.defaults.baseURL = baseUrl
axios.defaults.timeout = 50000
axios.defaults.withCredentials = true

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