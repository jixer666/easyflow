<template>
  <vue2-water-marker
    :text="info"
  ></vue2-water-marker>
</template>

<script>
export default {
  name: "Water-Marker",
  data() {
    return {
        info: ""
    };
  },
  mounted() {
    this.info = this.$store.getters.name
  },
};
</script>

<style scoped></style>
