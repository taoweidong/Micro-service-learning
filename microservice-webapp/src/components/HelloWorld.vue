<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>这是一个基于Vue和SpringBoot混合的项目，由vue进行前台开发，SpringBoot作为容器装载前台页面呈现</h2>
    <el-button type="success" @click="showMsg">成功按钮</el-button>
  </div>
</template>

<script>
import { getRequest } from "../utils/api";

export default {
  name: "HelloWorld",
  data() {
    return {
      msg: "Welcome to Your Vue.js App"
    };
  },
  methods: {
    showMsg: function() {
      getRequest("/user/getUser", {}).then(resp => {
        console.log(JSON.stringify(resp.data));
        this.msg = JSON.stringify(resp.data);

        this.$notify({
          title: "消息",
          message: JSON.stringify(resp.data)
        });
      });

      // this.$message("这是一条消息提示");
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
