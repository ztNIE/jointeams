module.exports = {
    // https://cli.vuejs.org/config/#devserver-proxy
    devServer: {
        port: 8888,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': '/'
                }
            }
        }
    }
}