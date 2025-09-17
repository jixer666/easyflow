const state = {
  tableId: '',
  isTried: false,
  promoterDrawer: false,
  flowPermission1: {},
  approverDrawer: false,
  approverConfig1: {},
  copyerDrawer: false,
  copyerConfig1: {},
  conditionDrawer: false,
  conditionsConfig1: {
    conditionNodes: [],
  },
  formItems: []
}
const mutations = {
  setTableId(status, payload) {
    status.tableId = payload
  },
  setIsTried(status, payload) {
    status.isTried = payload
  },
  setPromoter(status, payload) {
    status.promoterDrawer = payload
  },
  setFlowPermission(status, payload) {
    status.flowPermission1 = payload
  },
  setApprover(status, payload) {
    status.approverDrawer = payload
  },
  setApproverConfig(status, payload) {
    status.approverConfig1 = payload
  },
  setCopyer(status, payload) {
    status.copyerDrawer = payload
  },
  setCopyerConfig(status, payload) {
    status.copyerConfig1 = payload
  },
  setCondition(status, payload) {
    status.conditionDrawer = payload
  },
  setConditionsConfig(status, payload) {
    status.conditionsConfig1 = payload
  },
  setFormItems(status, payload) {
    status.formItems = payload
  },
}

const actions = {

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
