import request from '@/utils/request'

// 查询定时任务调度列表
export function listProcess(query) {
  return request({
    url: '/flowabled/process/list',
    method: 'get',
    params: query
  })
}

// 查询定时任务调度详细
export function getProcess(processId) {
  return request({
    url: '/flowabled/process/' + processId,
    method: 'get'
  })
}

// 新增定时任务调度
export function addProcess(data) {
  return request({
    url: '/flowabled/process',
    method: 'post',
    data: data
  })
}

// 修改定时任务调度
export function updateProcess(data) {
  return request({
    url: '/flowabled/process',
    method: 'put',
    data: data
  })
}

// 删除定时任务调度
export function delProcess(processId) {
  return request({
    url: '/flowabled/process/' + processId,
    method: 'delete'
  })
}

// 任务状态修改
export function changeProcessStatus(processId, status) {
  const data = {
    processId,
    status
  }
  return request({
    url: '/flowabled/process/changeStatus',
    method: 'put',
    data: data
  })
}


// 定时任务立即执行一次
export function runProcess(processId, processGroup) {
  const data = {
    processId,
    processGroup
  }
  return request({
    url: '/flowabled/process/run',
    method: 'put',
    data: data
  })
}
