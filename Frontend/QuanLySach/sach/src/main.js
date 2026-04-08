import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import FaIcon from './components/common/FaIcon.vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import '@fortawesome/fontawesome-free/css/all.min.css'

const app = createApp(App)

app.use(router)
app.component('FaIcon', FaIcon)

app.mount('#app')




