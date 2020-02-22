import request from '@/utils/request'

export function getRoutes() {
  return request({
    url: '/role/routes',
    method: 'get'
  })
}

export function getRoles() {
  return request({
    url: '/role/roles',
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/role/role',
    method: 'post',
    data
  })
}

export function updateRole(id, data) {
  return request({
    url: `/role/role/${id}`,
    method: 'put',
    data
  })
}

export function deleteRole(id) {
  return request({
    url: `/vue-element-admin/role/${id}`,
    method: 'delete'
  })
}
/**
 *导出请求
 * @param {查询参数} query
 */
export function getExportData(query) {
  return request({
    url: '/vue-element-admin/role/export',
    method: 'post',
    params: query,
    responseType: 'blob'
  })
}

