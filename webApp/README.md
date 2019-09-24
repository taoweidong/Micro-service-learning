# webapp

> ## 这是一个基于Vue和SpringBoot混合的项目，由vue进行前台开发，SpringBoot作为容器装载前台页面呈现

## Build Setup

``` shell
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

## 修改构建配置

``` js
 build: {

    // 修改构建后的文件存放位置，将其指定到SpringBoot的/resources/static目录中，与assetsRoot配置相对应   Template for index.html
    index: path.resolve(__dirname, '../../static/index.html'),

    // 修改构建后的文件存放位置，将其指定到SpringBoot的/resources/static目录中
    assetsRoot: path.resolve(__dirname, '../../static'),

    // 静态文件目录名
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',

    /**
     * Source Maps
     */
    productionSourceMap: true,

    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to true, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // npm run build --report
    // Set to true or false to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report

  }
```




