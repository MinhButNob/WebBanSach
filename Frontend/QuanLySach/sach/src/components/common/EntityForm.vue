<script setup>
const props = defineProps({
  fields: {
    type: Array,
    required: true,
  },
  modelValue: {
    type: Object,
    required: true,
  },
})

const emit = defineEmits(['update:modelValue', 'submit'])

const updateField = (key, value) => {
  emit('update:modelValue', { ...props.modelValue, [key]: value })
}
</script>

<template>
  <form class="row g-3" @submit.prevent="emit('submit')">
    <div v-for="field in fields" :key="field.key" :class="field.col || 'col-12'">
      <label class="form-label">{{ field.label }}</label>
      <select
        v-if="field.type === 'select'"
        class="form-select"
        :value="modelValue[field.key]"
        @change="updateField(field.key, $event.target.value)"
      >
        <option v-for="option in field.options" :key="option.value" :value="option.value">
          {{ option.label }}
        </option>
      </select>
      <textarea
        v-else-if="field.type === 'textarea'"
        class="form-control"
        rows="3"
        :value="modelValue[field.key]"
        @input="updateField(field.key, $event.target.value)"
      ></textarea>
      <input
        v-else
        :type="field.type || 'text'"
        class="form-control"
        :value="modelValue[field.key]"
        @input="updateField(field.key, $event.target.value)"
      />
    </div>

    <div class="col-12">
      <slot name="actions">
        <button type="submit" class="btn btn-primary">Luu</button>
      </slot>
    </div>
  </form>
</template>
