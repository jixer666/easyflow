<template>
  <div class="base-container">
    <el-card>
      <el-form
        ref="form"
        :model="formData"
        :rules="formRules"
        label-width="120px"
        class="form-container"
      >
        <el-form-item label="名称" prop="name">
          <el-input
            v-model="formData.name"
            placeholder="请输入流程名称"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item label="说明" prop="desc">
          <el-input
            v-model="formData.desc"
            placeholder="请输入流程说明"
            clearable
          ></el-input>
        </el-form-item>

        <el-form-item label="分组" prop="groupId">
          <el-select v-model="formData.groupId" placeholder="请选择流程分组">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item class="form-actions">
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "step1",
  props: ['formData'],
  data() {
    return {
      formRules: {
        name: [
          { required: true, message: '请输入流程名称', trigger: 'blur' },
          { min: 3, max: 15, message: '流程名称长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        groupId: [
          { required: true, message: '请输入流程分组', trigger: 'blur' },
        ],
      }
    };
  },
  created() {},
  methods: {
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 表单验证通过，这里可以处理提交逻辑
          this.$message.success('表单提交成功！');
          console.log('表单数据:', this.formData);
          // 实际项目中可以调用API提交数据
          // this.$api.submitUserInfo(this.formData).then(...);
        } else {
          this.$message.error('表单验证失败，请检查输入内容');
          return false;
        }
      });
    },

    // 重置表单
    resetForm() {
      this.$refs.form.resetFields();
    }
  }
};
</script>

<style scoped>
.base-container {
  width: 800px;
  margin: 0 auto;
  padding: 10px;
}

.form-container {
  padding: 20px;
}

.form-actions {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 10px;
}

::v-deep .el-form-item__content {
  flex: 1;
  min-width: 200px;
}
</style>
