<script setup>
import { computed } from 'vue'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import {
  faBook,
  faBookOpen,
  faBookOpenReader,
  faBookBookmark,
  faBan,
  faBookmark,
  faBoxesStacked,
  faCalendarPlus,
  faCheck,
  faChartColumn,
  faChartLine,
  faCheckCircle,
  faCoins,
  faCreditCard,
  faCircleExclamation,
  faCircleQuestion,
  faCircleUser,
  faEnvelope,
  faEye,
  faFileExport,
  faHand,
  faHandshake,
  faLock,
  faMagnifyingGlass,
  faPen,
  faPlus,
  faReceipt,
  faRightToBracket,
  faRotateLeft,
  faTrash,
  faTriangleExclamation,
  faUser,
  faUserPlus,
  faUsers,
  faUserCheck,
} from '@fortawesome/free-solid-svg-icons'

const props = defineProps({
  name: {
    type: String,
    required: true,
  },
  size: {
    type: String,
    default: undefined,
  },
})

const iconMap = {
  'fa-book': faBook,
  'fa-book-open': faBookOpen,
  'fa-book-open-reader': faBookOpenReader,
  'fa-bookmark': faBookmark,
  'fa-book-bookmark': faBookBookmark,
  'fa-ban': faBan,
  'fa-boxes-stacked': faBoxesStacked,
  'fa-check': faCheck,
  'fa-calendar-plus': faCalendarPlus,
  'fa-chart-column': faChartColumn,
  'fa-chart-line': faChartLine,
  'fa-check-circle': faCheckCircle,
  'fa-coins': faCoins,
  'fa-credit-card': faCreditCard,
  'fa-circle-exclamation': faCircleExclamation,
  'fa-circle-user': faCircleUser,
  'fa-envelope': faEnvelope,
  'fa-eye': faEye,
  'fa-file-export': faFileExport,
  'fa-hand': faHand,
  'fa-handshake': faHandshake,
  'fa-lock': faLock,
  'fa-magnifying-glass': faMagnifyingGlass,
  'fa-pen': faPen,
  'fa-plus': faPlus,
  'fa-receipt': faReceipt,
  'fa-right-to-bracket': faRightToBracket,
  'fa-rotate-left': faRotateLeft,
  'fa-trash': faTrash,
  'fa-triangle-exclamation': faTriangleExclamation,
  'fa-user': faUser,
  'fa-user-check': faUserCheck,
  'fa-user-plus': faUserPlus,
  'fa-users': faUsers,
}

const icon = computed(() => iconMap[props.name] ?? faCircleQuestion)
</script>

<template>
  <FontAwesomeIcon :icon="icon" :size="size" fixed-width />
</template>
