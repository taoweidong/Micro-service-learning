import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/data/list',
    method: 'get',
    params
  })
}
