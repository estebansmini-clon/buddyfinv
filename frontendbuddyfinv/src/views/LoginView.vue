<template>

    <div class="login-view">
        <UserTable/>
    </div>

</template>

<script >
import UserTable from '@/components/UserTable.vue'

export default{
    name: 'Loginview',
        components:{
            UserTable
        }
}
</script>

<style scoped>
.login-view {
  padding: 2rem;
  font-family: 'Segoe UI', sans-serif;
}
</style>
