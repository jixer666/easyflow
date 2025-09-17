<template>
  <div>
    <div class="fd-nav">
      <div class="fd-nav-left">
        <div class="fd-nav-title">{{ workFlowDef.name }}</div>
      </div>
      <div class="fd-nav-center">
        <div class="fd-nav-container">
          <div class="ghost-bar" :style="'transform: translateX(' + (activeNum - 1) * 150 + 'px)'"></div>
          <div class="fd-nav-item" :class="{ active: activeNum === 1 }" @click="toStep(1)" >
            <span class="order-num" >1</span>基础信息
          </div>
          <div class="fd-nav-item" :class="{ active: activeNum === 2 }" @click="toStep(2)">
            <span class="order-num" >2</span>表单设计
          </div>
          <div class="fd-nav-item" :class="{ active: activeNum === 3 }" @click="toStep(3)">
            <span class="order-num" >3</span>流程设计
          </div>
          <div class="fd-nav-item" :class="{ active: activeNum === 4 }" @click="toStep(4)">
            <span class="order-num" >4</span>高级设置
          </div>
        </div>
      </div>
      <div class="fd-nav-right">
        <button type="button" class="ant-btn button-preview">
          <span>预 览</span>
        </button>
        <button type="button" class="ant-btn button-publish" @click="saveSet">
          <span>发 布</span>
        </button>
      </div>
    </div>
    <step1
      v-if="activeNum === 1"
      :formData="flowBaseInfo"
    ></step1>
    <step2
      v-if="activeNum === 2"
      :drawingList="formItems"
    ></step2>
    <step3
      v-if="activeNum === 3"
      :nodeConfig="nodeConfig"
      :flowPermission="flowPermission"
      :directorMaxLevel="directorMaxLevel"
      :tipList="tipList"
      :tipVisible="tipVisible"
    ></step3>
  </div>
</template>
<script>
import Step1 from "./step1";
import Step2 from "./step2";
import Step3 from "./step3";
import { mapMutations } from "vuex";

export default {
  components: { Step1, Step2, Step3 },
  data() {
    return {
      activeNum: 1,
      tipList: [],
      tipVisible: false,
      processConfig: {
        tableId: null,
        workFlowDef: {
          name: "未命名流程",
        },
        directorMaxLevel: 4,
        flowPermission: [],
        nodeConfig: {
          nodeName: "发起人",
          type: 0,
          priorityLevel: "",
          settype: "",
          selectMode: "",
          selectRange: "",
          directorLevel: "",
          examineMode: "",
          noHanderAction: "",
          examineEndDirectorLevel: "",
          ccSelfSelectFlag: "",
          conditionList: [],
          nodeUserList: [],
          childNode: {},
          conditionNodes: {},
        },
      },
      nodeConfig: {},
      workFlowDef: {},
      flowPermission: [],
      directorMaxLevel: 0,
      formItems: [],
      flowBaseInfo: {
        name: null,
        desc: null,
        groupId: null
      },
    };
  },
  created() {
    this.initCreateData();
  },
  methods: {
    ...mapMutations("flowabled", ["setTableId", "setIsTried"]),
    initCreateData() {
      let {
        nodeConfig,
        flowPermission,
        directorMaxLevel,
        workFlowDef,
        tableId,
      } = this.processConfig;
      this.nodeConfig = nodeConfig;
      this.flowPermission = flowPermission;
      this.directorMaxLevel = directorMaxLevel;
      this.workFlowDef = workFlowDef;
      this.setTableId(tableId);
    },
    toStep(step) {
      this.activeNum = step;
    },
    reErr({ childNode }) {
      if (childNode) {
        let { type, error, nodeName, conditionNodes } = childNode;
        if (type == 1 || type == 2) {
          if (error) {
            this.tipList.push({
              name: nodeName,
              type: ["", "审核人", "抄送人"][type],
            });
          }
          this.reErr(childNode);
        } else if (type == 3) {
          this.reErr(childNode);
        } else if (type == 4) {
          this.reErr(childNode);
          for (var i = 0; i < conditionNodes.length; i++) {
            if (conditionNodes[i].error) {
              this.tipList.push({
                name: conditionNodes[i].nodeName,
                type: "条件",
              });
            }
            this.reErr(conditionNodes[i]);
          }
        }
      } else {
        childNode = null;
      }
    },
    async saveSet() {
      console.log({
        flowBaseInfo: this.flowBaseInfo,
        formItem: this.formItems,
        nodeConfig: this.nodeConfig
      })
      // this.setIsTried(true);
      // this.tipList = [];
      // this.reErr(this.nodeConfig);
      // if (this.tipList.length != 0) {
      //   this.tipVisible = true;
      //   return;
      // }
      // this.processConfig.flowPermission = this.flowPermission;
      // // eslint-disable-next-line no-console
      // console.log(JSON.stringify(this.processConfig));
      // let res = await setWorkFlowData(this.processConfig);
      // if (res.code == 200) {
      //   this.$message.success("设置成功");
      //   setTimeout(function () {
      //     window.location.href = "";
      //   }, 200);
      // }
    },
    submitForm() {
      this.submitLoading=true;
      step3Ref.value.getProcessData().then(res => {
        let step1 = store.step1;
        let step2 = store.step2;


        let flow = util.deepCopy(step1);
        flow.formItems = JSON.stringify(step2);
        flow.formItemsPc = JSON.stringify(store.step2Form);
        flow.process = JSON.stringify(res);
        flow.admin = JSON.stringify(step1.admin);

        flow.publish=directPublish.value

        addFlow(flow).then(res => {
          validateDialogShow.value = false;
          //TODO 修改返回地址
          router.push("/flow/group")
        }).finally(()=>{
          submitLoading.value=false;

        })


      })
    }
  },
};
</script>
<style scoped>
@import "../style/workflow.css";

@font-face {
  font-family: Chinese Quote;
  src: local("PingFang SC"), local("SimSun");
  unicode-range: u+2018, u+2019, u+201c, u+201d;
}


</style>
