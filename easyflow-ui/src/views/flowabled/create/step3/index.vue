<template>
  <div class="app-container">
    <div class="fd-nav-content">
      <section class="dingflow-design">
        <div class="zoom">
          <div
            :class="'zoom-out' + (nowVal == 50 ? ' disabled' : '')"
            @click="zoomSize(1)"
          ></div>
          <span>{{ nowVal }}%</span>
          <div
            :class="'zoom-in' + (nowVal == 300 ? ' disabled' : '')"
            @click="zoomSize(2)"
          ></div>
        </div>
        <div
          class="box-scale"
          id="box-scale"
          :style="
            'transform: scale(' +
            nowVal / 100 +
            '); transform-origin: 50% 0px 0px;'
          "
        >
          <nodeWrap
            :nodeConfig.sync="nodeConfig"
            :flowPermission.sync="flowPermission"
          ></nodeWrap>
          <div class="end-node">
            <div class="end-node-circle"></div>
            <div class="end-node-text">流程结束</div>
          </div>
        </div>
      </section>
    </div>

    <errorDialog :visible.sync="tipVisible" :list="tipList" />
    <promoterDrawer />
    <approverDrawer :directorMaxLevel="directorMaxLevel" />
    <copyerDrawer />
    <conditionDrawer />
  </div>
</template>

<script>
import nodeWrap from "../../components/nodeWrap";
import errorDialog from "../../components/dialog/errorDialog";
import promoterDrawer from "../../components/drawer/promoterDrawer";
import approverDrawer from "../../components/drawer/approverDrawer";
import copyerDrawer from "../../components/drawer/copyerDrawer";
import conditionDrawer from "../../components/drawer/conditionDrawer";

export default {
  name: "step3",
  props: [
    "nodeConfig",
    "flowPermission",
    "directorMaxLevel",
    "tipList",
    "tipVisible",
  ],
  components: {
    nodeWrap,
    errorDialog,
    promoterDrawer,
    approverDrawer,
    copyerDrawer,
    conditionDrawer,
  },
  data() {
    return {
      nowVal: 100,
    };
  },
  created() {},
  methods: {
    zoomSize(type) {
      if (type == 1) {
        if (this.nowVal == 50) return;
        this.nowVal -= 10;
      } else {
        if (this.nowVal == 300) return;
        this.nowVal += 10;
      }
    },
  },
};
</script>

<style scoped>
@import "../../style/workflow.css";

.error-modal-list {
  width: 455px;
}
</style>
