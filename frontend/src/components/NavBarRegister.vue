<template>
<section class="navappregister">
    <div>
    <md-dialog :md-active.sync="login">
      <md-dialog-title>Login</md-dialog-title>
      <md-field md-clearable> <label>Cleareable</label> <md-input v-model="initial"></md-input></md-field>
      <md-field><label>Password toggle</label><md-input v-model="password" type="password"></md-input>
    </md-field>
      <md-dialog-actions>
        <md-button class="md-primary" @click="login = false">Log in</md-button> </md-dialog-actions>
    </md-dialog>
    <md-toolbar class="md=accent" md-elevation="1">
        <a class="md-title" style="flex: 1" href ="/">SiteName</a>
        <md-button href ="/register">Register</md-button>
        <md-button class="md-primary md-raised" @click="login = true">Login</md-button>
    </md-toolbar>
    </div>
</section>
</template>

    
<style lang="scss" scoped>
</style>


<script>
export default {
        name: 'DialogCustom',
    data: () => ({
      login: false,
      initial: 'Username',
      password: 'mysecurepassword'
    }),
    
}
</script>