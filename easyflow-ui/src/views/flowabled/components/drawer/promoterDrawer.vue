<template>
  <el-drawer
    :append-to-body="true"
    title="发起人设置"
    :visible.sync="promoterDrawer"
    direction="rtl"
    class="set_promoter"
    size="550px"
    :before-close="savePromoter"
    :close-on-click-modal="false"
    :show-close="true"
  >
    <!-- 抽屉内容区 -->
    <div class="promoter_content drawer_content">
      <!-- 标签页 -->
      <el-tabs
        v-model="activeName"
        class="promoter-tabs"
        tab-position="top"
      >
        <el-tab-pane
          label="设置发起人"
          name="first"
          class="promoter-tab-pane"
        >
          <!-- 已选发起人展示 -->
          <div class="selected-promoter-wrap">
            <div class="selected-content">
              {{ $func.arrToStr(flowPermission) || "所有人" }}
            </div>
          </div>

          <!-- 操作按钮 -->
          <el-button
            type="primary"
            @click="addPromoter"
            round
            icon="el-icon-plus"
            class="add-promoter-btn"
            :disabled="!flowPermission1.id"
          >
          添加/修改发起人
          </el-button>
        </el-tab-pane>
      </el-tabs>

      <!-- 人员选择弹窗（优化层级和过渡） -->
      <employees-dialog
        :isDepartment="true"
        :visible.sync="promoterVisible"
        :data.sync="checkedList"
        @change="surePromoter"
        title="选择发起人"
      :close-on-click-modal="false"
      />
    </div>

    <!-- 底部操作栏（单独分离保存按钮，提升操作便捷性） -->
    <div class="promoter-footer">
      <el-button
        type="default"
        @click="closeDrawer"
        class="cancel-btn"
      >
        取消
      </el-button>
      <el-button
        type="primary"
        @click="savePromoter"
        class="save-btn"
        :disabled="!flowPermission1.id"
      >
      确认保存
      </el-button>
    </div>
  </el-drawer>
</template>

<script>
import employeesDialog from "../dialog/employeesDialog.vue";
import { mapState, mapMutations } from "vuex";

export default {
  components: { employeesDialog },
  data() {
    return {
      flowPermission: [],
      promoterVisible: false,
      checkedList: [],
      activeName: "first",
    };
  },
  computed: {
    ...mapState("flowabled", ["promoterDrawer", "flowPermission1"]),
  },
  watch: {
    // 监听flowPermission1变化，同步数据（增加immediate，初始化时触发）
    flowPermission1: {
      handler(val) {
        this.flowPermission = val?.value || [];
      },
      immediate: true,
      deep: true  // 深度监听，防止对象内部属性变化不触发
    }
  },
  methods: {
    ...mapMutations("flowabled", ["setPromoter", "setFlowPermission"]),

    // 打开添加/修改弹窗（增加数据深拷贝，避免原数据污染）
    addPromoter() {
      this.checkedList = JSON.parse(JSON.stringify(this.flowPermission));
      this.promoterVisible = true;
    },

    // 确认选择发起人
    surePromoter(data) {
      this.flowPermission = data || [];
      this.promoterVisible = false;
    },

    // 保存发起人设置（增加操作反馈）
    savePromoter() {
      this.setFlowPermission({
        value: this.flowPermission,
        flag: true,
        id: this.flowPermission1.id,
      });
      this.closeDrawer();
    },

    // 关闭抽屉
    closeDrawer() {
      this.setPromoter(false);
    }
  },
};
</script>

<style lang="less" scoped>
// 抽屉整体样式
.set_promoter {
  // 优化抽屉头部标题样式
  .el-drawer__header {
    padding: 16px 24px;
    border-bottom: 1px solid #f2f3f5;
    .el-drawer__title {
      font-size: 16px;
      font-weight: 500;
      color: #1d2129;
    }
  }

  // 内容区样式
  .promoter_content {
    padding: 0 24px;
    overflow: hidden;

    // 标签页样式
    .promoter-tabs {
      margin-bottom: 24px;
      .el-tabs__header {
        margin-bottom: 16px;
      }
      .el-tabs__nav-wrap {
        .el-tabs__nav {
          .el-tabs__item {
            font-size: 14px;
            color: #4e5969;
            &.is-active {
              color: #1890ff;
              font-weight: 500;
            }
          }
        }
      }
    }

    // 已选发起人展示区
    .selected-promoter-wrap {
      display: flex;
      flex-direction: column;
      margin-bottom: 24px;
      .selected-label {
        font-size: 14px;
        color: #4e5969;
        margin-bottom: 8px;
        font-weight: 500;
      }
      .selected-content {
        padding: 12px 16px;
        background: #f7f8fa;
        border-radius: 4px;
        border: 1px solid #ebeef5;
        font-size: 14px;
        color: #1d2129;
        min-height: 44px;
        display: flex;
        align-items: center;
        // 内容过长时换行
        white-space: pre-wrap;
        word-break: break-all;
      }
    }

    // 添加按钮样式
    .add-promoter-btn {
      margin-bottom: 0;
      padding: 8px 24px;
      font-size: 14px;
      // 禁用状态优化
      &.is-disabled {
        background: #f7f8fa;
        border-color: #ebeef5;
        color: #c0c4cc;
      }
    }
  }

  // 底部操作栏样式
  .promoter-footer {
    padding: 16px 24px;
    border-top: 1px solid #f2f3f5;
    display: flex;
    justify-content: flex-end;
    gap: 12px;  // 按钮间距
    .cancel-btn, .save-btn {
      padding: 8px 20px;
      font-size: 14px;
    }
    .save-btn {
      // 强调保存按钮
      background: #1890ff;
      border-color: #1890ff;
      &.is-disabled {
        background: #f7f8fa;
        border-color: #ebeef5;
        color: #c0c4cc;
      }
    }
  }

  // 统一弹窗过渡效果（与element-ui风格一致）
  .el-dialog {
    &-fade-enter-active,
    &-fade-leave-active {
      transition: opacity 0.3s, transform 0.3s;
    }
    &-fade-enter,
    &-fade-leave-to {
      opacity: 0;
      transform: translateX(50px);
    }
  }
}
</style>
