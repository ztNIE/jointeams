import Cookies from 'js-cookie'

const getTokenInCookies = function() {
    return Cookies.get('jointeams')
}

const setTokenInCookies = function(token) {
    Cookies.set('jointeams', token, {expires: 1})
}

const removeTokenInCookies = function() {
    Cookies.remove('jointeam')
}

export default{
    name: 'cookieUtils',
    getTokenInCookies,
    setTokenInCookies,
    removeTokenInCookies
}