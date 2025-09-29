import request from '@/utils/request'

// 查询定时任务调度列表
export function listProcessGroup(query) {
  return request({
    url: '/flowabled/processGroup/list',
    method: 'get',
    params: query
  })
}

// 查询定时任务调度详细
export function getProcessGroup(processGroupId) {
  return request({
    url: '/flowabled/processGroup/' + processGroupId,
    method: 'get'
  })
}

// 新增定时任务调度
export function addProcessGroup(data) {
  return request({
    url: '/flowabled/processGroup',
    method: 'post',
    data: data
  })
}

// 修改定时任务调度
export function updateProcessGroup(data) {
  return request({
    url: '/flowabled/processGroup',
    method: 'put',
    data: data
  })
}

// 删除定时任务调度
export function delProcessGroup(processGroupId) {
  return request({
    url: '/flowabled/processGroup/' + processGroupId,
    method: 'delete'
  })
}

// 任务状态修改
export function changeProcessGroupStatus(processGroupId, status) {
  const data = {
    processGroupId,
    status
  }
  return request({
    url: '/flowabled/processGroup/changeStatus',
    method: 'put',
    data: data
  })
}


// 定时任务立即执行一次
export function runProcessGroup(processGroupId, processGroupGroup) {
  const data = {
    processGroupId,
    processGroupGroup
  }
  return request({
    url: '/flowabled/processGroup/run',
    method: 'put',
    data: data
  })
}
