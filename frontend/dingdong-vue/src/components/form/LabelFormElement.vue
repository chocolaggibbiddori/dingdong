<script setup>
defineProps({
  text: {
    type: String,
    required: true
  },
  required: {
    type: Boolean,
    default: false
  }
});
</script>

<template>
  <div class="label-container">
    <label class="label-text">
      {{ text }}
      <span
        v-if="required"
        class="required-mark"
      >
        *
      </span>
    </label>

    <slot />
  </div>
</template>

<style scoped>
.label-container {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.label-text {
  color: #999;
  font-size: 0.9rem;
  text-align: left;
}

.required-mark {
  color: #ff0000;
}
</style>
