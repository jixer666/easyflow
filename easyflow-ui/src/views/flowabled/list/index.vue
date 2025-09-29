<template>
  <div class="list-div">
    <div class="top-op">
      <el-button
        type="primary"
        size="medium"
        @click="createGroup"
        :loading="addLaoding"
        >创建</el-button
      >
    </div>
    <div v-loading="listLaoding">
      <el-card
        class="box-card"
        v-for="(item, index) in groupsList"
        :key="index"
      >
        <div slot="header" class="box-header">
          <span
            v-if="!item.isEdit"
            @click="chooseEdit(item)"
            class="header-title"
            >{{ item.groupName }}</span
          >
          <div v-else>
            <el-input
              v-model="item.groupName"
              class="header-input"
              :ref="'input' + item.id"
              @blur.stop="editGroupInputBlur(item)"
              clearable
              size="medium"
            ></el-input>
          </div>
        </div>
        <div class="box-content">
          <div class="box-item">123</div>
        </div>
      </el-card>
    </div>
  </div>
</template>
<script>
import {
  addProcessGroup,
  listProcessGroup,
  updateProcessGroup,
} from "@/api/flowabled/processGroup";

export default {
  name: "flowList",
  data() {
    return {
      form: {
        pageNum: 1,
        pageSize: 10,
      },
      groupsList: [],
      addLaoding: false,
      listLaoding: false,
    };
  },
  created() {
    this.getProcessGroupList();
  },
  methods: {
    getProcessGroupList() {
      this.listLaoding = true;
      listProcessGroup(this.form)
        .then((res) => {
          this.groupsList = res.rows.map((item) => {
            return {
              ...item,
              isEdit: false,
            };
          });
          this.form.total = parent(res.total);
          this.listLaoding = false;
        })
        .catch((error) => {
          this.listLaoding = false;
        });
    },
    createGroup() {
      this.addLoding = true;
      addProcessGroup()
        .then((res) => {
          res.data.isEdit = true;
          this.groupsList.push(res);
          this.addLaoding = false;
        })
        .catch((error) => {
          this.addLaoding = false;
        });
    },
    chooseEdit(item) {
      item.isEdit = true;
      this.$nextTick(() => {
        const inputRef = `input${item.id}`;
        const inputElement = this.$refs[inputRef];

        if (Array.isArray(inputElement) && inputElement.length > 0) {
          if (typeof inputElement[0].focus === "function") {
            inputElement[0].focus();
          } else {
            console.warn(`元素${inputRef}没有focus方法`);
          }
        } else {
          console.warn(`未找到引用${inputRef}`);
        }
      });
    },
    editGroupInputBlur(item) {
      item.isEdit = false;
      updateProcessGroup(item);
    },
  },
};
</script>
<style scoped>
.list-div {
  padding: 20px;
}

.top-op {
  text-align: right;
}

.box-card {
  margin: 10px 0;
}

.box-header {
  height: 40px;
  display: flex;
  align-items: center;
}

.box-content {
  box-sizing: border-box;
  display: flex;
  flex-wrap: wrap;
  position: relative;
}

.header-input {
  width: 300px;
}

.header-title {
  font-size: 16px;
}

.box-item {
  margin: 5px 20px;
  padding: 5px;
  background-color: #d7f2f7;
  cursor: pointer;
}
</style>
