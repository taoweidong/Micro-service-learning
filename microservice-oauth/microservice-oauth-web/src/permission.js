import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

// 无需检查权限的路由白名单
const whiteList = ['/login']

/**
 * 全局钩子函数
 */
router.beforeEach(async(to, from, next) => {
  // 进度条开启
  NProgress.start()

  // 设置页面标题
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()

  console.log('用户所拥有的Token：' + hasToken)

  if (hasToken) {
    console.log('来源页面-------->' + to.path)
    if (to.path === '/login') {
      // 如果已经登录，则跳转到主页面
      next({ path: '/' })
      NProgress.done()
    } else {
      // 检查该用户是否拥有角色信息
      const hasRoles = store.getters.roles && store.getters.roles.length > 0
      console.log('用户当前角色' + store.getters.roles)
      if (hasRoles) {
        next()
      } else {
        try {
          // note: roles must be a object array! such as: ['admin'] or ,['developer','editor']
          const { roles } = await store.dispatch('user/getInfo')

          // 基于角色生成可访问路由图
          const accessRoutes = await store.dispatch('permission/generateRoutes', roles)

          // d动态添加可访问路由
          router.addRoutes(accessRoutes)

          // hack method to ensure that addRoutes is complete
          // set the replace: true, so the navigation will not leave a history record
          next({ ...to, replace: true })
        } catch (error) {
          // 如果获取user_info信息异常，则清除token信息，并且跳转到登录页面
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    // 如果没有Token，检查该路由是否在白名单中，如果在白名单中则不需要进行跳转
    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // 其他页面如果没有权限的话全部重定向到登录页面
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // 进度条关闭
  NProgress.done()
})
