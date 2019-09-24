'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
  dev: {

    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    // 前端跨域配置
    proxyTable: {
      '/ms/user': {
        // 测试环境
        target: 'http://127.0.0.1:9000', // 接口域名
        changeOrigin: true, //是否跨域
        pathRewrite: {
          '^/ms/user': '' //需要rewrite重写的,
        }
      }
    },

    // Various Dev Server settings
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-


    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },

  build: {
    // 修改构建后的文件存放位置，将其指定到SpringBoot的/resources/static目录中，与assetsRoot配置相对应   Template for index.html
    index: path.resolve(__dirname, '../../microservice-webapp/src/main/resources/static/index.html'),

    // 修改构建后的文件存放位置，将其指定到SpringBoot的/resources/static目录中
    assetsRoot: path.resolve(__dirname, '../../microservice-webapp/src/main/resources/static'),
    // 静态文件目录名
    assetsSubDirectory: 'static',
    // 注意：assetsPublicPath配置为生成的css和js文件引入时的前缀地址，
    // 应该使用相对路径
    assetsPublicPath: './',

    /**
     * Source Maps
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  }
}
