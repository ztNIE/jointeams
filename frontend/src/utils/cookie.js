import Cookies from 'js-cookie'

const getTokenInCookies = function() {
    return Cookies.get('token')
}

const setTokenInCookies = function(token) {
    Cookies.set('token', token, {expires: 1})
}

const removeTokenInCookies = function() {
    Cookies.remove('token')
}

export default{
    name: 'cookieUtils',
    getTokenInCookies,
    setTokenInCookies,
    removeTokenInCookies
}