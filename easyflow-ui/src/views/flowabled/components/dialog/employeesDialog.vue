<template>
   <el-dialog title="选择成员" :visible.sync="visibleDialog" width="600px" append-to-body class="promoter_person">
      <div class="person_body clear">
          <div class="person_tree l">
              <el-input type="text" placeholder="搜索成员" v-model="searchVal" @input="getDebounceData($event)"></el-input>
              <p class="ellipsis tree_nav" v-if="!searchVal">
                  <span @click="getDepartmentList(0)" class="ellipsis">根节点</span>
                  <span v-for="(item,index) in departments.titleDepartments" class="ellipsis"
                  :key="index+'a'" @click="getDepartmentList(item.deptId)">{{item.deptName}}</span>
              </p>
              <selectBox :list="list"/>
          </div>
          <selectResult :total="total" @del="delList" :list="resList"/>
      </div>
      <span slot="footer" class="dialog-footer">
          <el-button @click="$emit('update:visible',false)">取 消</el-button>
          <el-button type="primary" @click="saveDialog">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
import selectBox from '../selectBox.vue';
import selectResult from '../selectResult.vue';
import mixins from './mixins';
import { listDeptTree } from '@/api/system/dept';

export default {
  components: { selectBox, selectResult },
  mixins: [mixins],
  props: ['visible', 'data', 'isDepartment'],
  watch: {
    visible(val) {
      this.visibleDialog = this.visible
      if (val) {
        this.getDepartmentList();
        this.searchVal = "";
        this.checkedEmployessList = this.data.filter(item => item.type === 1).map(({ name, targetId }) => ({
          nickName: name,
          userId: targetId,
          type: 1
        }));
        this.checkedDepartmentList = this.data.filter(item => item.type === 3).map(({ name, targetId }) => ({
          deptName: name,
          deptId: targetId,
          type: 3
        }));
      }
    },
    visibleDialog(val) {
      this.$emit('update:visible', val)
    }
  },
  computed: {
    total() {
      return this.checkedDepartmentList.length + this.checkedEmployessList.length
    },
    list() {
      return [{
        isDepartment: this.isDepartment,
        type: 'department',
        data: this.departments.childDepartments,
        isActive: (item) => this.$func.toggleClass(this.checkedDepartmentList, item, "deptId"),
        change: (item) => this.$func.toChecked(this.checkedDepartmentList, item, "deptId"),
        next: (item) => this.getDepartmentList(item.deptId)
      }, {
        type: 'employee',
        data: this.departments.employees,
        isActive: (item) => this.$func.toggleClass(this.checkedEmployessList, item, "userId"),
        change: (item) => this.$func.toChecked(this.checkedEmployessList, item, "userId"),
      }]
    },
    resList() {
      let data = [{
        type: 'employee',
        data: this.checkedEmployessList,
        cancel: (item) => this.$func.removeEle(this.checkedEmployessList, item, "userId")
      }]
      if (this.isDepartment) {
        data.unshift({
          type: 'department',
          data: this.checkedDepartmentList,
          cancel: (item) => this.$func.removeEle(this.checkedDepartmentList, item, "deptId")
        })
      }
      return data
    }
  },
  data() {
    return {
      checkedDepartmentList: [],
      checkedEmployessList: [],
    }
  },
  methods: {
    saveDialog() {
      let checkedList = [...this.checkedDepartmentList, ...this.checkedEmployessList].map(item => ({
        type: item.nickName ? 1 : 3,
        targetId: item.userId || item.deptId,
        name: item.nickName || item.deptName
      }))
      this.$emit('change', checkedList)
    },
    delList() {
      this.checkedDepartmentList = [];
      this.checkedEmployessList = []
    },
    getDepartmentList(id) {
      listDeptTree({
        parentId: id
      }).then(res => {
        this.departments = res.data
      })
    }
  }
}
</script>

<style>
@import "../../style/dialog.css";
</style>
