import request from '@/utils/request'

// 查询定时任务调度列表
export function listFlowProcess(query) {
  return request({
    url: '/flowabled/flowProcess/list',
    method: 'get',
    params: query
  })
}

// 查询定时任务调度详细
export function getFlowProcess(flowProcessId) {
  return request({
    url: '/flowabled/flowProcess/' + flowProcessId,
    method: 'get'
  })
}

// 新增定时任务调度
export function addFlowProcess(data) {
  return request({
    url: '/flowabled/flowProcess',
    method: 'post',
    data: data
  })
}

// 修改定时任务调度
export function updateFlowProcess(data) {
  return request({
    url: '/flowabled/flowProcess',
    method: 'put',
    data: data
  })
}

// 删除定时任务调度
export function delFlowProcess(flowProcessId) {
  return request({
    url: '/flowabled/flowProcess/' + flowProcessId,
    method: 'delete'
  })
}

// 任务状态修改
export function changeFlowProcessStatus(flowProcessId, status) {
  const data = {
    flowProcessId,
    status
  }
  return request({
    url: '/flowabled/flowProcess/changeStatus',
    method: 'put',
    data: data
  })
}


// 定时任务立即执行一次
export function runFlowProcess(flowProcessId, flowProcessGroup) {
  const data = {
    flowProcessId,
    flowProcessGroup
  }
  return request({
    url: '/flowabled/flowProcess/run',
    method: 'put',
    data: data
  })
}
